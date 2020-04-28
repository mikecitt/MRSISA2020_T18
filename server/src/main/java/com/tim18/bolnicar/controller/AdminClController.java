package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admincl")
public class AdminClController {

    @Autowired
    private DoctorService doctorService;

    private static Map<String, Map<String, String>> doctors = new HashMap<String, Map<String, String>>();

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )

    public ResponseEntity<HashMap<String, String>> addDoctor(@RequestBody Doctor newDoctor) {
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

        /*
        if(newDoctor.get("firstname").isEmpty() || newDoctor.get("lastname").isEmpty() ||
                newDoctor.get("emailAddress").isEmpty() || newDoctor.get("password").isEmpty() ||
                newDoctor.get("address").isEmpty() || newDoctor.get("city").isEmpty() ||
                newDoctor.get("country").isEmpty() || newDoctor.get("contact").isEmpty())
            response.put("message", "false");
        else if(doctors.containsKey(newDoctor.get("emailAddress"))) {
            response.put("message", "false");
        }
        else {
            response.put("message", "true");

            doctors.put(newDoctor.get("emailAddress"), newDoctor);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);*/
    }


}
