package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.repository.AppointmentRepository;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public boolean bookAppointment(Integer appointmentId, Integer patientId) {
        Optional<Patient> patient = this.patientRepository.findById(patientId);
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId);

        if (patient.isEmpty())
            return false;

        if (appointment.isEmpty())
            return false;

        if (appointment.get().getPatient() != null)
            return false;

        Appointment app = appointment.get();
        app.setPatient(patient.get());

        this.appointmentRepository.save(app);

        return true;
    }

    @Override
    public List<Appointment> findDoctorsAppointments(Doctor doctor) {
        return (List<Appointment>)this.appointmentRepository.findAllByDoctor(doctor);
    }
}
