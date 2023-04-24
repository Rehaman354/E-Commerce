package com.example.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data//it contains getter,setter,RequiredArgsconstructor,ToString,EqualAndHashCode annotataions
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customer")
@FieldDefaults(level = AccessLevel.PRIVATE)//making all attributes or fields private default
public class OrderClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String orderNo;
    int totalOrderValue;
    Date orderdate;
    String cardUsed;

    //relations
    @OneToMany(mappedBy = "orderClass",cascade = CascadeType.ALL)
    List<Item> items=new ArrayList<>();

    @ManyToOne
    @JoinColumn
    Customer customer;
}
