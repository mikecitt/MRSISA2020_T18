package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findAllByDoctor(Doctor doctor);
}
