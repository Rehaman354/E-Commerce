package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.Dto.Request.ProductRequestDto;
import com.example.Ecommerce.Dto.Response.ProductResponseDto;
import com.example.Ecommerce.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductTransformer {
    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto)
    {
        return Product.builder()
                .productCategory(productRequestDto.getProductCategory())
                .expiryDate(productRequestDto.getExpiryDate())
                .name(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .manufactureDate(productRequestDto.getManufactureDate())
                .quantity(productRequestDto.getQuantity())
                .build();
    }

    public static ProductResponseDto productToProductResponseDto(Product product)
    {
        return ProductResponseDto.builder()
                .sellerName(product.getSeller().getName())
                .expiryDate(product.getExpiryDate())
                .manufactureDate(product.getManufactureDate())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .productName(product.getName())
                .build();
    }
}
