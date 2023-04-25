package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;
import com.example.Ecommerce.Service.Interfaces.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    //get seller by id
    // get all sellers
    //get all sellers by particular age
    //delete seller by id
    //delete seller by email
    //update seller by id
    //update address of seller by id

}
