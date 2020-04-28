package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.ClinicCenterAdmin;

import java.util.List;

public interface CCAdminService {
    ClinicCenterAdmin findSingle(String emailAddress);
    List<ClinicCenterAdmin> findAll();
    ClinicCenterAdmin save(ClinicCenterAdmin ccAdmin);
}
