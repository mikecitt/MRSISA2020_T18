package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.MedicalDiagnosis;
import com.tim18.bolnicar.repository.MedicalDiagnosisRepository;
import com.tim18.bolnicar.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    @Autowired
    private MedicalDiagnosisRepository medicalDiagnosisRepository;

    @Override
    public MedicalDiagnosis findOne(int id) {
        return medicalDiagnosisRepository.findById(id).orElse(null);
    }

    @Override
    public List<MedicalDiagnosis> findAll() {
        return (List<MedicalDiagnosis>)medicalDiagnosisRepository.findAll();
    }

    @Override
    public MedicalDiagnosis save(MedicalDiagnosis diagnosis) {
        return medicalDiagnosisRepository.save(diagnosis);
    }

    @Override
    public void remove(int id) {
        medicalDiagnosisRepository.deleteById(id);
    }
}
