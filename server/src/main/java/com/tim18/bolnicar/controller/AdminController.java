package com.tim18.bolnicar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private static ArrayList<Map<String, String>> admins = new ArrayList<Map<String, String>>();

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<HashMap<String, String>> addAdmin(@RequestBody Map<String, String> newAdmin) {
        HashMap<String, String> response = new HashMap<>();

        if(admins.contains(newAdmin)) {
            response.put("message", "false");
        }
        else {
            admins.add(newAdmin);
            response.put("message", "true");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
