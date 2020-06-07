package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Nurse;

public class NurseDTO {
    private int id;
    private String firstName;
    private String lastName;

    public NurseDTO(Nurse nurse) {
        this.id = nurse.getId();
        this.firstName = nurse.getFirstName();
        this.lastName = nurse.getLastName();
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
