package com.tim18.bolnicar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping(path="/")
    public ResponseEntity<ArrayList<Map<String, String>>> getDoctors() {
        ArrayList<Map<String, String>> doctors = new ArrayList<Map<String, String>>();

        HashMap<String, String> doctor1 = new HashMap<>();
        doctor1.put("name", "John");
        doctor1.put("surname", "Anderson");

        HashMap<String, String> doctor2 = new HashMap<>();
        doctor2.put("name", "Mark");
        doctor2.put("surname", "Marks");

        HashMap<String, String> doctor3 = new HashMap<>();
        doctor3.put("name", "Emma");
        doctor3.put("surname", "Johnson");

        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}
