package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.ExaminationType;
import com.tim18.bolnicar.repository.ExaminationTypeRepository;
import com.tim18.bolnicar.service.ExaminationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationTypeServiceImpl implements ExaminationTypeService {

    @Autowired
    private ExaminationTypeRepository examinationTypeRepository;

    @Override
    public ExaminationType save(ExaminationType examinationType) {
        return examinationTypeRepository.save(examinationType);
    }

    @Override
    public List<ExaminationType> findAll() {
        return (List<ExaminationType>) examinationTypeRepository.findAll();
    }

    @Override
    public ExaminationType findOne(int id) {
        return examinationTypeRepository.findById(id).orElseGet(null);
    }

    @Override
    public void remove(int id) {
        examinationTypeRepository.deleteById(id);
    }
}
