package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.Clinic;

import javax.persistence.Column;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicDTO {
    private Integer id;
    private String name;
    private String address;
    private String description;

    private Double examinationPrice;
    private Integer examinationTypeId;
    private List<DoctorDTO> freeDoctors;

    public ClinicDTO(Clinic clinic) {
        this.id = clinic.getId();
        this.name = clinic.getName();
        this.address = clinic.getAddress();
        this.description = clinic.getDescription();
    }

    public ClinicDTO() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getExaminationPrice() {
        return examinationPrice;
    }

    public void setExaminationPrice(Double examinationPrice) {
        this.examinationPrice = examinationPrice;
    }

    public List<DoctorDTO> getFreeDoctors() {
        return freeDoctors;
    }

    public void setFreeDoctors(List<DoctorDTO> freeDoctors) {
        this.freeDoctors = freeDoctors;
    }

    public Integer getExaminationTypeId() {
        return examinationTypeId;
    }

    public void setExaminationTypeId(Integer examinationTypeId) {
        this.examinationTypeId = examinationTypeId;
    }
}
