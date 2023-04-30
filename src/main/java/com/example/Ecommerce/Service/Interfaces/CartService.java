package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.CartCheckOutRequestDto;
import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.CartResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.model.Cart;

public interface CartService {
    CartResponseDto addItemToCart(ItemRequestDto itemRequestDto) throws Exception;

    OrderResponseDto checkOutCart(CartCheckOutRequestDto cartCheckOutRequestDto) throws Exception;
}
