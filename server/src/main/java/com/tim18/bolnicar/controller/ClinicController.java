package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.User;
import com.tim18.bolnicar.service.ClinicAdminService;
import com.tim18.bolnicar.service.UserService;
import com.tim18.bolnicar.service.impl.ClinicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/clinic")
public class ClinicController {

    @Autowired
    private ClinicServiceImpl clinicService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CENTER_ADMIN')")
    public ResponseEntity<Map<String, String>> addClinic(@RequestBody Clinic newClinic) {
        HashMap<String, String> response = new HashMap<>();

        try {
            clinicService.save(newClinic);
            response.put("message", "true");
        } catch (Exception ex) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'CENTER_ADMIN')")
    public ResponseEntity<List<ClinicDTO>> getClinics() {
        //TODO: optimise?
        List<Clinic> clinics = this.clinicService.findAll();
        List<ClinicDTO> response = new ArrayList<>();

        for (Clinic clinic : clinics)
            response.add(new ClinicDTO(clinic));

        return ResponseEntity.ok(response);
    }

    //TODO: prava pristupa
    @GetMapping(path = "/profile")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicDTO> getClinicProfile(Principal user) {
        return ResponseEntity.ok(this.clinicService.getClinicProfile(this.clinicAdminService.findSingle(user.getName()).getClinic().getId()));
    }

    @PutMapping(path = "/profile")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ResponseReport> updateClinicProfile(@RequestBody ClinicDTO clinicProfileUpdates, Principal user) {
        ResponseReport report = new ResponseReport("error", "Forbidden action, contact admin.");

        if(!clinicProfileUpdates.getId().equals(this.clinicAdminService.findSingle(user.getName()).getClinic().getId()))
            return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);

        if(this.clinicService.updateClinicProfile(this.clinicAdminService.findSingle(user.getName()).getClinic().getId(), clinicProfileUpdates)) {
            report.setStatus("ok");
            report.setMessage("Profile successfully updated.");
            return ResponseEntity.ok(report);
        }

        report.setMessage(null);

        return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/free")
    public ResponseEntity<Response> getAvailableClinics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                @RequestParam Integer examinationTypeId,
                                                @RequestParam(required = false) String address,
                                                @RequestParam(required = false) Integer grade) {
        Response resp = new Response();
        resp.setStatus("ok");
        resp.setData(
                this.clinicService.getClinicsWithFreeAppointments(
                        date,
                        examinationTypeId,
                        address,
                        grade
                ).toArray());

        return ResponseEntity.ok(resp);
    }
}
