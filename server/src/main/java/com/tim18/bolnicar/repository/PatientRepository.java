package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
}
