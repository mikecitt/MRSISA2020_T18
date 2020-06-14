package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.ClinicGrade;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.repository.AppointmentRepository;
import com.tim18.bolnicar.repository.ClinicGradeRepository;
import com.tim18.bolnicar.repository.ClinicRepository;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.ClinicGradeTransService;
import com.tim18.bolnicar.service.DoctorService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class ClinicGradeTransServiceImpl implements ClinicGradeTransService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicGradeRepository clinicGradeRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public boolean updateClinicGrade(Integer patientId, GradeRequest req) {
        ClinicGrade clinicGrade = this.clinicGradeRepository.getPatientGrade(req.getEntityId(), patientId);

        if (clinicGrade != null) {
//            try {
//                Thread.sleep(10000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            clinicGrade.setGrade(req.getGrade());
            this.clinicGradeRepository.save(clinicGrade);

            return true;
        }

        return false;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = REQUIRES_NEW)
    public boolean addClinicGrade(Patient patient, GradeRequest req) {
        if (!this.appointmentRepository.patientHasAppointment(req.getEntityId(), patient.getId()))
            return false;

        Optional<Clinic> clinic = this.clinicRepository.findById(req.getEntityId());

        ClinicGrade grade = new ClinicGrade();
        grade.setGrade(req.getGrade());
        grade.setClinic(clinic.get());
        grade.setPatient(patient);
        clinic.get().addGrade(grade);

//        try {
//            Thread.sleep(10000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        this.clinicRepository.save(clinic.get());

        return true;
    }

}
