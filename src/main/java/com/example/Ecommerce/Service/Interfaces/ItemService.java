package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Request.UpdateItemRequestDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.model.Item;

public interface ItemService {
    Item createItem(ItemRequestDto itemRequestDto) throws Exception;

    ItemResponseDto updateItem(UpdateItemRequestDto updateItemRequestDto) throws Exception;
}
