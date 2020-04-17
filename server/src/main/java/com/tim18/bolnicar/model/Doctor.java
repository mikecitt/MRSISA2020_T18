package com.tim18.bolnicar.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Doctor extends MedicalWorker {
    @OneToMany(fetch = FetchType.LAZY)
    private Set<ExaminationType> specialization;
}
