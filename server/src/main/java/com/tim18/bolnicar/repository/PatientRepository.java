package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    Patient findByEmailAddress(String emailAddress);
    Patient findByJmbg(String jmbg);
    Iterable<Patient> findAllByActive(Boolean active);
}
