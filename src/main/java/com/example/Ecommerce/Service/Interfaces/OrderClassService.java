package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.DirectOrderRequestDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.model.Card;
import com.example.Ecommerce.model.Customer;
import com.example.Ecommerce.model.OrderClass;

import java.util.Date;
import java.util.List;

public interface OrderClassService {
    public OrderClass placeOrder(Customer customer, Card card , Date date);

    OrderResponseDto placeOrder(DirectOrderRequestDto directOrderRequestDto) throws Exception;

    List<OrderResponseDto> getAllOrdersOfCustomer(int id) throws Exception;

    List<OrderResponseDto> recent5();
}
