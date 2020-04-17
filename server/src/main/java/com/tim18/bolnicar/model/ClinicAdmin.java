package com.tim18.bolnicar.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

//TODO: naslediti kao celu tabelu?

@Entity
public class ClinicAdmin {
    @ManyToOne
    private Clinic clinic;
}
