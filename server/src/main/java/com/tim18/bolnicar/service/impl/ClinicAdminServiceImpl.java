package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.repository.ClinicAdminRepository;
import com.tim18.bolnicar.service.ClinicAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicAdminServiceImpl implements ClinicAdminService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    @Override
    public ClinicAdmin findSingle(String emailAddress) {
        return clinicAdminRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<ClinicAdmin> findAll() {
        return (List<ClinicAdmin>)clinicAdminRepository.findAll();
    }

    @Override
    public ClinicAdmin save(ClinicAdmin clinicAdmin) {
        return clinicAdminRepository.save(clinicAdmin);
    }
}
