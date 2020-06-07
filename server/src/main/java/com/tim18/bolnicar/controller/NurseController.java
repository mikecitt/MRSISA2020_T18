package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.service.ClinicAdminService;
import com.tim18.bolnicar.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @GetMapping
    public ResponseEntity<List<NurseDTO>> getNurses() {
        List<Nurse> nurses = this.nurseService.findAll();
        List<NurseDTO> response = new ArrayList<>();

        for (Nurse nurse : nurses) {
            response.add(new NurseDTO(nurse));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addNurse(@RequestBody Nurse newNurse, Principal user) {
        HashMap<String, String> response = new HashMap<>();
        ClinicAdmin cadmin = clinicAdminService.findSingle(user.getName());
        newNurse.setClinic(cadmin.getClinic());

        if(nurseService.register(newNurse)) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> deleteNurse(@PathVariable int id) {
        Nurse nurse = nurseService.findOne(id);

        if (nurse != null) {
            nurseService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    Is this required?

    @PutMapping
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ResponseReport> updateNurse(@RequestBody Nurse nurseUpdates) {
        ResponseReport report = new ResponseReport("error", "Forbidden action, contact admin.");

        if(this.nurseService.save(nurseUpdates) != null) {
            report.setStatus("ok");
            report.setMessage("Profile successfully updated.");
            return ResponseEntity.ok(report);
        }

        report.setMessage(null);
        return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
    }*/
}
