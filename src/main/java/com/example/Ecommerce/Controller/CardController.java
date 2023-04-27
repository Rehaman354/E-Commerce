package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.Service.Interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    //get all visa cards
    //get all master cards whose expiry is greater than 1 jan 2025
    //return card type which has maximum no of cards


}
