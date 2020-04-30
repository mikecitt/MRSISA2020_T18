package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.ExaminationType;
import com.tim18.bolnicar.repository.ExaminationTypeRepository;
import com.tim18.bolnicar.service.ExaminationTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExaminationTypeServiceImpl implements ExaminationTypeService {

    @Autowired
    private ExaminationTypeRepository examinationTypeRepository;

    @Override
    public ExaminationType save(ExaminationType examinationType) {
        return examinationTypeRepository.save(examinationType);
    }
}
