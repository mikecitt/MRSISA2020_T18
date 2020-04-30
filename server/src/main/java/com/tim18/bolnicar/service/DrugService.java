package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Drug;

import java.util.List;

public interface DrugService {
    Drug findOne(int id);
    List<Drug> findAll();
    Drug save(Drug drug);
    void remove(int id);
}
