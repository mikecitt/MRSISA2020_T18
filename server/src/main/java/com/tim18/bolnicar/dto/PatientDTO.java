package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Patient;

public class PatientDTO {
    private String jmbg;
    private String firstName;
    private String lastName;

    public PatientDTO() {
    }

    public PatientDTO(Patient patient) {
        this.jmbg = patient.getJmbg();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if(!(obj instanceof PatientDTO)) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) obj;

        return this.jmbg.equals(patientDTO.getJmbg());
    }
}
