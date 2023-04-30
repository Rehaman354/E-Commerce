package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Exception.InvalidCustomerException;
import com.example.Ecommerce.Exception.ProductNotFoundException;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Repositiory.ItemRepository;
import com.example.Ecommerce.Repositiory.ProductRepository;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.model.Customer;
import com.example.Ecommerce.model.Item;
import com.example.Ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item createItem(ItemRequestDto itemRequestDto) throws Exception{
        Customer customer;
        try
        {
          customer=customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }catch(Exception e)
        {
            throw new InvalidCustomerException("Customer does not exist!");
        }
        Product product;
        try
        {
            product=productRepository.findById(itemRequestDto.getProductId()).get();
        }catch(Exception e)
        {
            throw new ProductNotFoundException("product does not exist!");
        }
        if(product.getQuantity()<itemRequestDto.getRequiredQuantity())
            throw new Exception("Required Quantity is not available");
        //everything is checked now create item
        Item item= ItemTransformer.itemRequestDtoToItem(itemRequestDto);
        item.setProduct(product);
        product.getItems().add(item);
        return itemRepository.save(item);
    }
}
