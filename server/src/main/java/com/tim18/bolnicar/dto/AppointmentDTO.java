package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.*;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {
    private Integer id;
    private Date datetime;
    private Double duration;
    private Double discount;
    private RoomDTO room;
    private ExaminationType type;
    private Integer medicalReportId;
    private Integer patientId;
    private String doctor;
    private String clinicName;

    public AppointmentDTO(Integer id,
                          Date datetime,
                          Double duration,
                          Double discount,
                          Room room,
                          ExaminationType type,
                          Integer medicalReportId,
                          Integer patientId,
                          String doctor,
                          String clinicName) {
        this.id = id;
        this.datetime = datetime;
        this.duration = duration;
        this.discount = discount;
        this.room = new RoomDTO(room);
        this.type = type;
        this.medicalReportId = medicalReportId;
        this.patientId = patientId;
        this.doctor = doctor;
        this.clinicName = clinicName;
    }

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.datetime = appointment.getDatetime();
        this.duration = appointment.getDuration();
        this.discount = appointment.getDiscount();
        this.room = new RoomDTO(appointment.getRoom());
        this.type = appointment.getType();
        this.medicalReportId = appointment.getReport() != null ? appointment.getReport().getId() : null;
        this.patientId = appointment.getPatient() != null ? appointment.getPatient().getId() : null;
        this.doctor = appointment.getDoctor() != null ?
                appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName() : null;
        this.clinicName = appointment.getClinic().getName();
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

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
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

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }
}
