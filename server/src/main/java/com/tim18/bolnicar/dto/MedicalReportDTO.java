package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.MedicalDiagnosis;
import com.tim18.bolnicar.model.Recipe;

import javax.persistence.*;
import java.util.Set;

public class MedicalReportDTO {
    private Integer id;
    private String description;
    private Set<MedicalDiagnosis> diagnoses;
    // private Set<Recipe> recipes;
    private Integer appointmentId;

    public MedicalReportDTO(Integer id, String description, Set<MedicalDiagnosis> diagnoses, Integer appointmentId) {
        this.id = id;
        this.description = description;
        this.diagnoses = diagnoses;
        this.appointmentId = appointmentId;
    }

    public MedicalReportDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MedicalDiagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<MedicalDiagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
}
