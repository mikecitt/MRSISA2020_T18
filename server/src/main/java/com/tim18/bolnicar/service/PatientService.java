package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.MedicalReport;
import com.tim18.bolnicar.model.Patient;

import java.util.List;
import java.util.Set;

public interface PatientService {
    boolean registerPatient(UserDTO user);
    MedicalRecordDTO getMedicalRecord(Integer patientId);
    MedicalRecordDTO getMedicalRecord(String patientEmail);
    List<AppointmentDTO> getAppointmentsHistory(String patientEmail);
    List<PatientDTO> getPatients();
    List<PatientDTO> getUnregistered();
    Patient getPatient(String patientJmbg);
    Patient save(Patient patient);
}
