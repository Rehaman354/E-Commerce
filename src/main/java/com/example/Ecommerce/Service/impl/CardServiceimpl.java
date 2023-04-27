package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.Repositiory.CardRepository;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Service.Interfaces.CardService;
import com.example.Ecommerce.Transformer.CardTransformer;
import com.example.Ecommerce.model.Card;
import com.example.Ecommerce.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
