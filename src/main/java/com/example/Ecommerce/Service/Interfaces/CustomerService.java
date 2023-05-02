package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerDetailResponseDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.Enum.CardType;
import com.example.Ecommerce.Exception.DuplicateEmailException;
import com.example.Ecommerce.Exception.DuplicateMobileException;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws DuplicateEmailException, DuplicateMobileException;

    List<CustomerDetailResponseDto> getAllCustomers();

    List<CustomerDetailResponseDto> getCustomersGreaterThanAge(int age);

    CustomerDetailResponseDto getCustomerByEmail(String email) throws Exception;

    CustomerDetailResponseDto getCustomerByMobNo(String mobNo) throws Exception;

    List<CustomerDetailResponseDto> getAllCustomersWithCardType(CardType cardType);

    CustomerDetailResponseDto updateAddressByEmail(String email, String address) throws Exception;

    CustomerDetailResponseDto deleteCustomerByEmailOrMobNo(CustomerRequestDto customerRequestDto) throws Exception;
}
