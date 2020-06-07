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
    private RoomRepository roomRepository;

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
                      TimeOffRepository timeOffRepository,
                      RoomRepository roomRepository) {
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
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
            /*
        Clinic clinicA = new Clinic();
        clinicA.setAddress("Address111");
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
        ca.setEmailAddress("cadmin.@gmail.com");
        ca.setPassword(passwordEncoder.encode("frogfrog"));
        ca.setFirstName("Zdravko");
        ca.setLastName("Dugonjic");
        ca.setAddress("moja adresa");
        ca.setCity("Novi Sad");
        ca.setCountry("Srbija");
        ca.setContact("123321");
        ca.setJmbg("4439827548374");
        ca.setActive(true);
        ca.setClinic(clinicA);

        clinicAdminRepository.save(ca);

        Doctor doctor1 = new Doctor();
        doctor1.setEmailAddress("z.dravko.dugi.@gmail.com");
        doctor1.setPassword(passwordEncoder.encode("frogfrog"));
        doctor1.setFirstName("Zdravko");
        doctor1.setLastName("Dugonjic");
        doctor1.setAddress("moja adresa");
        doctor1.setCity("Novi Sad");
        doctor1.setCountry("Srbija");
        doctor1.setContact("123321");
        doctor1.setJmbg("1928374657483");
        doctor1.setActive(true);

        doctorRepository.save(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setEmailAddress("gojk.o@gmail.com");
        doctor2.setPassword(passwordEncoder.encode("frogfrog"));
        doctor2.setFirstName("Gojko");
        doctor2.setLastName("Gojkovic");
        doctor2.setAddress("moja adresa");
        doctor2.setCity("Beograd");
        doctor2.setCountry("Srbija");
        doctor2.setContact("123321");
        doctor2.setJmbg("9685748574634");
        doctor2.setActive(true);
        doctor2.setClinic(clinicA);

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

        doctor2.setCalendar(calendar);

        doctorRepository.save(doctor2);

        // nurse

        Nurse nurse1 = new Nurse();
        nurse1.setEmailAddress("neven.a@gmail.com");
        nurse1.setPassword(passwordEncoder.encode("12341234"));
        nurse1.setFirstName("Nevena");
        nurse1.setLastName("Nevenic");
        nurse1.setAddress("moja adresa");
        nurse1.setCity("Beograd");
        nurse1.setCountry("Srbija");
        nurse1.setContact("123341");
        nurse1.setJmbg("1928375467384");
        nurse1.setActive(true);
        nurse1.setClinic(clinicA);

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
        Patient patient = new Patient();
        patient.setEmailAddress("patient.@gmail.com");

        patient.setPassword(passwordEncoder.encode("frogfrog"));
        patient.setFirstName("Prototype");
        patient.setLastName("Prototype");
        patient.setAddress("moja adresa");
        patient.setCity("Novi Sad");
        patient.setCountry("Srbija");
        patient.setContact("123321");
        patient.setJmbg("5948374857463");

        patient.setActive(true);

        patientRepository.save(patient);

        // patient
        Patient patient2 = new Patient();
        patient2.setEmailAddress("pacijent.@gmail.com");

        patient2.setPassword(passwordEncoder.encode("frogfrog"));
        patient2.setFirstName("Pacijent");
        patient2.setLastName("Pacijentovic");
        patient2.setAddress("moja adresa");
        patient2.setCity("Novi Sad");
        patient2.setCountry("Srbija");
        patient2.setContact("123321");
        patient2.setJmbg("1223211234543");

        patient2.setActive(true);

        patientRepository.save(patient2);

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
        ap1.setDuration(1.0);
        ap1.setClinic(clinicA);
        ap1.setPatient(patient);
        ap1.setType(e1);

        Appointment ap2 = new Appointment();
        ap2.setDiscount(0.0);
        ap2.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-05-29 12:00"));
        ap2.setDoctor(doctor2);
        ap2.setDuration(2.0);
        ap2.setClinic(clinicA);
        ap2.setPatient(patient2);
        ap2.setType(e2);

        Appointment ap3 = new Appointment();
        ap3.setDiscount(0.0);
        ap3.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-05-29 12:00"));
        ap3.setDoctor(doctor2);
        ap3.setDuration(0.2);
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

        examinationTypeRepository.save(et1);

        // more
        ClinicCenterAdmin cca = new ClinicCenterAdmin();
        cca.setEmailAddress("ccadmin.@gmail.com");
        cca.setPassword(passwordEncoder.encode("frogfrog"));
        cca.setFirstName("Zdravko");
        cca.setLastName("Dugonjic");
        cca.setAddress("moja adresa");
        cca.setCity("Novi Sad");
        cca.setCountry("Srbija");
        cca.setContact("123321");
        cca.setJmbg("1123324567897");
        cca.setActive(true);

        clinicCenterAdminRepository.save(cca);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Room r1 = new Room();
        r1.setRoomNumber(101);
        r1.setType(RoomType.EXAMINATION);

        Room r2 = new Room();
        r2.setRoomNumber(102);
        r2.setType(RoomType.EXAMINATION);

        Room r3 = new Room();
        r3.setRoomNumber(103);
        r3.setType(RoomType.EXAMINATION);

        roomRepository.save(r1);
        roomRepository.save(r2);
        roomRepository.save(r3);

        // some appointments
        Appointment app1 = new Appointment();
        app1.setType(e1);
        app1.setPatient(null);
        app1.setDatetime(formatter.parse("17-08-2020 10:10:11"));
        app1.setDiscount(0.2);
        app1.setClinic(clinicB);
        app1.setDoctor(doctor1);
        app1.setDuration(0.8);
        app1.setReport(null);
        app1.setRoom(r1);

        Appointment app2 = new Appointment();
        app2.setType(e2);
        app2.setPatient(null);
        app2.setDatetime(formatter.parse("13-09-2020 09:10:11"));
        app2.setDiscount(0.5);
        app2.setClinic(clinicB);
        app2.setDoctor(doctor1);
        app2.setDuration(0.6);
        app2.setReport(null);
        app2.setRoom(r2);

        Appointment app3 = new Appointment();
        app3.setType(e1);
        app3.setPatient(null);
        app3.setDatetime(formatter.parse("13-09-2020 09:10:11"));
        app3.setDiscount(0.5);
        app3.setClinic(clinicC);
        app3.setDoctor(doctor1);
        app3.setDuration(0.5);
        app3.setReport(null);
        app3.setRoom(r2);

        appointmentRepository.save(app1);
        appointmentRepository.save(app2);
        appointmentRepository.save(app3);
        
             */
    }
}