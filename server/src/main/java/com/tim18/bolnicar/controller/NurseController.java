package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.model.Nurse;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            timeOffs.put("data", new ArrayList<TimeOff>(nurse.getCalendar()));
        }
        return ResponseEntity.ok(timeOffs);
    }
}
