package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.UserDTO;

public interface UserService {
    UserDTO getProfile(String emailAddress);
    boolean updateProfile(String emailAddress, UserDTO user);
}
