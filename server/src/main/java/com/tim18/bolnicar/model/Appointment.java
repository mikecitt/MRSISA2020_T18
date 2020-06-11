package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private Date datetime;

    @Column
    private Double duration;

    // positive percents
    @Column(nullable = false)
    private Double discount;

    @OneToOne
    private Room room;

    @OneToOne
    private ExaminationType type;

    @OneToOne
    private MedicalReport report;

    @ManyToOne
    private Patient patient;

    @OneToOne
    private Doctor doctor;

    @ManyToMany
    private Set<Doctor> additionalDoctors;

    @ManyToOne
    private Clinic clinic;

    @Column
    private Boolean active;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ExaminationType getType() {
        return type;
    }

    public void setType(ExaminationType type) {
        this.type = type;
    }

    public MedicalReport getReport() {
        return report;
    }

    public void setReport(MedicalReport report) {
        this.report = report;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<Doctor> getAdditionalDoctors() {
        return additionalDoctors;
    }

    public void setAdditionalDoctors(Set<Doctor> additionalDoctors) {
        this.additionalDoctors = additionalDoctors;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
