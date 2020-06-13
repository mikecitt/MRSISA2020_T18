package com.tim18.bolnicar.dto;

import java.util.List;

public class MedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String jmbg;
    private List<MedicalReportDTO> medicalReports;

    public MedicalRecordDTO(String firstName, String lastName, String jmbg, List<MedicalReportDTO> medicalReports) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.medicalReports = medicalReports;
    }

    public MedicalRecordDTO() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public List<MedicalReportDTO> getMedicalReports() {
        return medicalReports;
    }

    public void setMedicalReports(List<MedicalReportDTO> medicalReports) {
        this.medicalReports = medicalReports;
    }
}
