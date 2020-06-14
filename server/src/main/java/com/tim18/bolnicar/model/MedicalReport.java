package com.tim18.bolnicar.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String description;

    @ManyToMany
    private Set<MedicalDiagnosis> diagnoses;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    @OneToOne
    private Appointment appointment;

    public Integer getId() {
        return id;
    }

    //public void setId(Integer id) {
    //    this.id = id;
    //}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MedicalDiagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<MedicalDiagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
