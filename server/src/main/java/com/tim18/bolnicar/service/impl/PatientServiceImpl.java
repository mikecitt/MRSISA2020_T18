package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.repository.UserRepository;
import com.tim18.bolnicar.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    //TODO: make annotation call of email notification
    @Override
    public boolean registerPatient(UserDTO user) {
        if(userRepository.findByEmailAddress(user.getEmailAddress()) == null && userRepository.findByJmbg(user.getJmbg()) == null) {

            Patient patient = new Patient();

            patient.setEmailAddress(user.getEmailAddress());
            patient.setPassword(passwordEncoder.encode(user.getPassword()));
            patient.setFirstName(user.getFirstName());
            patient.setLastName(user.getLastName());
            patient.setAddress(user.getAddress());
            patient.setCity(user.getCity());
            patient.setCountry(user.getCountry());
            patient.setContact(user.getContact());
            patient.setJmbg(user.getJmbg());
            patient.setActive(null);

            try {
                patientRepository.save(patient);
                return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    @Override
    // @Transactional
    public MedicalRecordDTO getMedicalRecord(Integer patientId) {
        Optional<Patient> patient = this.patientRepository.findById(patientId);

        if (patient.isEmpty())
            return null;

        MedicalRecordDTO mr = new MedicalRecordDTO();
        List<MedicalReportDTO> reports = new ArrayList<>();
        mr.setFirstName(patient.get().getFirstName());
        mr.setLastName(patient.get().getLastName());
        mr.setJmbg(patient.get().getJmbg());

        for (MedicalReport m : patient.get().getMedicalRecord()) {
            MedicalReportDTO report = new MedicalReportDTO();
            report.setId(m.getId());
            report.setAppointmentId(m.getAppointment().getId());
            report.setDescription(m.getDescription());
            report.setDiagnoses(m.getDiagnoses());
            // repack
            Set<RecipeDTO> recipes = new HashSet<>();
            for (Recipe r : m.getRecipes())
                recipes.add(new RecipeDTO(r));
            report.setRecipes(recipes);
            report.setAppointmentDate(m.getAppointment().getDatetime());
            reports.add(report);
        }

        mr.setMedicalReports(reports);

        return mr;
    }

    @Override
    // @Transactional
    public MedicalRecordDTO getMedicalRecord(String patientEmail) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);

        if (patient == null)
            return null;

        MedicalRecordDTO mr = new MedicalRecordDTO();
        List<MedicalReportDTO> reports = new ArrayList<>();
        mr.setFirstName(patient.getFirstName());
        mr.setLastName(patient.getLastName());
        mr.setJmbg(patient.getJmbg());

        for (MedicalReport m : patient.getMedicalRecord()) {
            MedicalReportDTO report = new MedicalReportDTO();
            report.setId(m.getId());
            report.setAppointmentId(m.getAppointment().getId());
            report.setDescription(m.getDescription());
            report.setDiagnoses(m.getDiagnoses());
            // repack
            Set<RecipeDTO> recipes = new HashSet<>();
            for (Recipe r : m.getRecipes())
                recipes.add(new RecipeDTO(r));
            report.setRecipes(recipes);
            report.setAppointmentDate(m.getAppointment().getDatetime());
            reports.add(report);
        }

        mr.setMedicalReports(reports);

        return mr;
    }

    @Override
    public List<AppointmentDTO> getAppointmentsHistory(String patientEmail) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);
        List<AppointmentDTO> appointments = new ArrayList<>();

        if (patient == null)
            return null;

        for (Appointment appointment : patient.getAppointments()) {
            if (!appointment.getActive()) continue;
            if (appointment.getReport() == null) continue;

            AppointmentDTO dto = new AppointmentDTO(appointment);

            // enable patient to grade appointment, if grade is null!
            //if (dto.getPatientGrade() == null)
            //    dto.setPatientGrade(0);

            appointments.add(dto);
        }

        return appointments;
    }

    @Override
    public List<PatientDTO> getPatients() {
        List<PatientDTO> patients = new ArrayList<>();

        for (Patient p : this.patientRepository.findAll()) {
            PatientDTO patient = new PatientDTO();
            patient.setFirstName(p.getFirstName());
            patient.setLastName(p.getLastName());
            patient.setJmbg(p.getJmbg());
            patients.add(patient);
        }

        return patients;
    }

    @Override
    public List<PatientDTO> getUnregistered() {
        List<PatientDTO> patients = new ArrayList<>();

        for(Patient p : this.patientRepository.findAllByActive(null)) {
            PatientDTO patient = new PatientDTO();
            patient.setFirstName(p.getFirstName());
            patient.setLastName(p.getLastName());
            patient.setJmbg(p.getJmbg());
            patients.add(patient);
        }

        return patients;
    }

    public Patient getPatient(String patientJmbg) {
        return this.patientRepository.findByJmbg(patientJmbg);
    }

    @Override
    public Patient getPatient(Integer id) {
        return this.patientRepository.findById(id).orElseGet(null);
    }

    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }

    public boolean isDoctorPatient(Patient patient, int doctor_id) {
        for(Patient patient1 : this.patientRepository.isDoctorPatient(doctor_id)) {
            if(patient1.getId().intValue() == patient.getId().intValue())
                return true;
        }

        return false;
    }
}
