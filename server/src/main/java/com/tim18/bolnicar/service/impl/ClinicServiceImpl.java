package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.dto.DoctorDTO;
import com.tim18.bolnicar.dto.TimeIntervalDTO;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.ExaminationType;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.repository.ClinicRepository;
import com.tim18.bolnicar.service.ClinicService;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.*;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

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
    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    @Override
    public List<ClinicDTO> getClinicsWithFreeAppointments(Date date, Integer examinationTypeId,
                                                       String address, Integer grade) {
        if (date == null || examinationTypeId == null)
            return null;

        List<ClinicDTO> clinics = new ArrayList<>();

        //TODO: filter by address and grade
        for (Clinic it : this.clinicRepository.findAll()) {
            ClinicDTO cl = new ClinicDTO(it);
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
                            flag = true;
                            break;
                        }
                    }

                    // doctor is not specialized...
                    if (!flag)
                        continue;

                    // yes, it is
                    List<TimeIntervalDTO> free = this.doctorService.getFreeDayTime(date, worker.getId());
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
}
