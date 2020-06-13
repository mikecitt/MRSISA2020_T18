package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.MedicalReport;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.service.PatientService;
import com.tim18.bolnicar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @GetMapping("/medicalRecord/{patientId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> getMedicalReport(@PathVariable Integer patientId) {
        MedicalRecordDTO record = null;
        Response resp = new Response();

        if (patientId != null)
            record = this.patientService.getMedicalRecord(patientId);
        else {
            resp.setStatus("error");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        if (record == null) {
            resp.setStatus("error");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        resp.setStatus("ok");
        resp.setData(new Object[] { record });
        return ResponseEntity.ok(resp);
    }


    @GetMapping("/medicalRecord")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> getMedicalReport(Principal user) {
        MedicalRecordDTO record = null;

        record = this.patientService.getMedicalRecord(user.getName());

        Response resp = new Response();

        if (record == null) {
            resp.setStatus("error");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        resp.setStatus("ok");
        resp.setData(new Object[] { record });
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('NURSE', 'DOCTOR')")
    public ResponseEntity<List<MedicalRecordDTO>> getPatients(Principal user) {
        List<MedicalRecordDTO> patientDTOList = new ArrayList<>();
        MedicalWorker medicalWorker = (MedicalWorker) userService.findByEmailAddress(user.getName());
        if(medicalWorker != null && medicalWorker.getClinic() != null)
            for(Appointment appointment : medicalWorker.getClinic().getAppointments()) {
                if(appointment.getPatient() != null) {
                    MedicalRecordDTO patientDTO = this.patientService.
                            getMedicalRecord(appointment.getPatient().getId());
                    if (!patientDTOList.contains(patientDTO))
                        patientDTOList.add(patientDTO);
                }
            }

        return ResponseEntity.ok(patientDTOList);
    }

    @GetMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> getAppointments(Principal user) {
        Response response = new Response();
        response.setStatus("ok");
        response.setData(this.patientService.getAppointmentsHistory(user.getName()).toArray());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/unregistered")
    @PreAuthorize("hasRole('CENTER_ADMIN')")
    public ResponseEntity<Map<String, List<PatientDTO>>> getUnregistered() {
        HashMap<String, List<PatientDTO>> data = new HashMap<>();
        data.put("unregistered", this.patientService.getUnregistered());
        return ResponseEntity.ok(data);
    }
}
