package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}
