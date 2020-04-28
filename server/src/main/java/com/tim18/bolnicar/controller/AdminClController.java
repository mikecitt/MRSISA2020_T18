package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.service.impl.ClinicAdminServiceImpl;
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
    private ClinicAdminServiceImpl clinicAdminService;

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )

    public ResponseEntity<Map<String, String>> addAdmin(@RequestBody ClinicAdmin newClinicAdmin) {
        HashMap<String, String> response = new HashMap<>();

        try {
            clinicAdminService.save(newClinicAdmin);
            response.put("message", "true");
        } catch(Exception e) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
