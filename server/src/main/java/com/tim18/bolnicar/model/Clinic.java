package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<Appointment> appointments;

    //TODO: one direction - fine?
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Room> rooms;

    //TODO: one direction?
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<MedicalWorker> workers;

    //TODO: one direction?
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<ClinicAdmin> admins;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<ClinicGrade> grades;
}
