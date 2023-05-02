package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.CartCheckOutRequestDto;
import com.example.Ecommerce.Dto.Request.DeleteItemRequestDto;
import com.example.Ecommerce.Dto.Request.ItemRequestDto;
import com.example.Ecommerce.Dto.Response.CartResponseDto;
import com.example.Ecommerce.Dto.Response.ItemResponseDto;
import com.example.Ecommerce.Dto.Response.OrderResponseDto;
import com.example.Ecommerce.Service.Interfaces.CartService;
import com.example.Ecommerce.Service.Interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    //add item to cart
    @PostMapping("/addItem")
    public CartResponseDto addItemToCart(@RequestBody ItemRequestDto itemRequestDto) throws Exception
    {
        return cartService.addItemToCart(itemRequestDto);
    }
    //checkout cart api
    @PostMapping("/checkOut")
    public OrderResponseDto checkOutCart(@RequestBody CartCheckOutRequestDto cartCheckOutRequestDto) throws Exception
    {
      return cartService.checkOutCart(cartCheckOutRequestDto);
    }
    //remove an item from cart
    @DeleteMapping("/removeItem")
    public ItemResponseDto removeItemInCart(@RequestBody DeleteItemRequestDto deleteItemRequestDto) throws Exception
    {
        return cartService.removeItemInCart(deleteItemRequestDto);
    }
    //view all items in cart
    @GetMapping("/getItemsInCart/{id}")
   public List<ItemResponseDto> allItemsInCart(@PathVariable("id") int customerId) throws  Exception
    {
        return cartService.allItemsInCart(customerId);
    }
}
