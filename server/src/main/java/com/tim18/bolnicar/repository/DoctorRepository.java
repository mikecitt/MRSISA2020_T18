package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Doctor;
import org.springframework.data.repository.CrudRepository;


public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    Doctor findByEmailAddress(String emailAddress);
}