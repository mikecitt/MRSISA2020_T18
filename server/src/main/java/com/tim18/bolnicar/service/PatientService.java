package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.MedicalReportDTO;
import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.MedicalReport;

import java.util.List;
import java.util.Set;

public interface PatientService {
    boolean registerPatient(UserDTO user);
    List<MedicalReportDTO> getMedicalRecord(Integer patientId);
}
