package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.ExaminationType;

import java.util.List;

public interface ExaminationTypeService {
    ExaminationType save(ExaminationType examinationType);
    List<ExaminationType> findAll();
    ExaminationType findOne(int id);
    void remove(int id);
}
