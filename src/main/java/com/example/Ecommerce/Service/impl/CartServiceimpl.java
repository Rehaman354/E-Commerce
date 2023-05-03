package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.CartCheckOutRequestDto;
import com.example.Ecommerce.Dto.Request.DeleteItemRequestDto;
import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.CartResponseDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Exception.CardNotFoundException;
import com.example.Ecommerce.Exception.EmptyCartException;
import com.example.Ecommerce.Exception.InvalidCustomerException;
import com.example.Ecommerce.Exception.ItemNotFoundException;
import com.example.Ecommerce.Repositiory.*;
import com.example.Ecommerce.Service.Interfaces.CartService;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import com.example.Ecommerce.Service.Interfaces.OrderClassService;
import com.example.Ecommerce.Service.Interfaces.ProductService;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.Transformer.OrderTransformer;
import com.example.Ecommerce.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceimpl implements CartService {
    @Autowired
    JavaMailSender emailSender;
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
    @Autowired
    ItemRepository itemRepository;
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
        //checking all items can be available or not
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
        //change in customer attributes
        customer.getOrders().add(presentOrder);
        //reset cart
//        resetCart(cart);
        cart.setTotalCartCost(0);
        cart.setNoOfItems(0);
        cart.setItems(new ArrayList<>());

        //finally save order
        OrderClass savedOrder=orderClassRepository.save(presentOrder);
       //prepare Orderresponsedto
        OrderResponseDto response=OrderTransformer.OrderToOrderResponseDto(savedOrder);
        for(Item item: savedOrder.getItems()){
            item.setCart(null);
            itemRepository.save(item);
        }
        //send mail to the customer about ordder details
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplyaryan999@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order details");
        StringBuilder itemNames=new StringBuilder();
        for(Item item:savedOrder.getItems())
        {
            itemNames.append(item.getProduct().getName()+" ");
        }
        message.setText("Dear "+customer.getName()+", Your Order of value $"+savedOrder.getTotalOrderValue()+", with an Order referenceId "+savedOrder.getOrderNo()+" has been Placed for the items "+itemNames.toString()+". Your Order will be shipped soon. "+"Thank for shopping with us!");
        emailSender.send(message);
        return response;
    }

    @Override
    public List<ItemResponseDto> allItemsInCart(int customerId) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(customerId).get();
        }catch(Exception e) {
            throw new InvalidCustomerException("Customer does not exist");
        }
        Cart cart=customer.getCart();
        List<ItemResponseDto> responseDtos=new ArrayList<>();
       for(Item item: cart.getItems())
       {
           responseDtos.add(ItemTransformer.itemToItemRequestDto(item));
       }
       return responseDtos;
    }

    @Override
    public ItemResponseDto removeItemInCart(DeleteItemRequestDto deleteItemRequestDto) throws Exception {
        Customer customer;
        try{
            customer=customerRepository.findById(deleteItemRequestDto.getCustomerId()).get();
        }catch(Exception e) {
            throw new InvalidCustomerException("Customer does not exist");
        }
        Item item;
        try {
            item = itemRepository.findById(deleteItemRequestDto.getItemid()).get();
        }catch(Exception e) {
            throw new ItemNotFoundException("Item does not exist");
        }
        if(item==null||item.getCart().getCustomer()!=customer)
            throw new Exception("Item not found in cart of customer");
        //all details are ok ,then delete the item in cart and save the cart
        Cart cart=customer.getCart();
        int noofItems=cart.getNoOfItems();
        cart.setNoOfItems(noofItems-1);
        int itemCost=item.getRequiredQuantity()*item.getProduct().getPrice();
        int cartTotal= cart.getTotalCartCost();
        cart.setTotalCartCost(cartTotal-itemCost);
        cart.getItems().remove(item);
        cartRepository.save(cart);
        itemRepository.deleteById(item.getId());
        //prepare response after successful deletion
        return ItemTransformer.itemToItemRequestDto(item);
    }

    public void resetCart(Cart cart){

        cart.setTotalCartCost(0);
        for(Item item: cart.getItems()){
            item.setCart(null);
        }
        cart.setNoOfItems(0);
        cart.getItems().clear();
    }

}
