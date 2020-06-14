package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.model.ClinicCenterAdmin;
import com.tim18.bolnicar.service.impl.CCAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CCAdminServiceImpl ccAdminService;

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CENTER_ADMIN')")
    public ResponseEntity<HashMap<String, String>> addAdmin(@RequestBody ClinicCenterAdmin newAdmin) {
        HashMap<String, String> response = new HashMap<>();

        if(ccAdminService.register(newAdmin)) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
