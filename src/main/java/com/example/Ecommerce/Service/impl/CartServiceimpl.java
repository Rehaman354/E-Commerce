package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.CartCheckOutRequestDto;
import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.CartResponseDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Exception.CardNotFoundException;
import com.example.Ecommerce.Exception.EmptyCartException;
import com.example.Ecommerce.Exception.InvalidCustomerException;
import com.example.Ecommerce.Repositiory.CardRepository;
import com.example.Ecommerce.Repositiory.CartRepository;
import com.example.Ecommerce.Repositiory.CustomerRepository;
import com.example.Ecommerce.Repositiory.OrderClassRepository;
import com.example.Ecommerce.Service.Interfaces.CartService;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import com.example.Ecommerce.Service.Interfaces.OrderClassService;
import com.example.Ecommerce.Service.Interfaces.ProductService;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.Transformer.OrderTransformer;
import com.example.Ecommerce.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceimpl implements CartService {
    @Autowired
    ItemService itemService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrderClassService orderClassService;
    @Autowired
    OrderClassRepository orderClassRepository;
    @Autowired
    ProductService productService;
    @Override
    public CartResponseDto addItemToCart(ItemRequestDto itemRequestDto) throws Exception {
        Item item;
         try{
             item=itemService.createItem(itemRequestDto);
         }catch(Exception e) {
             throw new Exception(e.getMessage());
         }
         //we got item then add it to the cart
        Customer customer= customerRepository.findById(itemRequestDto.getCustomerId()).get();
        Cart cart=customer.getCart();
        int newCost=cart.getTotalCartCost()+(item.getProduct().getPrice()*item.getRequiredQuantity());
        cart.setTotalCartCost(newCost);
        item.setCart(cart);
        cart.getItems().add(item);

         cart.setNoOfItems(cart.getItems().size());
         Cart savedCart=cartRepository.save(cart);


         //prepare cartresponsedto
        List<ItemResponseDto> responseDtoList=new ArrayList<>();
        for(Item eachitem:savedCart.getItems())
        {
            responseDtoList.add(ItemTransformer.itemToItemRequestDto(eachitem));
        }
        return CartResponseDto.builder()
                .totalCartCost(savedCart.getTotalCartCost())
                .noOfItems(savedCart.getNoOfItems())
                .customerName(customer.getName())
                .itemList(responseDtoList)
                .build();
    }

    @Override
    public OrderResponseDto checkOutCart(CartCheckOutRequestDto cartCheckOutRequestDto) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(cartCheckOutRequestDto.getCustomerId()).get();
        }catch(Exception e)
        {
            throw new InvalidCustomerException("Customer does not exist");
        }
        Card card;
        try{
            card=cardRepository.findByCardNo(cartCheckOutRequestDto.getCardNo());
        }catch(Exception e)
        {
            throw new CardNotFoundException("Card does not Exist");
        }
        Date date=new Date();//present date
        if(card==null||card.getCustomer()!=customer||card.getCvv()!= cartCheckOutRequestDto.getCvv()||card.getExpiryDate().compareTo(date)<0)
            throw new Exception("card is not valid or details are mismatching");
        //all details are valid prepare the order and bring the cart
        Cart cart=customer.getCart();
        if(cart.getItems().size()==0)
            throw new EmptyCartException("Cart is empty,pls add items to order!");
        //decrease items product quantities
        int noOfItems=cart.getItems().size();
        for(int i=0;i<noOfItems;i++)
        {
            Item item=cart.getItems().get(i);
            int productQuantity=item.getProduct().getQuantity();
            int orderQuantity= item.getRequiredQuantity();
            int newQuantity=productQuantity-orderQuantity;
            if(newQuantity<0) {
                for(int j=0;j<i;j++)
                {
                    //if exception occurs ,I am resetting the quantity of already set products quantity
                    Item itemj=cart.getItems().get(j);
                    int presentQuant=itemj.getProduct().getQuantity();
                    int resetQuant=presentQuant+itemj.getRequiredQuantity();
                    itemj.getProduct().setQuantity(resetQuant);
                }
                throw new Exception("Sorry, Required Quantity of product not available! order can't be placed");
            }
            item.getProduct().setQuantity(newQuantity);
            productService.setProductStatusByQuantity(item.getProduct());
        }
        //if all items are available place order
        OrderClass presentOrder=orderClassService.placeOrder(customer,card,date);
        //all cart items will be assigned order and removed from cart
        for(Item item:cart.getItems())
        {
            item.setOrderClass(presentOrder);
        }
        //change in customer
        customer.getOrders().add(presentOrder);
        //change in cart attributes (reset cart)
        cart.setTotalCartCost(0);
        cart.setNoOfItems(0);
        cart.setItems(new ArrayList<>());
        customer.setCart(cart);
        //finally save order
        OrderClass savedOrder=orderClassRepository.save(presentOrder);
       //prepare Orderresponsedto
        OrderResponseDto response=OrderTransformer.OrderToOrderResponseDto(presentOrder);
        return response;
    }

}
