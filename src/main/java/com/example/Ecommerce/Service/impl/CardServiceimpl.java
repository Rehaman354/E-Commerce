package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardDetailsResponseDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.Enum.CardType;
import com.example.Ecommerce.Repositiory.CardRepository;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Service.Interfaces.CardService;
import com.example.Ecommerce.Transformer.CardTransformer;
import com.example.Ecommerce.model.Card;
import com.example.Ecommerce.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceimpl implements CardService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;
    @Override
    public CardResponseDto addcard(CardRequestDto cardRequestDto) throws Exception {
        Customer customer;
        try{
        customer=customerRepository.findById(cardRequestDto.getCustomerId()).get();
        }catch(Exception e)
        {
            throw new Exception("Invalid customer Id");
        }
        if(cardRepository.findByCardNo(cardRequestDto.getCardNo())!=null)
            throw new Exception("CardNo already exists");
        Card card= CardTransformer.cardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);
        customer.getCards().add(card);
        customerRepository.save(customer);//customer will get updated,card also added automatically
        //prepare cardresponsedto
        return CardTransformer.cardToCardResponseDto(card);
    }

    @Override
    public List<CardResponseDto> getAllCardsOfParticularType(CardType cardType) {
        List<CardResponseDto> responseDtos=new ArrayList<>();
       for(Card card:cardRepository.findAll())
       {
           if(card.getCardType()==cardType)
           {
               responseDtos.add(CardTransformer.cardToCardResponseDto(card));
           }
       }
       return responseDtos;
    }

    @Override
    public List<CardResponseDto> getCardsOfCardTypeWithGreaterExpiryDate(CardType cardType, String date) {
        String cardtype=cardType.toString();
        List<Card> cards=cardRepository.getCardsOfCardTypeWithGreaterExpiryDate(cardtype,date);
        List<CardResponseDto> responseDtos=new ArrayList<>();
        for(Card card: cards)
        {
            responseDtos.add(CardTransformer.cardToCardResponseDto(card));
        }
        return responseDtos;
    }

    @Override
    public String getCardTypeOfMaxCards() {
       String str=cardRepository.getCardTypeOfMaxCards();
       if(str==null)return "No cards exist";
       return str;
    }

    @Override
    public CardResponseDto delete(CardRequestDto cardRequestDto) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(cardRequestDto.getCustomerId()).get();
        }catch(Exception e)
        {
            throw new Exception("Invalid customer Id");
        }
        Card card=cardRepository.findByCardNo(cardRequestDto.getCardNo());
        if(card==null || card.getCustomer()!=customer )
            throw new Exception("CardNot found,pls input valid cardno");
        CardResponseDto response=CardTransformer.cardToCardResponseDto(card);
        //all details match themn delete card from customer cards then from card repo
        customer.getCards().remove(card);
        customerRepository.save(customer);
        cardRepository.delete(card);
        //return cardresponse after deleting
        return response;
    }

    @Override
    public List<CardDetailsResponseDto> customerAllcards(int customerId) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(customerId).get();
        }catch(Exception e)
        {
            throw new Exception("Invalid customer Id");
        }
        List<CardDetailsResponseDto> responseDtos=new ArrayList<>();
        for(Card card: customer.getCards())
        {
            responseDtos.add(CardDetailsResponseDto.builder()
                            .cardNo(card.getCardNo())
                            .expiryDate(card.getExpiryDate())
                            .customerName(customer.getName())
                            .cardType(card.getCardType())
                    .build());
        }
        return responseDtos;
    }
}
