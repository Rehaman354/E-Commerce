package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerDetailResponseDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.model.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerTransformer {
    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto)
    {
        Customer customer= Customer.builder()
                .age(customerRequestDto.getAge())
                .mobNo(customerRequestDto.getMobNo())
                .email(customerRequestDto.getEmail())
                .name(customerRequestDto.getName())
                .address(customerRequestDto.getAddress())
                .build();
        return customer;
    }

    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer) {
        return CustomerResponseDto.builder()
                .name(customer.getName())
                .message("Congrats "+customer.getName()+" , you have been added!")
        .build();
    }

    public static CustomerDetailResponseDto customerToCustomerDetailResponseDto(Customer customer) {
        return CustomerDetailResponseDto.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobNo(customer.getMobNo())
                .build();
    }
}
