package com.example.Ecommerce.Dto.Request;

import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.Enum.ProductStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProductRequestDto {
    String productName;
    int price;
    int quantity;
    Date manufactureDate;
    Date ExpiryDate;
    ProductCategory productCategory;
    int  sellerId;
}
