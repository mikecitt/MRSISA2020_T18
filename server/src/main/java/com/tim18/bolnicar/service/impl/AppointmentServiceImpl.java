package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentRequestDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.*;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean bookAppointment(Integer appointmentId, Integer patientId) {
        Optional<Patient> patient = this.patientRepository.findById(patientId);
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId);

        if (patient.isEmpty())
            return false;

        if (appointment.isEmpty())
            return false;

        if (appointment.get().getPatient() != null)
            return false;

        Appointment app = appointment.get();
        app.setPatient(patient.get());

        this.appointmentRepository.save(app);

        return true;
    }

    @Override
    public boolean bookAppointment(Integer appointmentId, String patientEmail) {
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId);
        Optional<Patient> patient = Optional.ofNullable(this.patientRepository.findByEmailAddress(patientEmail));

        if (appointment.isEmpty() || patient.isEmpty())
            return false;

        if (appointment.get().getPatient() != null)
            return false;

        Appointment app = appointment.get();
        Patient pat = patient.get();

        app.setPatient(pat);
        pat.getAppointments().add(app);

        this.appointmentRepository.save(app);
        this.patientRepository.save(pat);

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
    public List<Appointment> findAllDoctorsAppointments(Doctor doctor) {
        return this.appointmentRepository.findAllByDoctor(doctor);
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

        Appointment app = new Appointment();
        app.setPatient(patient);
        app.setDoctor(doctor.get());
        app.setClinic(clinic.get());
        app.setType(et.get());
        app.setAppointmentType(appointment.getRoomType());
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

    @Override
    public List<Appointment> findAll() {
        return (List<Appointment>)this.appointmentRepository.findAll();
    }
}
