package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Request.UpdateItemRequestDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Exception.InvalidCustomerException;
import com.example.Ecommerce.Exception.ItemNotFoundException;
import com.example.Ecommerce.Exception.ProductNotFoundException;
import com.example.Ecommerce.Repositiory.CartRepository;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Repositiory.ItemRepository;
import com.example.Ecommerce.Repositiory.ProductRepository;
import com.example.Ecommerce.Service.Interfaces.CartService;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.model.Cart;
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
    @Autowired
    CartRepository cartRepository;

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

    @Override
    public ItemResponseDto updateItem(UpdateItemRequestDto updateItemRequestDto) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(updateItemRequestDto.getCustomerId()).get();
        }catch(Exception e) {
            throw new InvalidCustomerException("Customer does not exist");
        }
        Item item;
        try {
            item = itemRepository.findById(updateItemRequestDto.getItemId()).get();
        }catch(Exception e) {
            throw new ItemNotFoundException("Item does not exist");
        }
        if(item==null||item.getCart().getCustomer()!=customer)
            throw new Exception("Item not found in cart of customer");
        //all details match now update cart and item
        int presentItemCost=item.getRequiredQuantity()*item.getProduct().getPrice();
        int updatedItemCost=updateItemRequestDto.getUpdateQuantity()*item.getProduct().getPrice();
        Cart cart=customer.getCart();
        int cartTotal= cart.getTotalCartCost();
        cart.setTotalCartCost(cartTotal-presentItemCost+updatedItemCost);
        cartRepository.save(cart);
        item.setRequiredQuantity(updateItemRequestDto.getUpdateQuantity());
        Item updatedItem=itemRepository.save(item);
        return ItemTransformer.itemToItemRequestDto(updatedItem);

    }
}
