package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardDetailsResponseDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.Enum.CardType;

import java.util.Date;
import java.util.List;

public interface CardService {
    CardResponseDto addcard(CardRequestDto cardRequestDto) throws Exception;

    List<CardResponseDto> getAllCardsOfParticularType(CardType cardType);

    List<CardResponseDto> getCardsOfCardTypeWithGreaterExpiryDate(CardType cardType, String date);

    String getCardTypeOfMaxCards();

    CardResponseDto delete(CardRequestDto cardRequestDto) throws Exception;

    List<CardDetailsResponseDto> customerAllcards(int customerId) throws Exception;
}
