package com.example.Ecommerce.Dto.Response;

import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.Enum.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class ProductResponseDto {
    String productName;
    int price;
    Date manufactureDate;
    Date expiryDate;
    ProductCategory productCategory;
    ProductStatus productStatus;
    String sellerName;
}
