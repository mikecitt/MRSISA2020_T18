package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.ClinicGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClinicGradeRepository extends CrudRepository<ClinicGrade, Integer> {
    //@Query("SELECT 1 FROM CLINIC_GRADE WHERE CLINIC_ID = :clinic AND PATIENT_ID = :patient")
    //ClinicGrade getPatientGrade(Integer clinicId, Integer patientId);
}
