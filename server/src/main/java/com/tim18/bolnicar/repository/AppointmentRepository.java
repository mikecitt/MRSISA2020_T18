package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.*;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findAllByDoctor(Doctor doctor);
// DATE_PART('day', :pdate) = DATE_PART('day', DATETIME) AND DATE_PART('month', :pdate) = DATE_PART('month', DATETIME) AND DATE_PART('year', :pdate) = DATE_PART('year', DATETIME) AND
    @Query(value = "SELECT * FROM Appointment WHERE TO_DATE(:pdate, 'yyyy-MM-dd') <= DATETIME AND TO_DATE(:pdate, 'yyyy-MM-dd') + 24 * INTERVAL '1 hour' > DATETIME AND DOCTOR_ID = :did ORDER BY DATETIME ASC",
            nativeQuery = true)
    List<Appointment> findDoctorAppointmentsForDay(@Param("pdate") Date date, @Param("did")  Integer doctorId);

    @Query(value = "SELECT * FROM APPOINTMENT WHERE APPOINTMENT.ID = ( SELECT APPOINTMENT_ID FROM APPOINTMENT_ADDITIONAL_DOCTORS WHERE ADDITIONAL_DOCTORS_ID = :doctor_id)",
            nativeQuery = true)
    List<Appointment> findAdditionalAppointments(@Param("doctor_id") int id);

    @Query(value = "SELECT * FROM APPOINTMENT WHERE patient_id IS NOT NULL AND doctor_id = :doctor_id",
            nativeQuery = true)
    Iterable<Appointment> findAllFreeByDoctor(@Param("doctor_id") int doctor_id);

    List<Appointment> findAllByActiveAndClinic(boolean active, Clinic clinic);
    List<Appointment> findAllByRoom(Room room);

    @Query("SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM Appointment e where clinic_id = :cid and patient_id = :pid")
    boolean patientHasAppointment(@Param("cid") Integer cid, @Param("pid") Integer pid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Appointment a WHERE a.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Appointment findOneById(@Param("id") Integer id);

    @Query("SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM Appointment e where datetime = :pdate and duration = :duration and doctor_id = :did")
    boolean appointmentExists(@Param("pdate") Date pdate, @Param("duration") Integer duration, @Param("did") Integer did);
}
