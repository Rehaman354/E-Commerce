package com.example.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customer")
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int age;
    String mobNo;
    String email;
    String address;
    String enterprise;

    //relations
    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    List<Product> products=new ArrayList<>();
}
