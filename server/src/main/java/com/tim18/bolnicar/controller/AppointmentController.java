package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/{aid}/{pid}")
    public ResponseEntity<ResponseReport> bookAppointment(@PathVariable Integer aid,
                                                          @PathVariable Integer pid) {
        if (this.appointmentService.bookAppointment(aid, pid))
            return new ResponseEntity<>(new ResponseReport("ok", "Appointment booked."), HttpStatus.OK);

        return new ResponseEntity<>(new ResponseReport("error", "Input is not valid."), HttpStatus.BAD_REQUEST);
    }
}
