package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.service.impl.ClinicServiceImpl;
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
@RequestMapping("/clinic")
public class ClinicController {

    @Autowired
    private ClinicServiceImpl clinicService;

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CENTER_ADMIN')")
    public ResponseEntity<Map<String, String>> addClinic(@RequestBody Clinic newClinic) {
        HashMap<String, String> response = new HashMap<>();

        try {
            clinicService.save(newClinic);
            response.put("message", "true");
        } catch (Exception ex) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'CENTER_ADMIN')")
    public ResponseEntity<List<ClinicDTO>> getClinics() {
        //TODO: optimise?
        List<Clinic> clinics = this.clinicService.findAll();
        List<ClinicDTO> response = new ArrayList<>();

        for (Clinic clinic : clinics)
            response.add(new ClinicDTO(clinic));

        return ResponseEntity.ok(response);
    }
}
