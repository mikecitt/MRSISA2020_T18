package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.Doctor;

import java.util.List;

import java.util.Objects;

public class DoctorDTO {
    private int id;
    private String firstName;
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TimeIntervalDTO> freeIntervals;

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
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

    public List<TimeIntervalDTO> getFreeIntervals() {
        return freeIntervals;
    }

    public void setFreeIntervals(List<TimeIntervalDTO> freeIntervals) {
        this.freeIntervals = freeIntervals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDTO doctorDTO = (DoctorDTO) o;
        return id == doctorDTO.id;
    }
}
