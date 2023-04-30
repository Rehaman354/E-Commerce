package com.example.Ecommerce.Dto.Response;

import com.example.Ecommerce.model.Item;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class CartResponseDto {
    int noOfItems;
    int totalCartCost;
    String customerName;
    List<ItemResponseDto> itemList;

}
