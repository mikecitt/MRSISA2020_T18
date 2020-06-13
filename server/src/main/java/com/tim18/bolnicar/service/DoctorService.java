package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.TimeIntervalDTO;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Doctor;

import java.util.Date;
import java.util.List;

public interface DoctorService {
    boolean register(Doctor nurse);
    Doctor findOne(int id);
    Doctor findOne(String emailAddress);
    List<Doctor> findAll();
    Doctor save(Doctor doctor);
    void remove(int id);
    List<Appointment> getAppointmentsForDate(Date date, Integer doctorId);
    List<TimeIntervalDTO> getFreeDayTime(Date date, Integer doctorId, Integer duration);
}
