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

    // in hours
    @Column
    private Integer duration;

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

    @Enumerated(EnumType.STRING)
    @Column
    private RoomType appointmentType;

    @Column
    private Double price;

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

    public DoctorGrade getDoctorGrade() {
        return doctorGrade;
    }

    public void setDoctorGrade(DoctorGrade doctorGrade) {
        this.doctorGrade = doctorGrade;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public RoomType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(RoomType type) {
        this.appointmentType = type;
    }
}
