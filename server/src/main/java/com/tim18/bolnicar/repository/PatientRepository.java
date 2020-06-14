package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    Patient findByEmailAddress(String emailAddress);
    Patient findByJmbg(String jmbg);
    Iterable<Patient> findAllByActive(Boolean active);
    @Query(value = "SELECT * FROM patient JOIN appointment ON " +
            "patient.id = appointment.patient_id AND doctor_id = :doctor_id",
            nativeQuery = true)
    List<Patient> isDoctorPatient(@Param("doctor_id") int doctor_id);
}
