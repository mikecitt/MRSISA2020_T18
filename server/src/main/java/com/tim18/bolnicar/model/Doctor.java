package com.tim18.bolnicar.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

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

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", contact='" + contact + '\'' +
                ", jmbg='" + jmbg + '\'' +
                ", active=" + active +
                '}';
    }

    //TODO: make better
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority("ROLE_DOCTOR"));
        return authorityList;
    }
}
