package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.model.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTransformer {
    public static Item itemRequestDtoToItem(ItemRequestDto itemRequestDto)
    {
        return Item.builder()
                .requiredQuantity(itemRequestDto.getRequiredQuantity())
                .build();
    }
    public static ItemResponseDto itemToItemRequestDto(Item item)
    {
        return ItemResponseDto.builder()
                .priceOfOne(item.getProduct().getPrice())
                .totalPrice(item.getRequiredQuantity()*item.getProduct().getPrice())
                .productName(item.getProduct().getName())
                .quantity(item.getRequiredQuantity())
                .build();
    }
}
