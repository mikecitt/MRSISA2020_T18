package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.MedicalReportDTO;
import com.tim18.bolnicar.dto.PatientDTO;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.model.MedicalReport;
import com.tim18.bolnicar.model.Patient;
import com.tim18.bolnicar.service.PatientService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    //TODO: get patient id from session token
    @GetMapping("/medicalRecord")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Map<String, List<MedicalReportDTO>>> getMedicalReport(Principal user) {
        //TODO: replace with MedicalRecordDTO
        HashMap<String, List<MedicalReportDTO>> data = new HashMap<>();
        data.put("data", this.patientService.getMedicalRecord(user.getName()));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('NURSE', 'DOCTOR')")
    public ResponseEntity<Map<String, List<PatientDTO>>> getPatients() {
        HashMap<String, List<PatientDTO>> data = new HashMap<>();
        data.put("data", this.patientService.getPatients());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/unregistered")
    @PreAuthorize("hasRole('CENTER_ADMIN')")
    public ResponseEntity<Map<String, List<PatientDTO>>> getUnregistered() {
        HashMap<String, List<PatientDTO>> data = new HashMap<>();
        data.put("unregistered", this.patientService.getUnregistered());
        return ResponseEntity.ok(data);
    }
}
