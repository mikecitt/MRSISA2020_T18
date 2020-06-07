package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.DoctorDTO;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.service.ClinicAdminService;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<Doctor> doctors = this.doctorService.findAll();
        List<DoctorDTO> response = new ArrayList<>();

        for (Doctor doctor : doctors) {
            response.add(new DoctorDTO(doctor));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody Doctor newDoctor, Principal user) {
        HashMap<String, String> response = new HashMap<>();
        ClinicAdmin cadmin = clinicAdminService.findSingle(user.getName());
        newDoctor.setClinic(cadmin.getClinic());

        if(doctorService.register(newDoctor)) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        Doctor doctor = doctorService.findOne(id);

        if (doctor != null) {
            doctorService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
