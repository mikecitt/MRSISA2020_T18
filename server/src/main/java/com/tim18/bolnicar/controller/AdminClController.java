package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.VacationRequest;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.service.impl.ClinicAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
                if(!timeOff.isActive()) {
                    VacationRequest vacationRequest = new VacationRequest();
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
}
