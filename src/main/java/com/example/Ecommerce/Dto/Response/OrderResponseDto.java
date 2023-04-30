package com.example.Ecommerce.Dto.Response;

import com.example.Ecommerce.model.Customer;
import com.example.Ecommerce.model.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class OrderResponseDto {
    String orderNo;
    int totalOrderValue;
    Date orderDate;
    String cardUsed;
    String  customerName;

    List<ItemResponseDto> OrderedItems;

}
