package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.Exception.DuplicateEmailException;
import com.example.Ecommerce.Exception.DuplicateMobileException;

public interface CustomerService {
    CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws DuplicateEmailException, DuplicateMobileException;
}
