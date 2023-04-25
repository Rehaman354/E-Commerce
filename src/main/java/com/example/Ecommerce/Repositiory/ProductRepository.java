package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.Enum.ProductCategory;
import com.example.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductCategory(ProductCategory productCategory);

}
