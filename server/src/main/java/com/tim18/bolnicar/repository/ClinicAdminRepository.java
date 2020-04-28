package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.ClinicAdmin;
import org.springframework.data.repository.CrudRepository;

public interface ClinicAdminRepository extends CrudRepository<ClinicAdmin, Integer> {

    ClinicAdmin findByEmailAddress(String emailAddress);
}
