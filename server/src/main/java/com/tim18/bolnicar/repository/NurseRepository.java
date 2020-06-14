package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Nurse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NurseRepository extends CrudRepository<Nurse, Integer> {
    Nurse findByEmailAddress(String emailAddress);
    List<Nurse> findAllByClinicIdOrderByLastNameAsc(Integer clinicId);
}
