package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.ClinicGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClinicGradeRepository extends CrudRepository<ClinicGrade, Integer> {
    @Query(value = "SELECT * FROM CLINIC_GRADE WHERE CLINIC_ID = :clinic AND PATIENT_ID = :patient", nativeQuery = true)
    ClinicGrade getPatientGrade(@Param("clinic") Integer clinic, @Param("patient") Integer patient);
}
