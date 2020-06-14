package com.tim18.bolnicar.dto;

import java.util.List;
import java.util.Objects;

public class MedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String jmbg;
    private Integer patientId;
    private List<MedicalReportDTO> medicalReports;

    public MedicalRecordDTO(String firstName, String lastName, String jmbg,
                            List<MedicalReportDTO> medicalReports, Integer patientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.medicalReports = medicalReports;
        this.patientId = patientId;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecordDTO that = (MedicalRecordDTO) o;
        return Objects.equals(jmbg, that.jmbg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbg);
    }
}
