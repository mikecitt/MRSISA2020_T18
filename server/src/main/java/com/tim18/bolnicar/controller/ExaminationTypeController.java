package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.model.ExaminationType;
import com.tim18.bolnicar.service.impl.ExaminationTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/examination-type")
public class ExaminationTypeController {

    @Autowired
    private ExaminationTypeServiceImpl examinationTypeService;

    @PostMapping(
            path = "/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Map<String, String>> addExaminationType(@RequestBody ExaminationType newExaminationType) {
        HashMap<String, String> response = new HashMap<>();

        try {
            examinationTypeService.save(newExaminationType);
            response.put("message", "true");
        } catch (Exception ex) {
            response.put("message", "false");
            System.out.println(ex);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
