package com.example.Ecommerce.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class ItemResponseDto {
    int quantity;
    String productName;
    int priceOfOne;
    int totalPrice;
}
