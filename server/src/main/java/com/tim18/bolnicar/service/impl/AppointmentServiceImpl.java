package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.repository.AppointmentRepository;
import com.tim18.bolnicar.repository.ClinicRepository;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClinicRepository clinicRepository;

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
    public boolean addAppointment(Appointment appointment) { // sprecava overlap zbog lekara i sale
        for(Appointment a : appointment.getClinic().getAppointments()) {
            if(a.getDoctor().equals(appointment.getDoctor()) || a.getRoom().equals(appointment.getRoom())) {
                Date date = a.getDatetime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, a.getDuration().intValue());
                Date dateEnd = calendar.getTime();
                Date appointmentDate = appointment.getDatetime();
                calendar.setTime(appointmentDate);
                calendar.add(Calendar.MINUTE, appointment.getDuration().intValue());
                Date appointmentDateEnd = calendar.getTime();

                if(date.before(appointmentDateEnd) && appointmentDate.before(dateEnd))
                    return false;
            }
        }

        appointmentRepository.save(appointment);
        return true;
    }
}
