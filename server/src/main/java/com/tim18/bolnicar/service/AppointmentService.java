package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentRequestDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Doctor;

import java.util.List;

public interface AppointmentService {
    boolean bookAppointment(Integer appointmentId, Integer patientId);
    boolean bookAppointment(Integer appointmentId, String patientEmail);
    List<Appointment> findDoctorsAppointments(Doctor doctor);
    List<AppointmentDTO> getFreeAppointments(Integer clinicId);
    Appointment addAppointmentRequest(AppointmentRequestDTO appointment, String patientEmail);
    boolean addAppointment(Appointment appointment);
    Appointment save(Appointment appointment);
    boolean gradeAppointment(String patientEmail, GradeRequest req);
}
