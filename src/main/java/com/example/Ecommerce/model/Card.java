package com.example.Ecommerce.model;

import com.example.Ecommerce.Enum.CardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@Table(name="card")
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
@Builder
public class Card {
    //assuming card as immutable,no updates or modifications are not allowed ,only create,fetch,delete
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int cvv;
    @Column(unique = true,nullable = false)
    String cardNo;
    Date expiryDate;
    @Enumerated(EnumType.STRING)
    CardType cardType;//enum

    //relations
    @ManyToOne
    @JoinColumn
    Customer customer;
}
