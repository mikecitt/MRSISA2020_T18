package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.TimeIntervalDTO;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.repository.AppointmentRepository;
import com.tim18.bolnicar.repository.DoctorRepository;
import com.tim18.bolnicar.repository.UserRepository;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean register(Doctor doctor) {
        if(userRepository.findByEmailAddress(doctor.getEmailAddress()) == null && userRepository.findByJmbg(doctor.getJmbg()) == null) {
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            doctor.setLastPasswordResetDate(null);
            doctor.setActive(false);

            try {
                save(doctor);
                return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    @Override
    public List<Doctor> findAll() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    @Override
    public Doctor findOne(int id) {
        return doctorRepository.findById(id).orElseGet(null);
    }

    @Override
    public Doctor findOne(String emailAddress) {
        return doctorRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void remove(int id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> findDoctorsFromClinic(Integer clinicId) {
        return doctorRepository.findAllByClinicIdOrderByLastNameAsc(clinicId);
    }

    @Override
    public List<Appointment> getAppointmentsForDate(Date date, Integer doctorId) {
        return this.appointmentRepository.findDoctorAppointmentsForDay(date, doctorId);
    }

    private boolean isHoliday(Date date, Doctor doctor) {
        // added because it only needs to compare date without time portion
        Calendar compare = Calendar.getInstance();
        compare.setTime(date);
        compare.set(Calendar.HOUR, 0);
        compare.set(Calendar.MINUTE, 0);
        compare.set(Calendar.SECOND, 0);
        compare.set(Calendar.MILLISECOND, 0);
        //TODO: sorted?! no?
        for (TimeOff to : doctor.getActiveCalendar()) {
            // start_date <= date <= end_date, is date in holiday interval
            //TODO: test
            if (to.getStartDate().compareTo(compare.getTime()) <= 0 &&
                    to.getEndDate().compareTo(compare.getTime()) >= 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isWorkingDay(Date date, Doctor doctor) {
        //TODO: implement real check
        return true;
    }

    private boolean isIntersecting(Date startA, Date endA, Date startB, Date endB) {
        //TODO: testing
        if (startA.compareTo(endA) >= 0 || startB.compareTo(endB) >= 0)
            return false;
        return (startB.compareTo(startA) <= 0 && endB.compareTo(startA) >= 0) ||
                (startB.compareTo(endA) <= 0 && endB.compareTo(endA) >= 0);
    }

    //TODO: test
    @Override
    public List<TimeIntervalDTO> getFreeDayTime(Date date, Integer doctorId, Integer duration) {
        List<TimeIntervalDTO> free = new ArrayList<>();
        Optional<Doctor> doctor = this.doctorRepository.findById(doctorId);

        if (doctor.isEmpty())
            return free;

        List<Appointment> appointments = this.getAppointmentsForDate(date, doctorId);

        if (isHoliday(date, doctor.get()) || !isWorkingDay(date, doctor.get()))
            return free;

        //TODO: shift begin-end
        // init
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        begin.set(Calendar.HOUR, 8);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.MILLISECOND, 0);

        Calendar end = Calendar.getInstance();
        end.setTime(date);
        end.set(Calendar.HOUR, 22);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        // prepare for iteration
        Iterator<Appointment> itera = appointments.iterator();
        Appointment app = null;
        if (itera.hasNext())
            app = itera.next();

        while (begin.before(end)) {
            Calendar intervalEnd = Calendar.getInstance();
            intervalEnd.setTime(begin.getTime());
            intervalEnd.add(Calendar.MINUTE, duration);

            // appointment end
            Calendar appEnd = null;
            if (app != null) {
                appEnd = Calendar.getInstance();
                appEnd.setTime(app.getDatetime());
                appEnd.add(Calendar.MINUTE, (app.getDuration()));
            }

            // begin - a
            // intervalEnd - b
            // appDatetime - c
            // appEnd - d
            if (appEnd != null && this.isIntersecting(begin.getTime(), intervalEnd.getTime(), app.getDatetime(), appEnd.getTime())) {
                // intersecting with existing appointment, take appointment if it's free!

                if (app.getPatient() == null) {
                    TimeIntervalDTO tid = new TimeIntervalDTO(app.getDatetime(), appEnd.getTime());
                    tid.setAppointmentId(app.getId());
                    free.add(tid);
                }

                // iterate
                if (itera.hasNext())
                    app = itera.next();
                else
                    app = null;
                begin.setTime(appEnd.getTime());
            } else {
                free.add(new TimeIntervalDTO(begin.getTime(), intervalEnd.getTime()));
                begin.setTime(intervalEnd.getTime());

                if (app != null && begin.getTime().compareTo(app.getDatetime()) > 0) {
                    // after
                    if (itera.hasNext())
                        app = itera.next();
                    else
                        app = null;
                }
            }
        }

        return free;
    }
}
