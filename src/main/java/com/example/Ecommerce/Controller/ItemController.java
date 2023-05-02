package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.UpdateItemRequestDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    //decrease or increase quantity of item in a particular customer cart api
    //if customer and item does not exist, throw exception
    //note : prices of cart and items will change
    @Autowired
    ItemService itemService;
    @PutMapping("/update")
    public ItemResponseDto updateItem(@RequestBody UpdateItemRequestDto updateItemRequestDto) throws Exception
    {
        return itemService.updateItem(updateItemRequestDto);
    }
}
