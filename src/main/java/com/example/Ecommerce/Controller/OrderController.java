package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.DirectOrderRequestDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Service.Interfaces.OrderClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getAllOrdersOfCustomer")
    public List<OrderResponseDto> getAllOrdersOfCustomer(@RequestParam("id") int id) throws Exception{
        return orderClassService.getAllOrdersOfCustomer(id);
    }
    //get the recent 5 orders
    @GetMapping("/recent5")
    public List<OrderResponseDto> getRecent5(){
        return orderClassService.recent5();
    }
    //select order and customer with highest value in orders
    @GetMapping("/highestValueOrder")
    public OrderResponseDto highestValueOrder(){
        return orderClassService.highestValueOrder();
    }

}
