package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerDetailsResponseDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;
import com.example.Ecommerce.Exception.DuplicateEmailException;
import com.example.Ecommerce.Exception.DuplicateMobileException;
import com.example.Ecommerce.Exception.EmailNotFoundException;
import com.example.Ecommerce.Repositiory.ProductRepository;
import com.example.Ecommerce.Repositiory.SellerRepository;
import com.example.Ecommerce.Service.Interfaces.SellerService;
import com.example.Ecommerce.Transformer.SellerTransformer;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceimpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws Exception {

       if(sellerRepository.findByEmail(sellerRequestDto.getEmail())!=null)
        throw new DuplicateEmailException("Email already exist!");
       if(sellerRepository.findByMobNo(sellerRequestDto.getMobNo())!=null)
           throw new DuplicateMobileException("Mobile already exist");
       //if email not present
        Seller seller= SellerTransformer.sellerRequestDtoToSeller(sellerRequestDto);
        sellerRepository.save(seller);
        SellerResponseDto sellerResponseDto=SellerTransformer.sellerToSellerResponseDto(seller);
        return sellerResponseDto;
    }

    @Override
    public SellerDetailsResponseDto getByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if(seller==null)
            throw new Exception("seller does not exist with given email");
        return SellerTransformer.sellerToSellerDetailsResponseDto(seller);
    }

    @Override
    public SellerDetailsResponseDto getById(int sellerId) throws Exception {
        Seller seller;
        try {
            seller = sellerRepository.findById(sellerId).get();
        }catch(Exception e)
        {
            throw new Exception("seller with the given Id does not exist");
        }
        return SellerTransformer.sellerToSellerDetailsResponseDto(seller);
    }

    @Override
    public List<SellerDetailsResponseDto> getAllSellers() {
        List<SellerDetailsResponseDto> responseDtos=new ArrayList<>();
        for(Seller seller: sellerRepository.findAll())
        {
            responseDtos.add(SellerTransformer.sellerToSellerDetailsResponseDto(seller));
        }
        return responseDtos;
    }

    @Override
    public List<SellerDetailsResponseDto> getSellersOfParticularAge(int age) {
        List<SellerDetailsResponseDto> responseDtos=new ArrayList<>();
        List<Seller> sellers=sellerRepository.getSellersOfParticularAge(age);
        for(Seller seller:sellers){
            responseDtos.add(SellerTransformer.sellerToSellerDetailsResponseDto(seller));
        }
        return responseDtos;
    }

    @Override
    public SellerDetailsResponseDto updateAddressById(int id, String newAddress) throws Exception {
        Seller seller;
        try {
            seller = sellerRepository.findById(id).get();
        }catch(Exception e)
        {
            throw new Exception("seller with the given Id does not exist");
        }
        seller.setAddress(newAddress);
        Seller updatedSeller=sellerRepository.save(seller);
        return SellerTransformer.sellerToSellerDetailsResponseDto(updatedSeller);
    }

    @Override
    public SellerDetailsResponseDto deleteByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if(seller==null)
            throw new Exception("seller does not exist with given email");
        for(Product product:seller.getProducts())
        {
            productRepository.delete(product);
        }
        SellerDetailsResponseDto response=SellerTransformer.sellerToSellerDetailsResponseDto(seller);
        sellerRepository.delete(seller);
        return response;
    }
}
