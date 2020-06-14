package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentRequestDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.*;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ExaminationTypeRepository examinationTypeRepository;

    @Override
    @Transactional(readOnly = false)
    public boolean bookAppointment(Integer appointmentId, Integer patientId) {
        Appointment appointment = this.appointmentRepository.findOneById(appointmentId);
        Optional<Patient> patient = this.patientRepository.findById(patientId);

        if (patient.isEmpty() || appointment == null)
            return false;

        // System.out.println("Write: " + patient.get().getFirstName());

        if (appointment.getPatient() != null)
            return false;

//        try {
//            Thread.sleep(10000L);
//        } catch (Exception e) {
//
//        }

        appointment.setPatient(patient.get());
        this.appointmentRepository.save(appointment);
        //

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean bookAppointment(Integer appointmentId, String patientEmail) {
        Appointment appointment = this.appointmentRepository.findOneById(appointmentId);
        // Appointment appointment = this.appointmentRepository.findOneById(appointmentId);
        Optional<Patient> patient = Optional.ofNullable(this.patientRepository.findByEmailAddress(patientEmail));

        if (appointment == null || patient.isEmpty())
            return false;

        if (appointment.getPatient() != null)
            return false;

//        try {
//            Thread.sleep(10000L);
//        } catch (Exception e) {
//
//        }

        appointment.setPatient(patient.get());
        this.appointmentRepository.save(appointment);
        //

        return true;
    }

    @Override
    public List<Appointment> findDoctorsAppointments(Doctor doctor) {
        List<Appointment> result = (List<Appointment>)this.appointmentRepository
                .findAllFreeByDoctor(doctor.getId());
        result.addAll(this.appointmentRepository.findAdditionalAppointments(doctor.getId()));
        return result;
    }

    @Override
    public List<Appointment> findRoomsAppointments(Room room) {
        return this.appointmentRepository.findAllByRoom(room);
    }

    @Override
    public List<AppointmentDTO> getFreeAppointments(Integer clinicId) {
        Optional<Clinic> clinic = this.clinicRepository.findById(clinicId);

        List<AppointmentDTO> ret = new ArrayList<AppointmentDTO>();

        if (clinic.isEmpty())
            return ret;

        Date now = new Date();
        // System.out.println(now);
        for (Appointment app : clinic.get().getAppointments()) {
            if (app.getDatetime() != null && app.getDatetime().after(now) && app.getPatient() == null)
                ret.add(new AppointmentDTO(app));
        }

        return ret;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public Appointment addAppointmentRequest(AppointmentRequestDTO appointment, String patientEmail) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);
        Optional<Doctor> doctor = this.doctorRepository.findById(appointment.getDoctorId());
        Optional<Clinic> clinic = this.clinicRepository.findById(appointment.getClinicId());
        Optional<ExaminationType> et = this.examinationTypeRepository.findById(appointment.getExaminationTypeId());

        if (patient == null)
            return null;

        if (doctor.isEmpty())
            return null;

        if (clinic.isEmpty())
            return null;

        if (et.isEmpty())
            return null;

        // provera da ne dolazi do preklapanja
        //System.out.println("pending: " + patientEmail);

        //TODO: 30 minutes by default, make global constant!
        if (this.appointmentRepository.appointmentExists(
                appointment.getStart(),
                30,
                appointment.getDoctorId())) {
            return null;
        }

//                try {
//            Thread.sleep(10000L);
//        } catch (Exception e) {
//
//        }
//                System.out.println("Write: " + patientEmail);

        Appointment app = new Appointment();
        app.setPatient(patient);
        app.setDoctor(doctor.get());
        app.setClinic(clinic.get());
        app.setType(et.get());
        app.setDatetime(appointment.getStart());
        app.setActive(false);
        app.setDiscount(0.0);
        app.setPrice(app.getType().getPrice() * (1 - app.getDiscount()));

        long diff = appointment.getEnd().getTime() - appointment.getStart().getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        app.setDuration((int)diffMinutes);

        app = this.appointmentRepository.save(app);

        return app;
    }

    @Override
    public boolean addAppointment(Appointment appointment) { // sprecava overlap zbog lekara i sale
        for(Appointment a : appointment.getClinic().getAppointments()) {
            if(a.getDoctor().equals(appointment.getDoctor()) || a.getRoom().equals(appointment.getRoom())) {
                Date date = a.getDatetime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, a.getDuration());
                Date dateEnd = calendar.getTime();
                Date appointmentDate = appointment.getDatetime();
                calendar.setTime(appointmentDate);
                calendar.add(Calendar.MINUTE, appointment.getDuration());
                Date appointmentDateEnd = calendar.getTime();

                if(date.before(appointmentDateEnd) && appointmentDate.before(dateEnd))
                    return false;
            }
        }

        appointmentRepository.save(appointment);
        return true;
    }

    @Override
    public List<AppointmentDTO> findAllAppointmentRequests(Clinic clinic) {
        List<AppointmentDTO> result = new ArrayList<AppointmentDTO>();

        for(Appointment appointment : appointmentRepository
                .findAllByActiveAndClinic(false, clinic)) {
            result.add(new AppointmentDTO(appointment));
        }

        return result;
    }

    @Override
    public Appointment findById(int id) {
        return this.appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(int id) {
        this.appointmentRepository.deleteById(id);
    }

    @Override
    public boolean gradeAppointment(String patientEmail, GradeRequest req) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);

        if (patient == null ||
                req.getGrade() == null ||
                req.getEntityId() == null ||
                req.getGrade() > 5 ||
                req.getGrade() < 0)
            return false;

        Optional<Appointment> appointment = this.appointmentRepository.findById(req.getEntityId());

        if (appointment.isEmpty())
            return false;

        Appointment app = appointment.get();

        if (app.getPatient().getId().intValue() != patient.getId().intValue())
            return false;

        if (app.getDoctorGrade() != null)
            return false;

        DoctorGrade doctorGrade = new DoctorGrade();
        doctorGrade.setGrade(req.getGrade());
        doctorGrade.setDoctor(app.getDoctor()); // cringe?
        app.setDoctorGrade(doctorGrade);
        this.appointmentRepository.save(app);

        app.getDoctor().addGrade(doctorGrade);
        this.doctorRepository.save(app.getDoctor());

        return true;
    }

    @Override
    public String appointmentInfo(Appointment appointment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String roomStr = "";
        String roomType = "";
        if(appointment.getRoom() != null) {
            roomType = appointment.getRoom().getType() == RoomType.EXAMINATION
                    ? "Pregled" : "Operacija";


            roomStr = (roomType.equals("Pregled") ? "Ordinacija" : "Sala") +
                    ": " + appointment.getRoom().getRoomNumber() + "\n";
        }
        return  roomType +
                "\nKlinika: " + appointment.getClinic().getName() +
                "\nDatum početka: " + sdf.format(appointment.getDatetime()) +
                "\nTrajanje: " + appointment.getDuration() + " min" +
                "\nDoktor: " + appointment.getDoctor().getFirstName() + " " +
                appointment.getDoctor().getLastName() +
                "\n" + roomStr;
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}
