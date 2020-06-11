package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentPredefDTO;
import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.service.*;
import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.service.AppointmentService;
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

    @GetMapping("/request")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response> getRequests(Principal user) {
        Response resp = new Response();
        ClinicAdmin clinicAdmin = this.clinicAdminService.findSingle(user.getName());

        if(clinicAdmin != null) {
            resp.setStatus("ok");
            resp.setData(this.appointmentService
                    .findAllAppointmentRequests(clinicAdmin.getClinic()).toArray());
        }
        else {
            resp.setStatus("error");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response> approveAppointment(@RequestBody Approval approval, Principal user) {
        Response resp = new Response();
        ClinicAdmin clinicAdmin = this.clinicAdminService.findSingle(user.getName());
        String emailMessage = "";

        if(clinicAdmin != null) {
            if(approval.isApproved()) {
                Appointment appointment = this.appointmentService.findById(approval.getAppointmentId());
                Room room = this.roomService.findByRoomNumber(approval.getRoomNumber());

                if(room != null && appointment != null) {
                    appointment.setActive(true);
                    appointment.setRoom(room);
                    if(approval.getNewDate() != null) {
                        appointment.setDatetime(approval.getNewDate());
                    }
                    resp.setStatus("ok");
                    resp.setDescription("true");
                }
                else {
                    resp.setStatus("error");
                    return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                }
            }
            else {
                resp.setDescription("false");
            }
        }

        // TODO: posalati mejl lekaru i pacijentu
        return ResponseEntity.ok(resp);
    }
}
