package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor findOne(int id);
    List<Doctor> findAll();
    Doctor save(Doctor doctor);
    void remove(int id);

}
