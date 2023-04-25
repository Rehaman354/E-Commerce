package com.example.Ecommerce.model;

import com.example.Ecommerce.Enum.CardType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@Table(name="card")
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int cvv;
    String cardNo;
    Date expiryDate;
    @Enumerated(EnumType.STRING)
    CardType cardType;//enum

    //relations
    @ManyToOne
    @JoinColumn
    Customer customer;
}
