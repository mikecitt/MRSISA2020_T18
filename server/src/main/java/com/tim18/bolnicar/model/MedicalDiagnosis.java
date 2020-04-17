package com.tim18.bolnicar.model;

import javax.persistence.*;

@Entity
public class MedicalDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
