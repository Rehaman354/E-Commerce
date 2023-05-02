package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerDetailResponseDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.Enum.CardType;
import com.example.Ecommerce.Service.Interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getAll")
    public List<CustomerDetailResponseDto> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }
    //get a customer by email/mobile
    @GetMapping("/getByEmail")
    public CustomerDetailResponseDto getCustomerByEmail(@RequestParam("email") String email) throws Exception
    {
        return customerService.getCustomerByEmail(email);
    }
    @GetMapping("/getByMobNo")
    public CustomerDetailResponseDto getCustomerByMobNo(@RequestParam("mobNo") String mobNo) throws Exception
    {
        return customerService.getCustomerByMobNo(mobNo);
    }
    //get all customers whose age >25
    @GetMapping("/getOlderThanAge/{age}")
    public List<CustomerDetailResponseDto> getOlderThanAge(@PathVariable("age")int age)
    {
        return customerService.getCustomersGreaterThanAge(age);
    }
    //get all customers who use visa card
    @GetMapping("/customersWithCardType")
    public List<CustomerDetailResponseDto> getAllCustomersWithCardType(@RequestParam("cardType") CardType cardType)
    {
        return customerService.getAllCustomersWithCardType(cardType);
    }
    //update customer info by email
    @PutMapping("/updateAddressByEmail")
    public CustomerDetailResponseDto updateCustomerByEmail(@RequestParam("email") String email,
                                                           @RequestParam("address")String address) throws Exception
    {
        return customerService.updateAddressByEmail(email,address);
    }
    //delete a customer by email or mobile(take both inputs and check for which is not null accordingly delete)
         //if both are null throw exception
    @DeleteMapping("/deleteByEmailOrMobNo")
    public CustomerDetailResponseDto deleteCustomerByEmailOrMobNo(@RequestBody CustomerRequestDto customerRequestDto) throws Exception
    {
        return customerService.deleteCustomerByEmailOrMobNo(customerRequestDto);
    }


}
