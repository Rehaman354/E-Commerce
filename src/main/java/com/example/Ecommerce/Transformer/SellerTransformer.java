package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.Dto.Request.SellerRequestDto;
import com.example.Ecommerce.Dto.Response.SellerResponseDto;
import com.example.Ecommerce.model.Seller;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerTransformer {
    public static Seller sellerRequestDtoToSeller(SellerRequestDto sellerRequestDto)
    {
        Seller seller=Seller.builder().
                name(sellerRequestDto.getName()).
                mobNo(sellerRequestDto.getMobNo()).
                email(sellerRequestDto.getEmail()).
                enterprise(sellerRequestDto.getEnterprise()).
                age(sellerRequestDto.getAge()).
                address(sellerRequestDto.getAddress())
                .build();
        return seller;
    }
    public static SellerResponseDto sellerToSellerResponseDto(Seller seller)
    {
        SellerResponseDto responseDto=SellerResponseDto.builder()
                .name(seller.getName())
                .message("Congrats! "+seller.getName()+", you have been added!")
                .build();
        return responseDto;
    }
}
