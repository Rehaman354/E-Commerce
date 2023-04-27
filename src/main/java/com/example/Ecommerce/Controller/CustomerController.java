package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.Service.Interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @PostMapping("/add")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerRequestDto) throws Exception
    {
        return customerService.addCustomer(customerRequestDto);
    }

    //view all customers
    //get a customer by email/mobile
    //get all customers whose age >25
    //get all customers who use visa card
    //update customer info by email
    //delete a customer by email or mobile(take both inputs and check for which is not null accordingly delete)
         //if both are null throw exception

}
