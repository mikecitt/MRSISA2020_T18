package com.tim18.bolnicar.controller;


import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Drug;
import com.tim18.bolnicar.model.MedicalDiagnosis;
import com.tim18.bolnicar.service.impl.DiagnosisServiceImpl;
import com.tim18.bolnicar.service.impl.DrugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/codebook")
public class CodebookController {

    @Autowired
    private DrugServiceImpl drugService;

    @Autowired
    private DiagnosisServiceImpl diagnosisService;

    @PostMapping(
            path="/drug",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Map<String, String>> addDrug(@RequestBody Drug newDrug) {
        HashMap<String, String> response = new HashMap<>();

        try {
            drugService.save(newDrug);
            response.put("message", "true");
        } catch (Exception ex) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(
            path="/diagnosis",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Map<String, String>> addDiagnosis(@RequestBody MedicalDiagnosis newDiagnosis) {
        HashMap<String, String> response = new HashMap<>();

        try {
            diagnosisService.save(newDiagnosis);
            response.put("message", "true");
        } catch (Exception ex) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
