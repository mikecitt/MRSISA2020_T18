package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Drug;
import com.tim18.bolnicar.model.MedicalDiagnosis;

import java.util.List;

public class AppointmentSave {
    private Integer appointmentId;
    private String description;
    private List<MedicalDiagnosis> diagnosis;
    private List<Drug> recipe;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MedicalDiagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<MedicalDiagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Drug> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Drug> recipe) {
        this.recipe = recipe;
    }
}
