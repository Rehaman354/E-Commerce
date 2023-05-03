package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerDetailsResponseDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;

import java.util.List;

public interface SellerService {

    SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws Exception;

    SellerDetailsResponseDto getByEmail(String email) throws Exception;

    SellerDetailsResponseDto getById(int sellerId) throws Exception;

    List<SellerDetailsResponseDto> getAllSellers();

    List<SellerDetailsResponseDto> getSellersOfParticularAge(int age);

    SellerDetailsResponseDto updateAddressById(int id, String newAddress) throws Exception;

    SellerDetailsResponseDto deleteByEmail(String email) throws Exception;
}
