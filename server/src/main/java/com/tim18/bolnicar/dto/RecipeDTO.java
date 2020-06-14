package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Drug;
import com.tim18.bolnicar.model.Recipe;

import javax.persistence.*;
import java.util.Set;

public class RecipeDTO {
    private Integer id;
    private Drug drug;
    private Boolean sealed;
    private String givenBy;
    private Integer appointmentId;


    public RecipeDTO(Integer id, Drug drug, Boolean sealed, String givenBy, Integer appointmentId) {
        this.id = id;
        this.drug = drug;
        this.sealed = sealed;
        this.givenBy = givenBy;
        this.appointmentId = appointmentId;
    }

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.drug = recipe.getDrug();
        this.sealed = recipe.getSealed();
    }

    public RecipeDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Boolean getSealed() {
        return sealed;
    }

    public void setSealed(Boolean sealed) {
        this.sealed = sealed;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
}
