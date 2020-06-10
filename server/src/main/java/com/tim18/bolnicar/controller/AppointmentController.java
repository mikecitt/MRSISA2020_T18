package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentPredefDTO;
import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ExaminationTypeService examinationTypeService;

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

    @PostMapping
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response> addPredefinedAppointment(@RequestBody AppointmentPredefDTO appointmentPredefDTO, Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        Response resp = new Response();
        resp.setStatus("error");
        if(clinicAdmin != null && clinicAdmin.getClinic() != null) {
            Appointment appointment = new Appointment();
            appointment.setClinic(clinicAdmin.getClinic());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            try {
                appointment.setDatetime(sdf.parse(appointmentPredefDTO.getDatetime()));
            } catch (Exception ignored) {
                return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
            }
            appointment.setDuration(appointmentPredefDTO.getDuration());
            appointment.setDiscount(0.0);
            appointment.setType(this.examinationTypeService.findOne(appointmentPredefDTO.getType()));
            appointment.setDoctor(this.doctorService.findOne(appointmentPredefDTO.getDoctor()));
            appointment.setRoom(this.roomService.findOne(appointmentPredefDTO.getRoom()));

            if(appointmentService.addAppointment(appointment)) {
                resp.setStatus("ok");
                return ResponseEntity.ok(resp);
            }
        }
        return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
    }

}
