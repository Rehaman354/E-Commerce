package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.CustomerRequestDto;
import com.example.Ecommerce.Dto.Response.CustomerDetailResponseDto;
import com.example.Ecommerce.Dto.Response.CustomerResponseDto;
import com.example.Ecommerce.Enum.CardType;
import com.example.Ecommerce.Exception.DuplicateEmailException;
import com.example.Ecommerce.Exception.DuplicateMobileException;
import com.example.Ecommerce.Exception.EmailNotFoundException;
import com.example.Ecommerce.Exception.MobileNotFoundException;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Service.Interfaces.CustomerService;
import com.example.Ecommerce.Transformer.CustomerTransformer;
import com.example.Ecommerce.model.Cart;
import com.example.Ecommerce.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<CustomerDetailResponseDto> getAllCustomers() {
        List<CustomerDetailResponseDto> responseDtos=new ArrayList<>();
       for(Customer customer:customerRepository.findAll())
       {
           responseDtos.add(CustomerTransformer.customerToCustomerDetailResponseDto(customer));
       }
       return responseDtos;
    }

    @Override
    public List<CustomerDetailResponseDto> getCustomersGreaterThanAge(int age) {
        List<Customer> customers=customerRepository.getCustomersGreaterThanAge(age);
        List<CustomerDetailResponseDto> responseDtos=new ArrayList<>();
        for(Customer customer:customers)
        {
            responseDtos.add(CustomerTransformer.customerToCustomerDetailResponseDto(customer));
        }
        return  responseDtos;
    }

    @Override
    public CustomerDetailResponseDto getCustomerByEmail(String email) throws Exception {
        Customer customer;
        try {
            customer = customerRepository.findByEmail(email);
        }catch (Exception e) {
            throw new EmailNotFoundException("Email does not exist");
        }
        return CustomerTransformer.customerToCustomerDetailResponseDto(customer);
    }

    @Override
    public CustomerDetailResponseDto getCustomerByMobNo(String mobNo) throws Exception {
        Customer customer;
        try {
            customer = customerRepository.findByMobNo(mobNo);
        }catch (Exception e) {
            throw new MobileNotFoundException("mobile does not exist");
        }
        return CustomerTransformer.customerToCustomerDetailResponseDto(customer);
    }

    @Override
    public List<CustomerDetailResponseDto> getAllCustomersWithCardType(CardType cardType) {
        String typeOfCard=cardType.toString();
        List<Customer> customers=customerRepository.getAllCustomersWithCardType(typeOfCard);
        List<CustomerDetailResponseDto> responseDtos=new ArrayList<>();
        for(Customer customer:customers)
        {
            responseDtos.add(CustomerTransformer.customerToCustomerDetailResponseDto(customer));
        }
        return responseDtos;
    }

    @Override
    public CustomerDetailResponseDto updateAddressByEmail(String email, String address) throws Exception {
        Customer customer;
        try {
            customer = customerRepository.findByEmail(email);
        }catch(Exception e){
            throw new EmailNotFoundException("email does not exist");
        }
        customer.setAddress(address);
        Customer savedCustomer=customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerDetailResponseDto(savedCustomer);
    }

    @Override
    public CustomerDetailResponseDto deleteCustomerByEmailOrMobNo(CustomerRequestDto customerRequestDto) throws Exception {
        String email=customerRequestDto.getEmail();
        String mobNo=customerRequestDto.getMobNo();
        if(email==null && mobNo==null)
            throw new Exception("Please enter valid email or mobNo to delete customer!");
        Customer customer;
        if(email!=null) {
            try {
                customer = customerRepository.findByEmail(email);
            } catch (Exception e) {
                throw new EmailNotFoundException("email does not exist");
            }
        }else {
            try {
                customer = customerRepository.findByMobNo(mobNo);
            } catch (Exception e) {
                throw new MobileNotFoundException("mobNo does not exist");
            }
        }
        CustomerDetailResponseDto response=CustomerTransformer.customerToCustomerDetailResponseDto(customer);
        customerRepository.delete(customer);
        return response;


    }
}
