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

    private static Map<String, Map<String, String>> admins = new HashMap<String, Map<String, String>>();
    private final static Map<String, String> ADMIN = new HashMap<String, String>() {{
        put("firstname", "Admin");
        put("lastname", "Admin");
        put("username", "admin");
        put("email", "admin@gmail.com");
        put("password", "admin123");
    }};

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<HashMap<String, String>> addAdmin(@RequestBody Map<String, String> newAdmin) {
        HashMap<String, String> response = new HashMap<>();

        if(admins.containsKey(newAdmin.get("username")) || ADMIN.get("username").equals(newAdmin.get("username"))) {
            response.put("message", "false");
        }
        else {
            admins.put(newAdmin.get("username"), newAdmin);
            response.put("message", "true");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
