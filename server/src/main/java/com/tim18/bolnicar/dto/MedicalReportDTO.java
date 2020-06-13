package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.MedicalDiagnosis;
import com.tim18.bolnicar.model.Recipe;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class MedicalReportDTO {
    private Integer id;
    private String description;
    private Set<MedicalDiagnosis> diagnoses;
    private Set<Recipe> recipes;
    private Integer appointmentId;
    private Date appointmentDate;

    public MedicalReportDTO(Integer id,
                            String description,
                            Set<MedicalDiagnosis> diagnoses,
                            Set<Recipe> recipes,
                            Integer appointmentId,
                            Date appointmentDate) {
        this.id = id;
        this.description = description;
        this.diagnoses = diagnoses;
        this.recipes = recipes;
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
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

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
