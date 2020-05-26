package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.service.MedicalWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/medical")
public class MedicalControler {

    @Autowired
    private MedicalWorkerService medicalWorkerService;

    @GetMapping(value = "/timeoff")
    @PreAuthorize("hasRole('DOCTOR', 'NURSE')")
    public ResponseEntity<Map<String, List<TimeOff>>> getTimeOffs(Principal user) {
        HashMap<String, List<TimeOff>> timeOffs = new HashMap<String, List<TimeOff>>();

        MedicalWorker medicalWorker = medicalWorkerService.findOne(user.getName());

        if(medicalWorker != null) {
            timeOffs.put("data", new ArrayList<TimeOff>(medicalWorker.getActiveCalendar()));
        }
        return ResponseEntity.ok(timeOffs);
    }

    @PostMapping(path = "/timeoff")
    @PreAuthorize("hasRole('DOCTOR', 'NURSE')")
    public ResponseEntity<ResponseReport> postTimeOff(@RequestBody TimeOff timeOff,
                                                      Principal user) {
        MedicalWorker medicalWorker = this.medicalWorkerService.findOne(user.getName());

        boolean flag = true;

        try {
            medicalWorker.addTimeOff(timeOff);
            this.medicalWorkerService.save(medicalWorker);
        } catch(Exception ex) {
            flag = false;
        }

        if (flag) {
            return new ResponseEntity<>(
                    new ResponseReport("ok", "Your time off request is successfully created."),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new ResponseReport("error", "Invalid input."),
                HttpStatus.BAD_REQUEST);
    }
}
