package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.DirectOrderRequestDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Exception.CardNotFoundException;
import com.example.Ecommerce.Exception.InvalidCustomerException;
import com.example.Ecommerce.Exception.ProductNotFoundException;
import com.example.Ecommerce.Repositiory.CardRepository;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Repositiory.OrderClassRepository;
import com.example.Ecommerce.Repositiory.ProductRepository;
import com.example.Ecommerce.Service.Interfaces.OrderClassService;
import com.example.Ecommerce.Service.Interfaces.ProductService;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class OrderClassServiceimpl implements OrderClassService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderClassRepository orderClassRepository;
    public OrderClass placeOrder(Customer customer, Card card , Date date)
    {
        Cart cart=customer.getCart();
        OrderClass presentOrder=new OrderClass();
        presentOrder.setOrderNo(String.valueOf(UUID.randomUUID()));
        presentOrder.setCustomer(customer);
        presentOrder.setItems(cart.getItems());
        String cardUsed=card.getCardNo();
        int len=cardUsed.length();
        String maskedCardNo="";
        maskedCardNo="xxxxxxxxxxxx"+cardUsed.substring(len-4);
        presentOrder.setCardUsed(maskedCardNo);
        presentOrder.setOrderdate(date);
        presentOrder.setTotalOrderValue(cart.getTotalCartCost());
        return presentOrder;
    }

    @Override
    public OrderResponseDto placeOrder(DirectOrderRequestDto directOrderRequestDto) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(directOrderRequestDto.getCustomerId()).get();
        }
        catch(Exception e)
        {
            throw new InvalidCustomerException("Customer does not exist");
        }
        Product product;
        try{
            product=productRepository.findById(directOrderRequestDto.getProductId()).get();
        }catch(Exception e){
            throw new ProductNotFoundException("product does not exist!");
        }
        if(product.getQuantity()< directOrderRequestDto.getRequiredQuantity())
            throw new Exception("product Quantity is less than required");
        Card card=cardRepository.findByCardNo(directOrderRequestDto.getCardNo());
        Date date=new Date();
        if(card==null || card.getCvv()!= directOrderRequestDto.getCvv() || card.getExpiryDate().compareTo(date)<0)
        {
            throw new CardNotFoundException("Card is Invalid or wrong card entered,Pls check!");
        }

        //all checks are done
        OrderClass presentOrder=new OrderClass();
        presentOrder.setOrderNo(String.valueOf(UUID.randomUUID()));
        presentOrder.setCustomer(customer);
        String cardUsed=card.getCardNo();
        int len=cardUsed.length();
        String maskedCardNo="";
        maskedCardNo="xxxxxxxxxxxx"+cardUsed.substring(len-4);
        presentOrder.setCardUsed(maskedCardNo);
        presentOrder.setOrderdate(date);
        presentOrder.setTotalOrderValue(product.getPrice()* directOrderRequestDto.getRequiredQuantity());
        Item item= Item.builder()
                .orderClass(presentOrder)
                .requiredQuantity(directOrderRequestDto.getRequiredQuantity())
                .product(product)
                .build();
        List<Item> items=new ArrayList<>();
        items.add(item);
        presentOrder.setItems(items);
        //before returning response ,change all connected attributes in product,customer
        int presentQuantity= product.getQuantity();
        int newQuantity=presentQuantity- directOrderRequestDto.getRequiredQuantity();
        product.setQuantity(newQuantity);
        productService.setProductStatusByQuantity(product);
        customer.getOrders().add(presentOrder);
        orderClassRepository.save(presentOrder);
        //now prepare response dto
        ItemResponseDto itemResponseDto=ItemTransformer.itemToItemRequestDto(item);
        List<ItemResponseDto> orderedItems=new ArrayList<>();
        orderedItems.add(itemResponseDto);
        return OrderResponseDto.builder()
                .OrderedItems(orderedItems)
                .orderDate(date)
                .totalOrderValue(presentOrder.getTotalOrderValue())
                .orderNo(presentOrder.getOrderNo())
                .cardUsed(presentOrder.getCardUsed())
                .customerName(customer.getName())
                .build();

    }
}
