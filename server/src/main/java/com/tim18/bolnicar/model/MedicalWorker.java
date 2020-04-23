package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class MedicalWorker extends User {
    @ManyToOne
    protected Clinic clinic;

    @OneToMany
    protected Set<TimeOff> calendar; // change name...

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Set<TimeOff> getCalendar() {
        return calendar;
    }

    public void setCalendar(Set<TimeOff> calendar) {
        this.calendar = calendar;
    }
}