package com.example.Ecommerce.Repositiory;

import com.example.Ecommerce.Dto.Response.CardResponseDto;
import com.example.Ecommerce.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CardRepository  extends JpaRepository<Card,Integer> {
    @Query(value = "select * from card where card_type=:cardtype and expiry_date>:date",nativeQuery = true)
    List<Card> getCardsOfCardTypeWithGreaterExpiryDate(String cardtype, String date);

    Card findByCardNo(String cardNo);
    @Query(value = "select card_type from card c GROUP BY c.card_type ORDER BY COUNT(*) DESC LIMIT 1",nativeQuery = true)
    String getCardTypeOfMaxCards();
}
