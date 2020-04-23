package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.ClinicCenterAdmin;
import org.springframework.data.repository.CrudRepository;

public interface ClinicCenterAdminRepository extends CrudRepository<ClinicCenterAdmin, Integer> {

    ClinicCenterAdmin findByEmailAddress(String emailAddress);
}
