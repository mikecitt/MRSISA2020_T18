package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.MedicalWorker;

import java.util.List;

public interface MedicalWorkerService {
    MedicalWorker findOne(int id);
    MedicalWorker findOne(String emailAddress);
    List<MedicalWorker> findAll();
    MedicalWorker save(MedicalWorker medicalWorker);
    void remove(int id);
}
