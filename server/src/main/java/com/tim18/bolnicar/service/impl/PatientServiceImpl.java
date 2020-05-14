package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.MedicalReportDTO;
import com.tim18.bolnicar.dto.PatientDTO;
import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.MedicalDiagnosis;
import com.tim18.bolnicar.model.MedicalReport;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //TODO: make annotation call of email notification
    @Override
    public boolean registerPatient(UserDTO user) {
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
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    // @Transactional
    public List<MedicalReportDTO> getMedicalRecord(Integer patientId) {
        Optional<Patient> patient = this.patientRepository.findById(patientId);

        if (patient.isEmpty())
            return null;

        Set<MedicalReport> record = patient.get().getMedicalRecord();
        List<MedicalReportDTO> res = new ArrayList<MedicalReportDTO>();

        for (MedicalReport mr : record)
            res.add(new MedicalReportDTO(
                    mr.getId(),
                    mr.getDescription(),
                    mr.getDiagnoses(),
                    mr.getAppointment().getId()
            ));

        return res;
    }

    @Override
    // @Transactional
    public List<MedicalReportDTO> getMedicalRecord(String patientEmail) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);

        if (patient == null)
            return null;

        Set<MedicalReport> record = patient.getMedicalRecord();
        List<MedicalReportDTO> res = new ArrayList<MedicalReportDTO>();

        for (MedicalReport mr : record)
            res.add(new MedicalReportDTO(
                    mr.getId(),
                    mr.getDescription(),
                    mr.getDiagnoses(),
                    mr.getAppointment().getId()
            ));

        return res;
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

    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }
}
