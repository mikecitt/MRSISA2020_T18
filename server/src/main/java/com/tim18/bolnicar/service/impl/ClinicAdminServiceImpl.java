package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.repository.ClinicAdminRepository;
import com.tim18.bolnicar.repository.UserRepository;
import com.tim18.bolnicar.service.ClinicAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicAdminServiceImpl implements ClinicAdminService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ClinicAdmin findSingle(String emailAddress) {
        return clinicAdminRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<ClinicAdmin> findAll() {
        return (List<ClinicAdmin>)clinicAdminRepository.findAll();
    }

    @Override
    public ClinicAdmin save(ClinicAdmin clinicAdmin) {
        return clinicAdminRepository.save(clinicAdmin);
    }

    @Override
    public List<String> getAllEmails(Clinic clinic) {
        return this.clinicAdminRepository.findAllClinicAdminMails(clinic.getId());
    }

    @Override
    public boolean register(ClinicAdmin clinicAdmin) {
        if(userRepository.findByEmailAddress(clinicAdmin.getEmailAddress()) == null && userRepository.findByJmbg(clinicAdmin.getJmbg()) == null) {
            clinicAdmin.setPassword(passwordEncoder.encode(clinicAdmin.getPassword()));
            clinicAdmin.setLastPasswordResetDate(null);
            clinicAdmin.setActive(true);
            try {
                save(clinicAdmin);
                return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
