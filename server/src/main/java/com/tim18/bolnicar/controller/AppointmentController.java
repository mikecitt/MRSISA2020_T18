package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/{aid}/{pid}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ResponseReport> bookAppointment(@PathVariable Integer aid,
                                                          @PathVariable Integer pid) {
        if (this.appointmentService.bookAppointment(aid, pid))
            return new ResponseEntity<>(new ResponseReport("ok", "Appointment booked."), HttpStatus.OK);

        return new ResponseEntity<>(new ResponseReport("error", "Input is not valid."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/book/{aid}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> bookAppointment(@PathVariable Integer aid, Principal principal) {
        boolean status = this.appointmentService.bookAppointment(aid, principal.getName());
        Response resp = new Response();
        resp.setStatus("ok");

        if (!status) {
            resp.setStatus("error");
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/free/{cid}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> getFreeAppointments(@PathVariable Integer cid) {
        List<AppointmentDTO> appointments = this.appointmentService.getFreeAppointments(cid);
        Response resp = new Response();
        resp.setStatus("ok");
        resp.setData(appointments.toArray());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/request")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> makeRequest(@RequestBody AppointmentRequestDTO requestAppointment, Principal principal) {
        Response resp = new Response();

        //TODO: check two users
        if (requestAppointment.getAppointmentId() != null) {
            boolean flag =
                    this.appointmentService.bookAppointment(requestAppointment.getAppointmentId(), principal.getName());
            resp.setStatus(flag ? "ok" : "error");
            // resp.setDescription(flag ? "" : "");
            if (!flag)
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        } else {
            // create new
            Appointment app = this.appointmentService.addAppointmentRequest(requestAppointment, principal.getName());

            resp.setStatus(app != null ? "ok" : "error");

            if (app == null)
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(resp);
    }
}
