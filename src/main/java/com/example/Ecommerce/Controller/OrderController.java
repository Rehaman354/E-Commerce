package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.DirectOrderRequestDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Service.Interfaces.OrderClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderClassService orderClassService;
    //create api to order individually just from customerid,productid,quantity,card
    @PostMapping("/placeDirectOrder")
    public OrderResponseDto placeOrder(@RequestBody DirectOrderRequestDto directOrderRequestDto) throws Exception
    {
        return orderClassService.placeOrder(directOrderRequestDto);
    }
    //get all orders of the customer
    //get the recent 5 orders
    //delete an order from orderlist
    //select order and customer with highest value in orders

}
