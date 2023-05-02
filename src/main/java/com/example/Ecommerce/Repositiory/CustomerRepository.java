package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);
    Customer findByMobNo(String mobNo);

    @Query(value = "select * from customer c where c.age>:age",nativeQuery = true)
    List<Customer> getCustomersGreaterThanAge(int age);

    @Query(value = "SELECT DISTINCT c.* FROM customer c JOIN card ca ON c.id = ca.customer_id WHERE ca.card_type =:typeOfCard",nativeQuery = true)
    List<Customer> getAllCustomersWithCardType(String typeOfCard);
}
