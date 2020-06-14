package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.*;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {
    private Integer id;
    private Date datetime;
    private Integer duration;
    private Double discount;
    private RoomDTO room;
    private ExaminationType type;
    private Integer medicalReportId;
    private Integer patientId;
    private String jmbg;
    private String doctor;
    private String patient;
    private String clinicName;
    private Integer clinicId;

    /*
        null can't vote, no display
        0 can vote
        > 0 cant vote, just display
     */
    private Integer patientGrade;

    public AppointmentDTO(Integer id,
                          Date datetime,
                          Integer duration,
                          Double discount,
                          Room room,
                          ExaminationType type,
                          Integer medicalReportId,
                          Integer patientId,
                          String jmbg,
                          String doctor,
                          String patient,
                          String clinicName,
                          Integer patientGrade,
                          Integer clinicId) {
        this.id = id;
        this.datetime = datetime;
        this.duration = duration;
        this.discount = discount;
        this.room = new RoomDTO(room);
        this.type = type;
        this.medicalReportId = medicalReportId;
        this.patientId = patientId;
        this.jmbg = jmbg;
        this.doctor = doctor;
        this.patient = patient;
        this.clinicName = clinicName;
        this.patientGrade = patientGrade;
        this.clinicId = clinicId;
    }

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.datetime = appointment.getDatetime();
        this.duration = appointment.getDuration();
        this.discount = appointment.getDiscount();
        this.room = appointment.getActive() ? new RoomDTO(appointment.getRoom()) : null;
        this.type = appointment.getType();
        this.medicalReportId = appointment.getReport() != null ? appointment.getReport().getId() : null;
        this.patientId = appointment.getPatient() != null ? appointment.getPatient().getId() : null;
        this.jmbg = appointment.getPatient() != null ? appointment.getPatient().getJmbg() : null;
        this.doctor = appointment.getDoctor() != null ?
                appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName() : null;
        this.patient = appointment.getDoctor() != null ?
                appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName() : null;
        this.clinicName = appointment.getClinic().getName();
        this.clinicId = appointment.getClinic().getId();
        this.patientGrade = appointment.getDoctorGrade() != null ? appointment.getDoctorGrade().getGrade() : 0;
    }

    public AppointmentDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public ExaminationType getType() {
        return type;
    }

    public void setType(ExaminationType type) {
        this.type = type;
    }

    public Integer getMedicalReportId() {
        return medicalReportId;
    }

    public void setMedicalReportId(Integer medicalReportId) {
        this.medicalReportId = medicalReportId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Integer getPatientGrade() {
        return patientGrade;
    }

    public void setPatientGrade(Integer patientGrade) {
        this.patientGrade = patientGrade;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }
}
