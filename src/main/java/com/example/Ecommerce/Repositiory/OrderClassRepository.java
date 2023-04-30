package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.model.OrderClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderClassRepository extends JpaRepository<OrderClass,Integer> {
}
