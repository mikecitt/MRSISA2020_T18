package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<Appointment> appointments;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<Room> rooms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<MedicalWorker> workers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<ClinicAdmin> admins;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinic")
    private Set<ClinicGrade> grades;

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

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<MedicalWorker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<MedicalWorker> workers) {
        this.workers = workers;
    }

    public Set<ClinicAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<ClinicAdmin> admins) {
        this.admins = admins;
    }

    public Set<ClinicGrade> getGrades() {
        return grades;
    }

    public void setGrades(Set<ClinicGrade> grades) {
        this.grades = grades;
    }
}
