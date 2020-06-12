package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ClinicDTO;
import com.tim18.bolnicar.dto.GradeRequest;
import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.*;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public ResponseEntity<List<ClinicDTO>> getClinics(Principal principal) {
        //TODO: optimise?
        List<ClinicDTO> clinics = this.clinicService.findAll(principal.getName());

        return ResponseEntity.ok(clinics);
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
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> getAvailableClinics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                        @RequestParam Integer examinationTypeId,
                                                        @RequestParam(required = false) String address,
                                                        @RequestParam(required = false) Integer grade,
                                                        Principal principal) {
        Response resp = new Response();
        resp.setStatus("ok");
        resp.setData(
                this.clinicService.getClinicsWithFreeAppointments(
                        date,
                        examinationTypeId,
                        address,
                        grade,
                        principal.getName()
                ).toArray());

        return ResponseEntity.ok(resp);
    }


    @PostMapping("/grade")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> gradeClinic(@RequestBody GradeRequest req, Principal principal) {
        boolean flag = this.clinicService.gradeClinic(principal.getName(), req);
        Response resp = new Response();

        if (flag) {
            resp.setStatus("ok");
            return ResponseEntity.ok(resp);
        }

        resp.setStatus("error");
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/income")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response> getClinicIncome(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date dateFrom, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date dateTo, Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        Response resp = new Response();
        resp.setStatus("error");
        //Date now = new Date();
        if(dateFrom.after(dateTo)) {
            resp.setDescription("invalid dates passed");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        double sum = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTo);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dateTo.setTime(calendar.getTimeInMillis());
        Double[] res = new Double[1];

        if(clinicAdmin != null && clinicAdmin.getClinic() != null) {
            for(Appointment appointment : clinicAdmin.getClinic().getAppointments()) {
                if (appointment.getReport() != null && appointment.getDatetime().after(dateFrom) && appointment.getDatetime().before(dateTo)) {
                    sum += appointment.getType().getPrice() * (1 - appointment.getDiscount());
                }
            }
            res[0] = sum;
            resp.setStatus("ok");
            resp.setData(res);
            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/appointmentsSummary")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response>getAppointmentsSummary(Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        Response resp = new Response();
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        Object[] objects = new Object[1];

        if(clinicAdmin != null && clinicAdmin.getClinic() != null) {
            for(Appointment appointment : clinicAdmin.getClinic().getAppointments()) {
                if(appointment.getReport() != null) {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date = formatter.format(appointment.getDatetime());

                    if (hashMap.containsKey(date))
                        hashMap.put(date, hashMap.get(date) + 1);
                    else
                        hashMap.put(date, 1);
                }
            }

            resp.setStatus("ok");
            objects[0] = hashMap;
            resp.setData(objects);
            return ResponseEntity.ok(resp);
        }
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
