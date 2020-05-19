package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WhoDTO {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private Collection<? extends GrantedAuthority> authorities;

    public WhoDTO(User user) {
        this.emailAddress = user.getEmailAddress();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.authorities = user.getAuthorities();
    }

    public WhoDTO() {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
