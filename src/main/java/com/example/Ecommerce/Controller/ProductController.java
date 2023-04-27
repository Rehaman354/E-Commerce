package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.ProductRequestDto;
import com.example.Ecommerce.Dto.Response.ProductResponseDto;
import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.Service.Interfaces.ProductService;
import com.example.Ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    //add product
    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) throws Exception
    {
      return productService.addProduct(productRequestDto);
    }
    //get all products of a particular category
    @GetMapping("/allProducts/{category}")
    public List<ProductResponseDto> allProductsOfParticularCategory(@PathVariable("category") ProductCategory category)
    {
        return productService.allProductsOfParticularCategory(category);
    }
    //get all products of seller by email or id
    //delete product by id
    //return top 5 cheapest products
    //return top 5 costliest products
    //return all "available" Products
    //return all "out_Of_Stock" products
    //return all "Only few left" products
    //return products belong to certain category and below particular price
    @GetMapping("/get/{price}/{category}")
    public List<ProductResponseDto> getAllProductsBelowPriceAndOfCategory(@PathVariable("price") int price,
                                                               @PathVariable("category") String  productCategory)
    {
        return productService.getAllProductsBelowPriceAndOfCategory(price ,productCategory);
    }

}
