package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerDetailsResponseDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;
import com.example.Ecommerce.Service.Interfaces.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

  @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto)
  {
      try {
          SellerResponseDto response = sellerService.addSeller(sellerRequestDto);
          return new ResponseEntity(response,HttpStatus.CREATED);
      }catch(Exception e)
      {
          return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
      }
  }
    //get a seller by email
    @GetMapping("/getByEmail")
    public SellerDetailsResponseDto getByEmail(@RequestParam("email") String email) throws Exception{
      return sellerService.getByEmail(email);
    }
    //get seller by id
    @GetMapping("/getById/{id}")
    public SellerDetailsResponseDto getById(@PathVariable("id") int sellerId) throws Exception {
      return sellerService.getById(sellerId);
    }
    // get all sellers
    @GetMapping("/getAll")
    public List<SellerDetailsResponseDto> getAllSellers(){
      return sellerService.getAllSellers();
    }
    //get all sellers by particular age
    @GetMapping("/getSellersOfParticularAge/{age}")
    public List<SellerDetailsResponseDto> getSellersOfParticularAge(@PathVariable("age") int age){
      return sellerService.getSellersOfParticularAge(age);
    }
    //update address of seller by id
    @PutMapping("/updateAddress")
    public SellerDetailsResponseDto updateAddressById(@RequestParam("id") int id,@RequestParam("address") String newAddress) throws Exception {
        return sellerService.updateAddressById(id, newAddress);
    }
    //delete seller by email
    @DeleteMapping("/deleteByEmail")
    public SellerDetailsResponseDto deleteByEmail(@RequestParam("email") String email) throws Exception{
      return sellerService.deleteByEmail(email);
    }




}
