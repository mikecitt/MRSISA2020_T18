package com.tim18.bolnicar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admincl")
public class AdminClController {

    private static Map<String, Map<String, String>> doctors = new HashMap<String, Map<String, String>>();

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )

    public ResponseEntity<HashMap<String, String>> addDoctor(@RequestBody Map<String, String> newDoctor) {
        HashMap<String, String> response = new HashMap<>();

        if(doctors.containsKey(newDoctor.get("username"))) {
            response.put("message", "false");
        }
        else {
            response.put("message", "true");
            doctors.put(newDoctor.get("username"), newDoctor);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
