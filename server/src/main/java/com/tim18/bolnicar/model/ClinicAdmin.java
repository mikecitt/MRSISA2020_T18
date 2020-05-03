package com.tim18.bolnicar.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority("ROLE_CLINIC_ADMIN"));
        return authorityList;
    }
}
