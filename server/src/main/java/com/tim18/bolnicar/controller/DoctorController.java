package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.DoctorDTO;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping(path="/")
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<Doctor> doctors = this.doctorService.findAll();
        List<DoctorDTO> response = new ArrayList<>();

        for (Doctor doctor : doctors) {
            response.add(new DoctorDTO(doctor));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody Doctor newDoctor) {
        HashMap<String, String> response = new HashMap<>();
        System.out.println(newDoctor);
        Doctor doctor = new Doctor();
        try {
            doctor = doctorService.save(newDoctor);
            response.put("message", "true");

        } catch(Exception e) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
