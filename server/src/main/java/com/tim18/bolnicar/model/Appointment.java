package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private Date datetime;

    @Column
    private Integer duration;

    // positive percents
    @Column(nullable = false)
    private Double discount;

    @OneToOne(fetch = FetchType.EAGER)
    private Room room;

    @OneToOne(fetch = FetchType.EAGER)
    private ExaminationType type;

    @OneToOne(fetch = FetchType.LAZY)
    private MedicalReport report;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    private MedicalWorker doctor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;

}
