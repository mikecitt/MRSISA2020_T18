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

    }
}