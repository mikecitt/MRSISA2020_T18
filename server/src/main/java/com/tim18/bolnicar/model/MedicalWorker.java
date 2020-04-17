package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class MedicalWorker {

    @Enumerated(EnumType.STRING)
    private MedicalWorkerType type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nurse")
    private Set<Recipe> recipes;

    @ManyToOne
    private Clinic clinic;
}
