package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.ProductRequestDto;
import com.example.Ecommerce.Dto.Response.ProductResponseDto;
import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.Enum.ProductStatus;
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
    //get all products of seller by id
    @GetMapping("/sellerProducts/{id}")
    public List<ProductResponseDto> getAllProductsOfSeller(@PathVariable("id") int id) throws Exception{
        return productService.getAllProductsOfSeller(id);
    }
    //delete product by id
    @DeleteMapping("/deleteById/{id}")
    public ProductResponseDto deleteProductById(@PathVariable("id") int id) throws Exception{
        return productService.deleteProductById(id);
    }

    //return top 5 cheapest products
    @GetMapping("/top5Cheapest")
    public List<ProductResponseDto> top5Cheapest() {
        return productService.top5Cheapest();
    }

    //return top 5 costliest products
    @GetMapping("/top5Costliest")
    public List<ProductResponseDto> top5Costliest() {
        return productService.top5Costliest();
    }

    //return all  Products
    @GetMapping("/getAll")
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProducts();
    }

    //return all Products belongs to particular category
    @GetMapping("/getAllProductsOfCategory/{category}")
    public List<ProductResponseDto> getAllProductsOfCategory(@PathVariable("category") ProductCategory category){
        return productService.getAllProductsOfCategory(category);
    }

    //return all "available" ,Or "out_Of_Stock",or "Only few left" Products
    @GetMapping("/getProductsBasedOnAvailability/{status}")
    public List<ProductResponseDto> getProductsBasedOnAvailability(@PathVariable("status")ProductStatus status){
        return productService.getProductsBasedOnAvailability(status);
    }

    //return products belong to certain category and below particular price
    @GetMapping("/get/{price}/{category}")
    public List<ProductResponseDto> getAllProductsBelowPriceAndOfCategory(@PathVariable("price") int price,
                                                               @PathVariable("category") String  productCategory)
    {
        return productService.getAllProductsBelowPriceAndOfCategory(price ,productCategory);
    }

}
