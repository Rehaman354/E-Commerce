package com.example.Ecommerce.Dto.Response;

import com.example.Ecommerce.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class CardDetailsResponseDto {
    String cardNo;
    String customerName;
    CardType cardType;
    Date expiryDate;
}
