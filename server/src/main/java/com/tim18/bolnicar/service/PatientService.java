package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.MedicalReportDTO;
import com.tim18.bolnicar.dto.PatientDTO;
import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.MedicalReport;
import com.tim18.bolnicar.model.Patient;

import java.util.List;
import java.util.Set;

public interface PatientService {
    boolean registerPatient(UserDTO user);
    List<MedicalReportDTO> getMedicalRecord(Integer patientId);
    List<MedicalReportDTO> getMedicalRecord(String patientEmail);
    List<PatientDTO> getPatients();
    List<PatientDTO> getUnregistered();
    Patient getPatient(String patientJmbg);
    Patient save(Patient patient);
}
