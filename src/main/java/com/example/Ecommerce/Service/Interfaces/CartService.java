package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.CartCheckOutRequestDto;
import com.example.Ecommerce.Dto.Request.DeleteItemRequestDto;
import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.CartResponseDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.model.Cart;

import java.util.List;

public interface CartService {
    CartResponseDto addItemToCart(ItemRequestDto itemRequestDto) throws Exception;

    OrderResponseDto checkOutCart(CartCheckOutRequestDto cartCheckOutRequestDto) throws Exception;

    List<ItemResponseDto> allItemsInCart(int customerId) throws  Exception;

    ItemResponseDto removeItemInCart(DeleteItemRequestDto deleteItemRequestDto) throws Exception;
}
