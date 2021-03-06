package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.ExaminationType;
import com.tim18.bolnicar.service.DoctorService;
import com.tim18.bolnicar.service.ExaminationTypeService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/examination-type")
public class ExaminationTypeController {

    @Autowired
    ExaminationTypeService examinationTypeService;

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<ExaminationType>> getExaminationTypes() {
        return new ResponseEntity<>(examinationTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/spec")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> getDoctorsSpecialists(Principal user) {
        Doctor doctor = doctorService.findOne(user.getName());
        Response resp = new Response();
        resp.setStatus("error");
        if(doctor != null) {
            resp.setStatus("ok");
            resp.setData(doctor.getSpecialization().toArray());
            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(
            path = "/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
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

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> deleteExaminationType(@PathVariable int id) {
        ExaminationType examinationType = examinationTypeService.findOne(id);

        if (examinationType != null) {
            examinationTypeService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ExaminationType> getExaminationType(@PathVariable int id) {
        return new ResponseEntity<>(examinationTypeService.findOne(id), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ResponseReport> updateExaminationType(@RequestBody ExaminationType examinationTypeChanges) {
        ResponseReport report = new ResponseReport("error", "Forbidden action, contact admin.");

        if(this.examinationTypeService.save(examinationTypeChanges) != null) {
            report.setStatus("ok");
            report.setMessage("Profile successfully updated.");
            return ResponseEntity.ok(report);
        }

        report.setMessage(null);

        return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
    }
}
