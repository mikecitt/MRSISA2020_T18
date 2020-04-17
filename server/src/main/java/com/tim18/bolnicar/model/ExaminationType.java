package com.tim18.bolnicar.model;

import javax.persistence.*;

@Entity
public class ExaminationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;
}
