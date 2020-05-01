package com.tim18.bolnicar.testers;

import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.AppointmentRepository;
import com.tim18.bolnicar.repository.DoctorRepository;
import com.tim18.bolnicar.repository.MedicalReportRepository;
import com.tim18.bolnicar.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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

    @Autowired
    public DataLoader(DoctorRepository doctorRepository,
                      PatientRepository patientRepository,
                      AppointmentRepository appointmentRepository,
                      MedicalReportRepository medicalReportRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalReportRepository = medicalReportRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Doctor doctor1 = new Doctor();
        doctor1.setEmailAddress("zdravko.dugi@gmail.com");
        doctor1.setPassword("frog");
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
        doctor2.setEmailAddress("goja@gmail.com");
        doctor2.setPassword("hippopotamus");
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
        patient.setEmailAddress("prototype@gmail.com");
        patient.setPassword("hippopotamus");
        patient.setFirstName("Prototype");
        patient.setLastName("Proton");
        patient.setAddress("adress");
        patient.setCity("Beograd");
        patient.setCountry("Srbija");
        patient.setContact("123-321");
        patient.setJmbg("322111223");
        patient.setActive(true);

        patientRepository.save(patient);

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
    }
}