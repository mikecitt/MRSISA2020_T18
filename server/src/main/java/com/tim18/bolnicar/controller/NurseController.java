package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.model.Nurse;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.service.NurseService;
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
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @GetMapping(value = "/timeoff")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<Map<String, List<TimeOff>>> getTimeOffs(Principal user) {
        HashMap<String, List<TimeOff>> timeOffs = new HashMap<String, List<TimeOff>>();

        Nurse nurse = nurseService.findOne(user.getName());

        if(nurse != null) {
            timeOffs.put("data", new ArrayList<TimeOff>(nurse.getActiveCalendar()));
        }
        return ResponseEntity.ok(timeOffs);
    }

    @PostMapping(path = "/timeoff")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<ResponseReport> postTimeOff(@RequestBody TimeOff timeOff,
                                                      Principal user) {
        Nurse nurse = this.nurseService.findOne(user.getName());

        boolean flag = true;

        try {
            nurse.addTimeOff(timeOff);
            this.nurseService.save(nurse);
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
