package com.tim18.bolnicar.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

//TODO: naslediti kao celu tabelu?

@Entity
public class ClinicAdmin extends User {
    @ManyToOne
    private Clinic clinic;
}
