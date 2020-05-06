package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.ExaminationType;

import java.util.List;

public interface ExaminationTypeService {
    ExaminationType save(ExaminationType examinationType);
    List<ExaminationType> findAll();
}
