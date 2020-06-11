package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.User;

public interface UserService {
    UserDTO getProfile(String emailAddress);
    boolean updateProfile(String emailAddress, UserDTO user);
    boolean activateProfile(String emailAddress, UserDTO user);
    User findByJmbg(String jmbg);
    User findByEmailAddress(String emailAddress);
}
