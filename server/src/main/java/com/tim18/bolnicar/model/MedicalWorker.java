package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class MedicalWorker extends User {
    @ManyToOne
    protected Clinic clinic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public Set<TimeOff> getActiveCalendar() {
        Set<TimeOff> active = new HashSet<TimeOff>();

        for(TimeOff timeOff : calendar) {
            if(timeOff.isActive() != null && timeOff.isActive()) {
                active.add(timeOff);
            }
        }

        return active;
    }

    public void setCalendar(Set<TimeOff> calendar) {
        this.calendar = calendar;
    }

    public void addTimeOff(TimeOff timeOff) { this.calendar.add(timeOff); }
}
