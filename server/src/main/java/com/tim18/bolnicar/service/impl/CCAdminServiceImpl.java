package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.ClinicCenterAdmin;
import com.tim18.bolnicar.repository.ClinicCenterAdminRepository;
import com.tim18.bolnicar.repository.UserRepository;
import com.tim18.bolnicar.service.CCAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CCAdminServiceImpl implements CCAdminService {

    @Autowired
    private ClinicCenterAdminRepository clCenterAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public ClinicCenterAdmin findSingle(String emailAddress) {
        return clCenterAdminRepository.findByEmailAddress(emailAddress);
    }

    public List<ClinicCenterAdmin> findAll() {
        return (List<ClinicCenterAdmin>)(clCenterAdminRepository.findAll());
    }

    public ClinicCenterAdmin save(ClinicCenterAdmin ccAdmin) {
        return clCenterAdminRepository.save(ccAdmin);
    }

    @Override
    public boolean register(ClinicCenterAdmin ccAdmin) {
        if(userRepository.findByEmailAddress(ccAdmin.getEmailAddress()) == null && userRepository.findByJmbg(ccAdmin.getJmbg()) == null) {
            ccAdmin.setPassword(passwordEncoder.encode(ccAdmin.getPassword()));
            ccAdmin.setLastPasswordResetDate(null);
            ccAdmin.setActive(true);

            try {
                save(ccAdmin);
                return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
