package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentRequestDTO;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.*;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ExaminationTypeRepository examinationTypeRepository;

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
    public boolean bookAppointment(Integer appointmentId, String patientEmail) {
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId);
        Optional<Patient> patient = Optional.ofNullable(this.patientRepository.findByEmailAddress(patientEmail));

        if (appointment.isEmpty() || patient.isEmpty())
            return false;

        if (appointment.get().getPatient() != null)
            return false;

        Appointment app = appointment.get();
        Patient pat = patient.get();

        app.setPatient(pat);
        pat.getAppointments().add(app);

        this.appointmentRepository.save(app);
        this.patientRepository.save(pat);

        return true;
    }

    @Override
    public List<Appointment> findDoctorsAppointments(Doctor doctor) {
        return (List<Appointment>)this.appointmentRepository.findAllByDoctor(doctor);
    }

    @Override
    public List<AppointmentDTO> getFreeAppointments(Integer clinicId) {
        Optional<Clinic> clinic = this.clinicRepository.findById(clinicId);

        List<AppointmentDTO> ret = new ArrayList<AppointmentDTO>();

        if (clinic.isEmpty())
            return ret;

        Date now = new Date();
        System.out.println(now);
        for (Appointment app : clinic.get().getAppointments()) {
            if (app.getDatetime() != null && app.getDatetime().after(now) && app.getPatient() == null)
                ret.add(new AppointmentDTO(app));
        }

        return ret;
    }

    @Override
    public Appointment addAppointmentRequest(AppointmentRequestDTO appointment, String patientEmail) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);
        Optional<Doctor> doctor = this.doctorRepository.findById(appointment.getDoctorId());
        Optional<Clinic> clinic = this.clinicRepository.findById(appointment.getClinicId());
        Optional<ExaminationType> et = this.examinationTypeRepository.findById(appointment.getExaminationTypeId());

        if (patient == null)
            return null;

        if (doctor.isEmpty())
            return null;

        if (clinic.isEmpty())
            return null;

        if (et.isEmpty())
            return null;

        Appointment app = new Appointment();
        app.setPatient(patient);
        app.setDoctor(doctor.get());
        app.setClinic(clinic.get());
        app.setType(et.get());
        app.setDatetime(appointment.getStart());
        app.setActive(false);
        app.setDiscount(0.0);

        long diff = appointment.getEnd().getTime() - appointment.getStart().getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        app.setDuration(diffMinutes / 60.0);

        app = this.appointmentRepository.save(app);

        return app;
    }


}
