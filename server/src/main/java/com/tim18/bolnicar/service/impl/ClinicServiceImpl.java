package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.repository.ClinicRepository;
import com.tim18.bolnicar.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public Clinic findSingle(String name) {
        return clinicRepository.findByName(name);
    }

    @Override
    public List<Clinic> findAll() {
        return (List<Clinic>)clinicRepository.findAll();
    }

    @Override
    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }
}
