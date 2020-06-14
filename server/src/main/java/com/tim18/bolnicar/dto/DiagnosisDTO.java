package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.MedicalDiagnosis;


public class DiagnosisDTO {
    private Integer id;
    private String name;

    public DiagnosisDTO() {

    }

    public DiagnosisDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public DiagnosisDTO(MedicalDiagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.name = diagnosis.getName();
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
