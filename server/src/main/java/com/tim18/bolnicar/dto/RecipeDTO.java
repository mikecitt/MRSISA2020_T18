package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Drug;
import com.tim18.bolnicar.model.Recipe;

import javax.persistence.*;
import java.util.Set;

public class RecipeDTO {
    private Integer id;
    private Drug drug;
    // private Boolean sealed;


    public RecipeDTO(Integer id, Drug drug) {
        this.id = id;
        this.drug = drug;
    }

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.drug = recipe.getDrug();
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
}
