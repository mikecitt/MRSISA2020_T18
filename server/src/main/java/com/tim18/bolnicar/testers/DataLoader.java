package com.tim18.bolnicar.testers;

import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;
    private MedicalReportRepository medicalReportRepository;
    private ExaminationTypeRepository examinationTypeRepository;
    private ClinicCenterAdminRepository clinicCenterAdminRepository;
    private ClinicAdminRepository clinicAdminRepository;
    private ClinicRepository clinicRepository;
    private NurseRepository nurseRepository;
    private TimeOffRepository timeOffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(DoctorRepository doctorRepository,
                      PatientRepository patientRepository,
                      AppointmentRepository appointmentRepository,
                      MedicalReportRepository medicalReportRepository,
                      ExaminationTypeRepository examinationTypeRepository,
                      ClinicCenterAdminRepository clinicCenterAdminRepository,
                      ClinicAdminRepository clinicAdminRepository,
                      ClinicRepository clinicRepository,
                      NurseRepository nurseRepository,
                      TimeOffRepository timeOffRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalReportRepository = medicalReportRepository;
        this.examinationTypeRepository = examinationTypeRepository;
        this.clinicAdminRepository = clinicAdminRepository;
        this.clinicCenterAdminRepository = clinicCenterAdminRepository;
        this.clinicRepository = clinicRepository;
        this.nurseRepository = nurseRepository;
        this.timeOffRepository = timeOffRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
/*
        TimeOff t1 = new TimeOff();
        t1.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-15"));
        t1.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-25"));
        t1.setActive(true);
        timeOffRepository.save(t1);

        TimeOff t2 = new TimeOff();
        t2.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-10"));
        t2.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-12"));
        timeOffRepository.save(t2);

        Set<TimeOff> calendar = new HashSet<TimeOff>();
        calendar.add(t1);
        calendar.add(t2);

        // nurse

        TimeOff t21 = new TimeOff();
        t21.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-15"));
        t21.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-25"));
        t21.setActive(true);
        timeOffRepository.save(t21);

        TimeOff t22 = new TimeOff();
        t22.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-10"));
        t22.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-12"));
        t22.setActive(true);
        timeOffRepository.save(t22);

        Set<TimeOff> calendar2 = new HashSet<TimeOff>();
        calendar2.add(t21);
        calendar2.add(t22);

        nurse1.setCalendar(calendar2);

        nurseRepository.save(nurse1);

        // patient

        // patient

        ExaminationType e1 = new ExaminationType();
        e1.setName("Examination COVID-19");
        e1.setPrice(100.0);

        ExaminationType e2 = new ExaminationType();
        e2.setName("Examination COVID-20");
        e2.setPrice(200.0);

        examinationTypeRepository.save(e1);
        examinationTypeRepository.save(e2);

        Appointment ap1 = new Appointment();
        ap1.setDiscount(0.0);
        ap1.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-05-28 10:30"));
        ap1.setDoctor(doctor2);
        ap1.setDuration(1);
        ap1.setClinic(clinicA);
        ap1.setPatient(patient);
        ap1.setType(e1);

        Appointment ap2 = new Appointment();
        ap2.setDiscount(0.0);
        ap2.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-05-29 12:00"));
        ap2.setDoctor(doctor2);
        ap2.setDuration(2);
        ap2.setClinic(clinicA);
        ap2.setPatient(patient2);
        ap2.setType(e2);

        Appointment ap3 = new Appointment();
        ap3.setDiscount(0.0);
        ap3.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-05-29 12:00"));
        ap3.setDoctor(doctor2);
        ap3.setDuration(2);
        ap3.setClinic(clinicB);
        ap3.setPatient(patient2);
        ap3.setType(e1);

        appointmentRepository.save(ap1);
        appointmentRepository.save(ap2);
        appointmentRepository.save(ap3);

        MedicalReport mr1 = new MedicalReport();
        mr1.setAppointment(ap1);
        mr1.setDescription("Description first report");

        MedicalReport mr2 = new MedicalReport();
        mr2.setAppointment(ap2);
        mr2.setDescription("Description second report");

        Set<MedicalReport> mrs = new HashSet<MedicalReport>();
        mrs.add(mr1);
        mrs.add(mr2);

        patient.setMedicalRecord(mrs);

        patientRepository.save(patient);

        ExaminationType et1 = new ExaminationType();
        et1.setId(101);
        et1.setName("Testiranje na COVID-19");
        et1.setPrice(20000.00);

        examinationTypeRepository.save(et1);*/
    }
}