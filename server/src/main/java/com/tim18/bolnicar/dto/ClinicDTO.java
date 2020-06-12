package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.ClinicGrade;

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

    private Integer clinicGrade;

    /*
        null can't vote, no display
        0 can vote
        > 0 cant vote, just display
     */
    private Integer patientGrade;

    public ClinicDTO(Clinic clinic) {
        this.id = clinic.getId();
        this.name = clinic.getName();
        this.address = clinic.getAddress();
        this.description = clinic.getDescription();
        this.patientGrade = 0; // default no no
        this.clinicGrade = 0; //(int)(Math.random() * 100) % 5 + 1;
        //TODO: db agr
        for (ClinicGrade grade : clinic.getGrades())
            this.clinicGrade += grade.getGrade();
        if (clinic.getGrades().size() > 0)
            this.clinicGrade = (int) Math.rint(this.clinicGrade * 1.0 / clinic.getGrades().size());
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

    public Integer getClinicGrade() {
        return clinicGrade;
    }

    public void setClinicGrade(Integer clinicGrade) {
        this.clinicGrade = clinicGrade;
    }

    public Integer getPatientGrade() {
        return patientGrade;
    }

    public void setPatientGrade(Integer patientGrade) {
        this.patientGrade = patientGrade;
    }
}
