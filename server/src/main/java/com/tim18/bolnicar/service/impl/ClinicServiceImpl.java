package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.ClinicDTO;
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
    public ClinicDTO getClinicProfile(int id) {
        Clinic clinic = clinicRepository.findById(id).orElseGet(null);
        return new ClinicDTO(clinic);
    }

    @Override
    public boolean updateClinicProfile(int id, ClinicDTO clinic) {
        Clinic clinicCurrent = this.clinicRepository.findById(id).orElseGet(null);

        //TODO: check data

        clinicCurrent.setName(clinic.getName());
        clinicCurrent.setAddress(clinic.getAddress());
        clinicCurrent.setDescription(clinic.getDescription());

        this.clinicRepository.save(clinicCurrent);

        return true;
    }

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
