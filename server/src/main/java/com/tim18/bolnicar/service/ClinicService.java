package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.model.Clinic;

import java.util.List;

public interface ClinicService {
    ClinicDTO getClinicProfile(int id);
    boolean updateClinicProfile(int id, ClinicDTO clinic);
    Clinic findSingle(String name);
    List<Clinic> findAll();
    Clinic save(Clinic clinic);
}
