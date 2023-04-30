package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.model.Item;

public interface ItemService {
    Item createItem(ItemRequestDto itemRequestDto) throws Exception;
}
