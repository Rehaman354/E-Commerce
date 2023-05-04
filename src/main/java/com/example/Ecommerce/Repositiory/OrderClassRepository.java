package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.model.OrderClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderClassRepository extends JpaRepository<OrderClass,Integer> {
    @Query(value = "select * from order_class o order by o.total_order_value desc limit 1",nativeQuery = true)
    OrderClass highestValueOrder();
}
