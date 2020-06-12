package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.MedicalReport;
import com.tim18.bolnicar.repository.MedicalReportRepository;
import com.tim18.bolnicar.service.MedicalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {
    @Autowired
    private MedicalReportRepository medicalReportRepository;

    @Override
    public MedicalReport save(MedicalReport medicalReport) {
        return medicalReportRepository.save(medicalReport);
    }
}
