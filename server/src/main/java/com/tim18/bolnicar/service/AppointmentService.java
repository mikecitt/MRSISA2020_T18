package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Doctor;

import java.util.List;

public interface AppointmentService {
    boolean bookAppointment(Integer appointmentId, Integer patientId);
    List<Appointment> findDoctorsAppointments(Doctor doctor);
}
