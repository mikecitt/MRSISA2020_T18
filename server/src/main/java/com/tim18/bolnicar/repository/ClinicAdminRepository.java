package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.ClinicAdmin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicAdminRepository extends CrudRepository<ClinicAdmin, Integer> {

    ClinicAdmin findByEmailAddress(String emailAddress);

    @Query(value = "SELECT email FROM CLINIC_ADMIN WHERE clinic_id = :clinic_id",
            nativeQuery = true)
    List<String> findAllClinicAdminMails(@Param("clinic_id") int clinic_id);
}
