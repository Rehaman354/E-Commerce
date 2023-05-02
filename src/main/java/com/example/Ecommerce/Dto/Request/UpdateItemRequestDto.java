package com.example.Ecommerce.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class UpdateItemRequestDto {
    int customerId;
    int itemId;
    int updateQuantity;
}
