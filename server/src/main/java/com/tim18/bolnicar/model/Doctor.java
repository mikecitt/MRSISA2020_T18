package com.tim18.bolnicar.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@DiscriminatorValue("DO")
public class Doctor extends MedicalWorker {
    @OneToMany(fetch = FetchType.LAZY)
    private Set<ExaminationType> specialization;

    public Set<ExaminationType> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Set<ExaminationType> specialization) {
        this.specialization = specialization;
    }
}
