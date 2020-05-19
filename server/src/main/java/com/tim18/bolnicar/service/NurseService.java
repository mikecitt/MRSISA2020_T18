package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Nurse;

import java.util.List;

public interface NurseService {
    Nurse findOne(int id);
    Nurse findOne(String emailAddress);
    List<Nurse> findAll();
    Nurse save(Nurse nurse);
    void remove(int id);
}
