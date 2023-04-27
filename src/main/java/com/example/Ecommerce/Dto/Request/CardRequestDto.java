package com.example.Ecommerce.Dto.Request;

import com.example.Ecommerce.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class CardRequestDto {
     int customerId;
    int cvv;
    String cardNo;
    Date expiryDate;
    CardType cardType;//enum
}
