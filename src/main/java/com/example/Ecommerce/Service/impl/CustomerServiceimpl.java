package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.Exception.DuplicateEmailException;
import com.example.Ecommerce.Exception.DuplicateMobileException;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Service.Interfaces.CustomerService;
import com.example.Ecommerce.Transformer.CustomerTransformer;
import com.example.Ecommerce.model.Cart;
import com.example.Ecommerce.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceimpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws DuplicateEmailException, DuplicateMobileException {
          //transform given customerrequestDto to customer and allocate cart to him
         if(customerRepository.findByEmail(customerRequestDto.getEmail())!=null)
             throw new DuplicateEmailException("email already exists!");
        if(customerRepository.findByMobNo(customerRequestDto.getMobNo())!=null)
            throw new DuplicateMobileException("Mobile already exists!");
        Customer customer=CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);
        Cart cart= Cart.builder()
                .customer(customer)
                .build();
        customer.setCart(cart);
        Customer savedCustomer=customerRepository.save(customer);//it will save customer and cart as well
        ////transform given customerrequestDto to customer
        CustomerResponseDto responseDto=CustomerTransformer.customerToCustomerResponseDto(savedCustomer);
        return responseDto;
    }
}
