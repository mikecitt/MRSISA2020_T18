package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Nurse;
import com.tim18.bolnicar.repository.NurseRepository;
import com.tim18.bolnicar.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(Nurse nurse) {
        nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));
        nurse.setActive(true);

        try {
            save(nurse);
            return true;
        } catch (Exception ex) {
        }

        return false;
    }

    @Override
    public Nurse findOne(int id) {
        return this.nurseRepository.findById(id).orElse(null);
    }

    @Override
    public Nurse findOne(String emailAddress) {
        return this.nurseRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<Nurse> findAll() {
        return (List<Nurse>)this.nurseRepository.findAll();
    }

    @Override
    public Nurse save(Nurse nurse) {
        return this.nurseRepository.save(nurse);
    }

    @Override
    public void remove(int id) {
        this.nurseRepository.deleteById(id);
    }

    @Override
    public List<Nurse> findNursesFromClinic(Integer clinicId) {
        return this.nurseRepository.findAllByClinicIdOrderByLastNameAsc(clinicId);
    }
}
