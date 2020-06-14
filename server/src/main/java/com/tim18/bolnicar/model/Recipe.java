package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Drug drug;

    @Column
    private Boolean sealed;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Nurse nurse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Boolean getSealed() {
        return sealed;
    }

    public void setSealed(Boolean sealed) {
        this.sealed = sealed;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }
}
