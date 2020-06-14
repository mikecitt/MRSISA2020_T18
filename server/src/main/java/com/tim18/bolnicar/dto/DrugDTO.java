package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Drug;

public class DrugDTO {
    private Integer id;
    private String name;

    public DrugDTO() {

    }

    public DrugDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public DrugDTO(Drug drug) {
        this.id = drug.getId();
        this.name = drug.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
