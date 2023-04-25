package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;
import com.example.Ecommerce.Exception.DuplicateEmailException;
import com.example.Ecommerce.Repositiory.SellerRepository;
import com.example.Ecommerce.Service.Interfaces.SellerService;
import com.example.Ecommerce.Transformer.SellerTransformer;
import com.example.Ecommerce.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceimpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws Exception {

       if(sellerRepository.findByEmail(sellerRequestDto.getEmail())!=null)
        throw new DuplicateEmailException("Email already exist!");
       //if email not present
        Seller seller= SellerTransformer.sellerRequestDtoToSeller(sellerRequestDto);
        sellerRepository.save(seller);
        SellerResponseDto sellerResponseDto=SellerTransformer.sellerToSellerResponseDto(seller);
        return sellerResponseDto;
    }
}
