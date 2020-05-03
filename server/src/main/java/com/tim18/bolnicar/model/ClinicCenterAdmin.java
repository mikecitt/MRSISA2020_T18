package com.tim18.bolnicar.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class ClinicCenterAdmin extends User {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority("ROLE_CENTER_ADMIN"));
        return authorityList;
    }
}
