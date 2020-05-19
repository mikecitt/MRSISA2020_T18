package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Nurse;
import com.tim18.bolnicar.repository.NurseRepository;
import com.tim18.bolnicar.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseRepository nurseRepository;

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
}
