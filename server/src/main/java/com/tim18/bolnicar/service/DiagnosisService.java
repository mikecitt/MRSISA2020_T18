package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.MedicalDiagnosis;

import java.util.List;

public interface DiagnosisService {
    MedicalDiagnosis findOne(int id);
    List<MedicalDiagnosis> findAll();
    MedicalDiagnosis save(MedicalDiagnosis diagnosis);
    void remove(int id);
}
