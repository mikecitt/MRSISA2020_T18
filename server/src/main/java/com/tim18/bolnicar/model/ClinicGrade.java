package com.tim18.bolnicar.model;

import javax.persistence.*;

//TODO: no in patient?

@Entity
public class ClinicGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Appointment appointment;

    @ManyToOne
    private Clinic clinic;

    // between 1 and 10
    @Column(nullable = false)
    private Integer grade;
}
