package com.tim18.bolnicar.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String description;

    @OneToOne
    private MedicalDiagnosis diagnosis;

    @OneToMany
    private Set<Recipe> recipes;

    @OneToOne
    private Appointment appointment;
}
