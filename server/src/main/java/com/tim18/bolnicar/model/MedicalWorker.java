package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class MedicalWorker extends User {
    @ManyToOne
    private Clinic clinic;

    @OneToMany
    private Set<TimeOff> calendar; // change name...
}
