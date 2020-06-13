package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.ClinicAdmin;

import java.util.List;

public interface ClinicAdminService {
    ClinicAdmin findSingle(String emailAddress);
    List<ClinicAdmin> findAll();
    ClinicAdmin save(ClinicAdmin clinicAdmin);
    List<String> getAllEmails(Clinic clinic);
}
