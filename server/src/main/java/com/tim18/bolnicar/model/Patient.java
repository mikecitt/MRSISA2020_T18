package com.tim18.bolnicar.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Patient extends User {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalReport> medicalRecord;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<Appointment> appointments;

    public Patient() {
        this.medicalRecord = new HashSet<MedicalReport>();
        this.appointments = new HashSet<Appointment>();
    }

    public Set<MedicalReport> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(Set<MedicalReport> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    //TODO: make better
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority("ROLE_PATIENT"));
        return authorityList;
    }
}
