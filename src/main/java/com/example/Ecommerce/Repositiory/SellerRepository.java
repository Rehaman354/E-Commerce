package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository  extends JpaRepository<Seller,Integer> {
    Seller findByEmail(String email) throws Exception;

    Seller findByMobNo(String mobNo) throws Exception;

    @Query(value = "select * from seller s where s.age=:age",nativeQuery = true)
    List<Seller> getSellersOfParticularAge(int age);
}
