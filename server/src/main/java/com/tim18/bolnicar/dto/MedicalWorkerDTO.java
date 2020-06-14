package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.MedicalWorker;

public class MedicalWorkerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String jmbg;

    public MedicalWorkerDTO(MedicalWorker medicalWorker) {
        this.id = medicalWorker.getId();
        this.firstName = medicalWorker.getFirstName();
        this.lastName = medicalWorker.getLastName();
        this.emailAddress = medicalWorker.getEmailAddress();
        this.jmbg = medicalWorker.getJmbg();
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
