package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.model.Item;
import com.example.Ecommerce.model.OrderClass;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class OrderTransformer {
    public static OrderResponseDto OrderToOrderResponseDto(OrderClass presentOrder) {
        List<Item> items=presentOrder.getItems();
        List<ItemResponseDto> responseDtos=new ArrayList<>();
        for(Item item:items)
            responseDtos.add(ItemTransformer.itemToItemRequestDto(item));
        return OrderResponseDto.builder()
                .orderDate(presentOrder.getOrderdate())
                .orderNo(presentOrder.getOrderNo())
                .totalOrderValue(presentOrder.getTotalOrderValue())
                .cardUsed(presentOrder.getCardUsed())
                .OrderedItems(responseDtos)
                .customerName(presentOrder.getCustomer().getName())
        .build();
    }
}
