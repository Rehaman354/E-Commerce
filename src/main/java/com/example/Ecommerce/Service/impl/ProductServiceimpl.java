package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.ProductRequestDto;
import com.example.Ecommerce.Dto.Response.ProductResponseDto;
import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.Enum.ProductStatus;
import com.example.Ecommerce.Exception.InvalidSellerException;
import com.example.Ecommerce.Repositiory.ProductRepository;
import com.example.Ecommerce.Repositiory.SellerRepository;
import com.example.Ecommerce.Service.Interfaces.ProductService;
import com.example.Ecommerce.Transformer.ProductTransformer;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws Exception{
        Seller seller;
        try{
            seller=sellerRepository.findById(productRequestDto.getSellerId()).get();
        }catch(Exception e)
        {
            throw new InvalidSellerException("seller does not exist for product");
        }
        Product product= ProductTransformer.productRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);
        if(product.getQuantity()>10)
            product.setProductStatus(ProductStatus.AVAILABLE);
        else if(product.getQuantity()>0 && product.getQuantity()<=10)
            product.setProductStatus(ProductStatus.ONLY_FEW_LEFT);
        else
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        //add product to seller product list
        seller.getProducts().add(product);
        //save the seller ,it will automatically saves child(product)
        sellerRepository.save(seller);
        //create and return response
        return ProductTransformer.productToProductResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> allProductsOfParticularCategory(ProductCategory category) {
      List<Product> products=productRepository.findByProductCategory(category);
      List<ProductResponseDto> productResponseDtos=new ArrayList<>();
      for(Product product:products)
      {
         productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
      }
      return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getAllProductsBelowPriceAndOfCategory(int price, String productCategory) {
           List<Product> products=productRepository.getAllProductsBelowPriceAndOfCategory(price,productCategory);
           List<ProductResponseDto> responseDtos=new ArrayList<>();
           for(Product product :products)
           {
              responseDtos.add(ProductTransformer.productToProductResponseDto(product));
           }
           return responseDtos;
    }
}
