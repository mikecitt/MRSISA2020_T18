package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findAllByDoctor(Doctor doctor);

    @Query(value = "SELECT * FROM Appointment WHERE DATEDIFF(day, DATETIME, :pdate) = 0 AND DOCTOR_ID = :did ORDER BY DATETIME ASC",
            nativeQuery = true)
    List<Appointment> findDoctorAppointmentsForDay(@Param("pdate") Date date, @Param("did")  Integer doctorId);

    @Query(value = "SELECT * FROM APPOINTMENT WHERE APPOINTMENT.ID = ( SELECT APPOINTMENT_ID FROM APPOINTMENT_ADDITIONAL_DOCTORS WHERE ADDITIONAL_DOCTORS_ID = :doctor_id)",
            nativeQuery = true)
    List<Appointment> findAdditionalAppointments(@Param("doctor_id") int id);

    @Query(value = "SELECT * FROM APPOINTMENT WHERE patient_id IS NOT NULL AND doctor_id = :doctor_id",
            nativeQuery = true)
    Iterable<Appointment> findAllFreeByDoctor(@Param("doctor_id") int doctor_id);
}
