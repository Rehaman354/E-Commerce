package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.model.Card;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardTransformer {
    public static Card cardRequestDtoToCard(CardRequestDto cardRequestDto)
    {
      return Card.builder()
              .cardNo(cardRequestDto.getCardNo())
              .cardType(cardRequestDto.getCardType())
              .cvv(cardRequestDto.getCvv())
              .expiryDate(cardRequestDto.getExpiryDate())
              .build();
    }
    public static CardResponseDto cardToCardResponseDto( Card card)
    {
        return CardResponseDto.builder()
                .customerName(card.getCustomer().getName())
                .cardNo(card.getCardNo())
                .build();
    }
}
