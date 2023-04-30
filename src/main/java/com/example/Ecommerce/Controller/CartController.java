package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.CartCheckOutRequestDto;
import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.CartResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Service.Interfaces.CartService;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping("/addItem")
    public CartResponseDto addItemToCart(@RequestBody ItemRequestDto itemRequestDto) throws Exception
    {
        return cartService.addItemToCart(itemRequestDto);
    }
    @PostMapping("/checkOut")
    public OrderResponseDto checkOutCart(@RequestBody CartCheckOutRequestDto cartCheckOutRequestDto) throws Exception
    {
      return cartService.checkOutCart(cartCheckOutRequestDto);
    }
    //remove an item from cart
    //view all items in cart
    //kunaljindal995@gmail.com(email integration) in checkout cart and directOrderPlace
}
