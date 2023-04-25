package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.ProductRequestDto;
import com.example.Ecommerce.Dto.Response.ProductResponseDto;
import com.example.Ecommerce.Enum.ProductCategory;

import java.util.List;

public interface ProductService{

    ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws Exception;

    List<ProductResponseDto> allProductsOfParticularCategory(ProductCategory category);
}
