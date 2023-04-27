package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.CardRequestDto;
import com.example.Ecommerce.Dto.Response.CardResponseDto;

public interface CardService {
    CardResponseDto addcard(CardRequestDto cardRequestDto) throws Exception;
}
