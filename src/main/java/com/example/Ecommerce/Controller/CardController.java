package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardDetailsResponseDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.Enum.CardType;
import com.example.Ecommerce.Service.Interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;
    @PostMapping("/add")
    public CardResponseDto addCard(@RequestBody CardRequestDto cardRequestDto) throws Exception
    {
        return cardService.addcard(cardRequestDto);
    }
    //get all cards based on card type
    @GetMapping("/getAllCards/{cardType}")//card type he has to choose from my list ,so no exceptions of cardType not found(assume)
    public List<CardResponseDto> getAllCardsOfParticularType(@PathVariable("cardType") CardType cardType)
    {
        return cardService.getAllCardsOfParticularType(cardType);
    }
    //get all master cards whose expiry is greater than 1 jan 2025
    @GetMapping("/getCardsOfCardTypeWithGreaterExpiryDate/{cardType}/{date}")
    public  List<CardResponseDto> getCardsOfCardTypeWithGreaterExpiryDate(@PathVariable("cardType") CardType cardType,
                                                                          @PathVariable("date") String date)
    {
        return cardService.getCardsOfCardTypeWithGreaterExpiryDate(cardType,date);
    }
    //return card type which has maximum no of cards
    @GetMapping("/getMaxCardType")
    public String getCardTypeOfMaxCards()
    {
        return cardService.getCardTypeOfMaxCards();
    }
    //delete Card of customer by his id and card no
    @DeleteMapping("/delete")
    public CardResponseDto deleteCard(@RequestBody CardRequestDto cardRequestDto) throws Exception
    {
        return cardService.delete(cardRequestDto);
    }
    //get all cards of a customer
    @GetMapping("/customerAllcards/{id}")
    public List<CardDetailsResponseDto> customerAllcards(@PathVariable("id") int customerId) throws Exception
    {
        return cardService.customerAllcards(customerId);
    }


}
