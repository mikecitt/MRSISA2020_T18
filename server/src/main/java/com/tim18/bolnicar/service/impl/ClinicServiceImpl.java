package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.dto.DoctorDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.dto.TimeIntervalDTO;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.AppointmentRepository;
import com.tim18.bolnicar.repository.ClinicGradeRepository;
import com.tim18.bolnicar.repository.ClinicRepository;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.ClinicGradeTransService;
import com.tim18.bolnicar.service.ClinicService;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicGradeRepository clinicGradeRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    //TODO: discuss
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ClinicGradeTransService clinicGradeTransService;

    @Override
    public ClinicDTO getClinicProfile(int id) {
        Clinic clinic = clinicRepository.findById(id).orElseGet(null);
        return new ClinicDTO(clinic);
    }

    @Override
    public boolean updateClinicProfile(int id, ClinicDTO clinic) {
        Clinic clinicCurrent = this.clinicRepository.findById(id).orElseGet(null);

        //TODO: check data

        clinicCurrent.setName(clinic.getName());
        clinicCurrent.setAddress(clinic.getAddress());
        clinicCurrent.setDescription(clinic.getDescription());

        this.clinicRepository.save(clinicCurrent);

        return true;
    }

    @Override
    public Clinic findSingle(String name) {
        return clinicRepository.findByName(name);
    }

    @Override
    public Clinic findOne(int id) {
        return clinicRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Clinic> findAll() {
        return (List<Clinic>)clinicRepository.findAll();
    }

    @Override
    public List<ClinicDTO> findAll(String patientEmail) {
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);
        boolean votingRight = true;

        if (patient == null)
            votingRight = false;

        List<ClinicDTO> clinics = new ArrayList<>();

        //TODO: optimisation
        for (Clinic c : this.clinicRepository.findAll()) {
            ClinicDTO dto = new ClinicDTO(c);
            dto.setPatientGrade(null);
            if (votingRight) {
                // patient graded?
                boolean hasGrade = false;
                for (ClinicGrade g : c.getGrades()) {
                    if (g.getPatient().getId() == patient.getId()) {
                        dto.setPatientGrade(g.getGrade());
                        hasGrade = true;
                        break;
                    }
                }

                // buy time?
                if (!hasGrade) {
                    // patient has appointment in clinic?
                    for (Appointment a : c.getAppointments()) {
                        if (a.getPatient() != null &&
                                a.getPatient().getId() == patient.getId() &&
                                a.getReport() != null) {
                            //TODO: better check?
                            dto.setPatientGrade(0);
                            break;
                        }
                    }
                }
            }
            clinics.add(dto);
        }

        return clinics;
    }

    @Override
    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    @Override
    public List<ClinicDTO> getClinicsWithFreeAppointments(Date date, Integer examinationTypeId,
                                                       String address, Integer grade) {
        if (date == null || examinationTypeId == null)
            return null;

        Date now = new Date();
        
        List<ClinicDTO> clinics = new ArrayList<>();

        if (now.after(date)) {
            return clinics;
        }

        for (Clinic it : this.clinicRepository.findAll()) {
            ClinicDTO cl = new ClinicDTO(it);
            cl.setPatientGrade(null);
            List<DoctorDTO> freeDoctors = new ArrayList<>();

            // filter address
            if (address != null && !it.getAddress().toLowerCase().equals(address.toLowerCase()))
                continue;

            // filter grade
            if (grade != null && grade > 0) {
                double sum = 0.0;
                for (ClinicGrade c : it.getGrades()) {
                    sum += c.getGrade();
                }

                if (it.getGrades().size() > 0)
                    sum /= it.getGrades().size();

                if ((int)Math.rint(sum) != grade)
                    continue;
            }

            for (MedicalWorker worker : it.getWorkers()) {
                if (worker instanceof Doctor) {
                    // check examination type
                    boolean flag = false;
                    for (ExaminationType et : ((Doctor)worker).getSpecialization()) {
                        if (et.getId() == examinationTypeId) {
                            cl.setExaminationPrice(et.getPrice());
                            cl.setExaminationTypeId(examinationTypeId);
                            flag = true;
                            break;
                        }
                    }

                    // doctor is not specialized...
                    if (!flag)
                        continue;

                    // yes, it is
                    List<TimeIntervalDTO> free = this.doctorService.getFreeDayTime(date, worker.getId(), 30);
                    DoctorDTO doc = new DoctorDTO((Doctor)worker);
                    doc.setFreeIntervals(free);
                    freeDoctors.add(doc);
                }
            }

            // free doctors?
            if (freeDoctors.size() > 0) {
                cl.setFreeDoctors(freeDoctors);
                clinics.add(cl);
            }
        }

        return clinics;
    }


    @Override
    public boolean gradeClinic(String patientEmail, GradeRequest req) {

        if (req.getGrade() == null || req.getGrade() < 1 || req.getGrade() > 5 || req.getEntityId() == null)
            return false;

        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);

        if (patient == null)
            return false;

        Integer patientId = patient.getId();

        // transaction: required
        if (this.clinicGradeTransService.updateClinicGrade(patientId, req))
            return true;

        try {
            if (this.clinicGradeTransService.addClinicGrade(patient, req)) {
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }


    @Override
    public ClinicDTO getClinic(String patientEmail, Integer clinicId) {
        Optional<Clinic> clinic = this.clinicRepository.findById(clinicId);
        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);

        //TODO: null or exception?
        if (clinic.isEmpty() || patient == null)
            return null;

        ClinicDTO cl = new ClinicDTO(clinic.get());
        boolean graded = false;

        for (ClinicGrade c : clinic.get().getGrades()) {
            if (c.getPatient().getId() == patient.getId()) {
                graded = true;
                cl.setPatientGrade(c.getGrade());
                break;
            }
        }

        if (!graded)
            for (Appointment a : clinic.get().getAppointments()) {
                if (a.getPatient() != null &&
                        a.getPatient().getId() == patient.getId() &&
                        a.getReport() != null) {
                    cl.setPatientGrade(0);
                    break;
                }
            }

        return cl;
    }
}
