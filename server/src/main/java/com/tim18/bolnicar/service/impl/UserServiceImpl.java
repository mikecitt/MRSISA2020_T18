package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.User;
import com.tim18.bolnicar.repository.UserRepository;
import com.tim18.bolnicar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getProfile(String emailAddress) {
        User user = this.userRepository.findByEmailAddress(emailAddress);
        return new UserDTO(user);
    }

    @Override
    public boolean updateProfile(String emailAddress, UserDTO user) {
        User userCurrent = this.userRepository.findByEmailAddress(emailAddress);

        //TODO: check data

        userCurrent.setFirstName(user.getFirstName());
        userCurrent.setLastName(user.getLastName());
        userCurrent.setPassword(user.getPassword() == null ? userCurrent.getPassword() : passwordEncoder.encode(user.getPassword()));
        userCurrent.setAddress(user.getAddress());
        userCurrent.setCity(user.getCity());
        userCurrent.setCountry(user.getCountry());
        userCurrent.setContact(user.getContact());

        this.userRepository.save(userCurrent);

        return true;
    }

    @Override
    public boolean activateProfile(String emailAddress, UserDTO user) {
        User userCurrent = this.userRepository.findByEmailAddress(emailAddress);

        if(!userCurrent.getActive()) {

            userCurrent.setPassword(passwordEncoder.encode(user.getPassword()));
            userCurrent.setActive(true);

            this.userRepository.save(userCurrent);

            return true;
        }
        else
            return false;
    }

    @Override
    public User findByJmbg(String jmbg) {
        return this.userRepository.findByJmbg(jmbg);
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        return this.userRepository.findByEmailAddress(emailAddress);
    }

}
