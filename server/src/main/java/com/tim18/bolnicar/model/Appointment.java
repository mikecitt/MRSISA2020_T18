package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne
    private Clinic clinic;

    @Column
    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    private DoctorGrade doctorGrade;

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

    public DoctorGrade getDoctorGrade() {
        return doctorGrade;
    }

    public void setDoctorGrade(DoctorGrade doctorGrade) {
        this.doctorGrade = doctorGrade;
    }
}
