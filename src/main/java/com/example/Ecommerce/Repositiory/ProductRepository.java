package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.Dto.Response.ProductResponseDto;
import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductCategory(ProductCategory productCategory);
    @Query(value="select * from product p where p.price>:price and p.product_category=:productCategory",nativeQuery = true)
    List<Product> getAllProductsBelowPriceAndOfCategory(int price, String productCategory);

    @Query(value = "select * from product p order by p.price limit 5",nativeQuery = true)
    List<Product> top5Cheapest();
    @Query(value = "select * from product p order by p.price desc limit 5",nativeQuery = true)
    List<Product> top5Costliest();
    @Query(value = "select * from product ",nativeQuery = true)
    List<Product> getAllProducts();
    @Query(value = "select * from product p where p.product_category=:catgory",nativeQuery = true)
    List<Product> getAllProductsOfCategory(String catgory);

    @Query(value = "select * from product p where p.product_status=:productStatus",nativeQuery = true)
    List<Product> getProductsBasedOnAvailability(String productStatus);
}
