package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.dto.DoctorDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.dto.TimeIntervalDTO;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.ClinicGradeRepository;
import com.tim18.bolnicar.repository.ClinicRepository;
import com.tim18.bolnicar.repository.PatientRepository;
import com.tim18.bolnicar.service.ClinicService;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.security.Timestamp;
import java.util.*;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicGradeRepository clinicGradeRepository;

    //TODO: discuss
    @Autowired
    private DoctorService doctorService;

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
                                                       String address, Integer grade, String patientEmail) {
        if (date == null || examinationTypeId == null)
            return null;

        List<ClinicDTO> clinics = new ArrayList<>();

        //TODO: filter by address and grade
        for (Clinic it : this.clinicRepository.findAll()) {
            ClinicDTO cl = new ClinicDTO(it);
            cl.setPatientGrade(null);
            List<DoctorDTO> freeDoctors = new ArrayList<>();

            if (address != null && !it.getAddress().toLowerCase().equals(address.toLowerCase()))
                continue;
            //TODO: check grade

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

                // pack grade
                Patient patient = this.patientRepository.findByEmailAddress(patientEmail);
                if (patient != null) {
                    boolean hasGrade = false;
                    for (ClinicGrade g : it.getGrades()) {
                        if (g.getPatient().getId() == patient.getId()) {
                            cl.setPatientGrade(g.getGrade());
                            hasGrade = true;
                            break;
                        }
                    }

                    if (!hasGrade) {
                        for (Appointment a : it.getAppointments()) {
                            if (a.getPatient() != null &&
                                    a.getPatient().getId() == patient.getId() &&
                                    a.getReport() != null) {
                                //TODO: better check done?
                                cl.setPatientGrade(0);
                                break;
                            }
                        }
                    }
                }

                clinics.add(cl);
            }
        }

        return clinics;
    }


    @Override
    //TODO: optimise
    public boolean gradeClinic(String patientEmail, GradeRequest req) {

        if (req.getGrade() == null || req.getGrade() < 1 || req.getGrade() > 5 || req.getEntityId() == null)
            return false;

        Patient patient = this.patientRepository.findByEmailAddress(patientEmail);

        if (patient == null)
            return false;

        Optional<Clinic> clinic = this.clinicRepository.findById(req.getEntityId());

        if (clinic.isEmpty())
            return false;

        // grade exists?
        for (ClinicGrade grade : clinic.get().getGrades()) {
            if (grade.getPatient().getId() == patient.getId()) {
                grade.setGrade(req.getGrade());
                this.clinicGradeRepository.save(grade);
                return true;
            }
        }

        // has appointment?
        for (Appointment app : clinic.get().getAppointments()) {
            if (app.getPatient() != null &&
                    app.getPatient().getId() == patient.getId() &&
                    app.getReport() != null) {
                ClinicGrade grade = new ClinicGrade();
                grade.setGrade(req.getGrade());
                grade.setClinic(clinic.get());
                grade.setPatient(patient);
                clinic.get().addGrade(grade);
                this.clinicRepository.save(clinic.get());
                return true;
            }
        }

        return false;
    }
}
