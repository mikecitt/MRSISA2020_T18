package com.tim18.bolnicar.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ClinicAdmin extends User {
    @ManyToOne
    private Clinic clinic;

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
