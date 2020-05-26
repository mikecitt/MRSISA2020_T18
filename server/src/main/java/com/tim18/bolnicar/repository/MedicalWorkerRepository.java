package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.MedicalWorker;
import org.springframework.data.repository.CrudRepository;


public interface MedicalWorkerRepository extends CrudRepository<MedicalWorker, Integer> {
    MedicalWorker findByEmailAddress(String emailAddress);
}