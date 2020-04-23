package com.tim18.bolnicar.testers;

import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class DataLoader implements ApplicationRunner {

    private DoctorRepository doctorRepository;

    @Autowired
    public DataLoader(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
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
    }
}
