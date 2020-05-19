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
                      ClinicRepository clinicRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalReportRepository = medicalReportRepository;
        this.examinationTypeRepository = examinationTypeRepository;
        this.clinicAdminRepository = clinicAdminRepository;
        this.clinicCenterAdminRepository = clinicCenterAdminRepository;
        this.clinicRepository = clinicRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Doctor doctor1 = new Doctor();
        doctor1.setEmailAddress("zdravko.dugi@gmail.com");
        doctor1.setPassword(passwordEncoder.encode("frogfrog"));
        doctor1.setFirstName("Zdravko");
        doctor1.setLastName("Dugonjic");
        doctor1.setAddress("moja adresa");
        doctor1.setCity("Novi Sad");
        doctor1.setCountry("Srbija");
        doctor1.setContact("123-321");
        doctor1.setJmbg("123456789");
        doctor1.setActive(true);

        doctorRepository.save(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setEmailAddress("gojko@gmail.com");
        doctor2.setPassword(passwordEncoder.encode("hippopotamus"));
        doctor2.setFirstName("Gojko");
        doctor2.setLastName("Gojkovic");
        doctor2.setAddress("moja adresa");
        doctor2.setCity("Beograd");
        doctor2.setCountry("Srbija");
        doctor2.setContact("123-321");
        doctor2.setJmbg("322111223");
        doctor2.setActive(true);

        doctorRepository.save(doctor2);

        // patient
        Patient patient = new Patient();
        patient.setEmailAddress("patient@gmail.com");

        patient.setPassword(passwordEncoder.encode("frogfrog"));
        patient.setFirstName("Prototype");
        patient.setLastName("Prototype");
        patient.setAddress("moja adresa");
        patient.setCity("Novi Sad");
        patient.setCountry("Srbija");
        patient.setContact("123-321");
        patient.setJmbg("5431134431234");

        patient.setActive(true);

        patientRepository.save(patient);

        // patient
        Patient patient2 = new Patient();
        patient2.setEmailAddress("pacijent@gmail.com");

        patient2.setPassword(passwordEncoder.encode("frogfrog"));
        patient2.setFirstName("Pacijent");
        patient2.setLastName("Pacijentovic");
        patient2.setAddress("moja adresa");
        patient2.setCity("Novi Sad");
        patient2.setCountry("Srbija");
        patient2.setContact("123-321");
        patient2.setJmbg("123456789321");

        patient2.setActive(true);

        patientRepository.save(patient2);

        Appointment ap1 = new Appointment();
        ap1.setDiscount(0.0);
        ap1.setDatetime(new Date());
        ap1.setPatient(patient);

        Appointment ap2 = new Appointment();
        ap2.setDiscount(0.0);
        ap2.setDatetime(new Date());
        ap2.setPatient(patient);

        appointmentRepository.save(ap1);
        appointmentRepository.save(ap2);

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

        examinationTypeRepository.save(et1);

        // more
        ClinicCenterAdmin cca = new ClinicCenterAdmin();
        cca.setEmailAddress("ccadmin@gmail.com");
        cca.setPassword(passwordEncoder.encode("frogfrog"));
        cca.setFirstName("Zdravko");
        cca.setLastName("Dugonjic");
        cca.setAddress("moja adresa");
        cca.setCity("Novi Sad");
        cca.setCountry("Srbija");
        cca.setContact("123-321");
        cca.setJmbg("1234456789");
        cca.setActive(true);

        clinicCenterAdminRepository.save(cca);

        // clinics
        Clinic clinicA = new Clinic();
        clinicA.setAddress("Address1");
        clinicA.setName("Clinic A");
        clinicA.setDescription("Clinic A Description");

        Clinic clinicB = new Clinic();
        clinicB.setAddress("Address2");
        clinicB.setName("Clinic B");
        clinicB.setDescription("Clinic B Description");

        Clinic clinicC = new Clinic();
        clinicC.setAddress("Address3");
        clinicC.setName("Clinic A3");
        clinicC.setDescription("Clinic A3 Description");

        this.clinicRepository.save(clinicA);
        this.clinicRepository.save(clinicB);
        this.clinicRepository.save(clinicC);

        ClinicAdmin ca = new ClinicAdmin();
        ca.setEmailAddress("cadmin@gmail.com");
        ca.setPassword(passwordEncoder.encode("frogfrog"));
        ca.setFirstName("Zdravko");
        ca.setLastName("Dugonjic");
        ca.setAddress("moja adresa");
        ca.setCity("Novi Sad");
        ca.setCountry("Srbija");
        ca.setContact("123-321");
        ca.setJmbg("123445675389");
        ca.setActive(true);
        ca.setClinic(clinicA);

        clinicAdminRepository.save(ca);
    }
}