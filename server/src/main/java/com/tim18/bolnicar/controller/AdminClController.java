package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.Acceptance;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.dto.VacationRequest;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.service.EmailService;
import com.tim18.bolnicar.service.TimeOffService;
import com.tim18.bolnicar.service.UserService;
import com.tim18.bolnicar.service.impl.ClinicAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admincl")
public class AdminClController {

    @Autowired
    private ClinicAdminServiceImpl clinicAdminService;

    @Autowired
    private TimeOffService timeOffService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CENTER_ADMIN')")
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

    @GetMapping(path = "/vacations")
    public ResponseEntity<List<VacationRequest>> getVacationRequests(Principal user) {
        Clinic clinic = clinicAdminService.findSingle(user.getName()).getClinic();

        List<VacationRequest> vacationRequests = new ArrayList<>();
        for(MedicalWorker medicalWorker : clinic.getWorkers()) {
            for(TimeOff timeOff : medicalWorker.getCalendar()) {
                if(timeOff.isActive() == null) {
                    VacationRequest vacationRequest = new VacationRequest();
                    vacationRequest.setId(timeOff.getId().toString());
                    vacationRequest.setFirstName(medicalWorker.getFirstName());
                    vacationRequest.setLastName(medicalWorker.getLastName());
                    vacationRequest.setJmbg(medicalWorker.getJmbg());
                    vacationRequest.setStartDate(timeOff.getStartDate());
                    vacationRequest.setEndDate(timeOff.getEndDate());
                    vacationRequests.add(vacationRequest);
                }
            }
        }

        return new ResponseEntity<>(vacationRequests, HttpStatus.OK);
    }

    @PostMapping(path = "/vacation")
    public ResponseEntity<Map<String, String>> setVacation(@RequestBody TimeOff timeOff) {
        HashMap<String, String> response = new HashMap<>();

        TimeOff timeOffChanged = timeOffService.findOne(timeOff.getId());
        timeOffChanged.setActive(timeOff.isActive());

        try {
            timeOffService.save(timeOffChanged);
            response.put("message", "true");
        } catch(Exception e) {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/acceptance")
    public ResponseEntity<ResponseReport> resolveRequest(@RequestBody Acceptance acceptance) {
        User user = userService.findByJmbg(acceptance.getUserJmbg());
        if(user != null) {
            this.emailService.sendMessage(
                    user.getEmailAddress(),
                    "Resenje zahteva za godisnji odmor",
                    acceptance.getMessage()
            );
            return new ResponseEntity<>(
                    new ResponseReport("ok", "Request is successfully processed."),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(
                    new ResponseReport("error", "Something went wrong."),
                    HttpStatus.OK);
        }
    }
}
