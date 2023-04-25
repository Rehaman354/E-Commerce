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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int age;
    String mobNo;
    @Column(unique = true)
    String email;
    String address;
    boolean premium;//tells he is premium member or not

    //relations
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Card> cards=new ArrayList<>();

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<OrderClass> orders=new ArrayList<>();



}
