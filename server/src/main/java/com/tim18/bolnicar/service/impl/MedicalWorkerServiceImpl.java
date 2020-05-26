package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.repository.MedicalWorkerRepository;
import com.tim18.bolnicar.service.MedicalWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalWorkerServiceImpl implements MedicalWorkerService {

    @Autowired
    private MedicalWorkerRepository medicalWorkerRepository;

    @Override
    public MedicalWorker findOne(int id) {
        return this.medicalWorkerRepository.findById(id).orElse(null);
    }

    @Override
    public MedicalWorker findOne(String emailAddress) {
        return this.medicalWorkerRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<MedicalWorker> findAll() {
        return (List<MedicalWorker>)this.medicalWorkerRepository.findAll();
    }

    @Override
    public MedicalWorker save(MedicalWorker medicalWorker) {
        return this.medicalWorkerRepository.save(medicalWorker);
    }

    @Override
    public void remove(int id) {
        this.medicalWorkerRepository.deleteById(id);
    }
}
