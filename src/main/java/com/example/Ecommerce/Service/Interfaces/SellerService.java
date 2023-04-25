package com.example.Ecommerce.Service.Interfaces;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;

public interface SellerService {

    SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws Exception;
}
