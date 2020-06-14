package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.DoctorGrade;

import java.util.List;

import java.util.Objects;

public class DoctorDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String jmbg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TimeIntervalDTO> freeIntervals;

    private Integer grade;

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.grade = 0;

        for (DoctorGrade grade : doctor.getGrades()) {
            this.grade += grade.getGrade();
        }

        this.emailAddress = doctor.getEmailAddress();
        this.jmbg = doctor.getJmbg();

        if (doctor.getGrades().size() > 0)
            this.grade = (int)Math.rint(this.grade * 1.0 / doctor.getGrades().size());
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDTO doctorDTO = (DoctorDTO) o;
        return id == doctorDTO.id;
    }
}
