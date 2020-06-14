package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.model.Patient;

public interface ClinicGradeTransService {
    boolean updateClinicGrade(Integer patientId, GradeRequest req);
    boolean addClinicGrade(Patient patient, GradeRequest req);
}
