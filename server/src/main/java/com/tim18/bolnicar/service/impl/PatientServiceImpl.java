package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    //TODO: make annotation call of email notification
    @Override
    public boolean registerPatient(UserDTO user) {
        Patient patient = new Patient();

        patient.setEmailAddress(user.getEmailAddress());
        patient.setPassword(user.getPassword()); //TODO: hash!
        patient.setFirstName(user.getFirstName());
        patient.setLastName(user.getLastName());
        patient.setAddress(user.getAddress());
        patient.setCity(user.getCity());
        patient.setCountry(user.getCountry());
        patient.setContact(user.getContact());
        patient.setJmbg(user.getJmbg());
        patient.setActive(false);

        try {
            patientRepository.save(patient);
            return true;
        } catch (Exception e) {
        }

        return false;
    }
}
