package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Drug;
import com.tim18.bolnicar.model.Recipe;

import javax.persistence.*;
import java.util.Set;

public class RecipeDTO {
    private Integer id;
    private Set<Drug> drugs;
    // private Boolean sealed;


    public RecipeDTO(Integer id, Set<Drug> drugs) {
        this.id = id;
        this.drugs = drugs;
    }

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.drugs = recipe.getDrugs();
    }

    public RecipeDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }
}
