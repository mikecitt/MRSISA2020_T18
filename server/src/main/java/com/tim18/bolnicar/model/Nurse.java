package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("NU")
public class Nurse extends MedicalWorker {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nurse")
    private Set<Recipe> recipes;
}
