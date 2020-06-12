package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.model.Clinic;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface ClinicService {
    ClinicDTO getClinicProfile(int id);
    boolean updateClinicProfile(int id, ClinicDTO clinic);
    Clinic findSingle(String name);
    List<Clinic> findAll();
    List<ClinicDTO> findAll(String patientEmail); // check patient voting right
    Clinic save(Clinic clinic);
    List<ClinicDTO> getClinicsWithFreeAppointments(
            Date date, Integer examinationTypeId, String address, Integer grade);
    boolean gradeClinic(String patientEmail, GradeRequest req);
    ClinicDTO getClinic(String patientEmail, Integer clinicId);
}
