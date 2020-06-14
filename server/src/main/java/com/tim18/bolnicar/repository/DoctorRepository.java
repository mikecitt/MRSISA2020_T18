package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    Doctor findByEmailAddress(String emailAddress);
    List<Doctor> findAllByClinicIdOrderByLastNameAsc(Integer clinicId);
}