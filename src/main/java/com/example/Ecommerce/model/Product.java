package com.example.Ecommerce.model;

import com.example.Ecommerce.Enum.ProductCategory;
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
@Table(name="customer")
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int price;
    int quantity;
    Date manufactureDate;
    Date ExpiryDate;

    @Enumerated(EnumType.STRING)
    ProductCategory productCategory;//enum

    //relations
    @ManyToOne
    @JoinColumn
    Seller seller;
}
