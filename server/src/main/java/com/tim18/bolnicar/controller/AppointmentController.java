package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.AppointmentDTO;
import com.tim18.bolnicar.dto.AppointmentPredefDTO;
import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.MedicalReportRepository;
import com.tim18.bolnicar.service.*;
import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.service.AppointmentService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    @Autowired
    private EmailService emailService;

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

    @PostMapping("/grade")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> gradeAppointment(@RequestBody GradeRequest req, Principal principal) {
        boolean flag = this.appointmentService.gradeAppointment(principal.getName(), req);
        Response resp = new Response();

        if (flag) {
            resp.setStatus("ok");
            return ResponseEntity.ok(resp);
        }

        resp.setStatus("error");
        resp.setDescription("Ocenjivanje pregleda nije moguce.");

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
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

            Object[] objArray = this.clinicAdminService.getAllEmails(app.getClinic()).toArray();
            String[] stringArray = Arrays.copyOf(objArray,
                    objArray.length, String[].class);
            this.emailService.sendMessages(
                    stringArray,
                    "[INFO] TERMINI",
                    "Poštovani,\n\nNovi zahtev je dodat, opis je u priloženom\n" +
                         this.appointmentService.appointmentInfo(app)
            );
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
        String emailMessage = "Poštovani,\n";

        Appointment appointment = this.appointmentService.findById(approval.getAppointmentId());
        Room room = this.roomService.findByRoomNumber(approval.getRoomNumber());

        if(clinicAdmin != null) {
            if(approval.isApproved()) {
                if(room != null && appointment != null) {
                    appointment.setActive(true);
                    String moveDate = "";
                    if(approval.getNewDate() != null) {
                        appointment.setDatetime(approval.getNewDate());
                        moveDate += "i pomeren je sa početnog datuma";
                    }

                    if(this.roomService.isRoomAlreadyTaken(room, appointment)) {
                        appointment.setRoom(room);
                        this.appointmentService.save(appointment);
                        resp.setStatus("ok");
                        resp.setDescription("true");
                        emailMessage += "\nOdobren je zahtev";
                        emailMessage += moveDate + " za" + "\n";
                        emailMessage += this.appointmentService.appointmentInfo(appointment);
                        this.emailService.sendMessages(new String[] {
                                appointment.getPatient().getEmailAddress(),
                                appointment.getDoctor().getEmailAddress()
                                },
                                "[INFO] TERMINI",
                                emailMessage
                        );
                    }
                    else {
                        resp.setStatus("error");
                        resp.setDescription("taken");
                        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                    }
                }
                else {
                    resp.setStatus("error");
                    return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                }
            }
            else {
                this.appointmentService.remove(appointment.getId());
                emailMessage += "\nOdbijen je zahtev za \n";
                emailMessage += this.appointmentService.appointmentInfo(appointment);
                this.emailService.sendMessages(new String[] {
                                appointment.getPatient().getEmailAddress(),
                                appointment.getDoctor().getEmailAddress()
                        },
                        "[INFO] TERMINI",
                        emailMessage
                );
                resp.setDescription("false");
            }
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/canStart")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> canStart(Principal user) {
        Response resp = new Response();
        Doctor doctor = doctorService.findOne(user.getName());
        if(doctor != null && doctor.getClinic() != null) {
            for(Appointment appointment : doctor.getClinic().getAppointments()) {
                if(appointment.getDoctor().getId().intValue() !=
                   doctor.getId())
                    continue;
                if(appointment.getReport() == null) {
                    Date now = new Date();
                    Date start = appointment.getDatetime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(appointment.getDatetime());
                    calendar.add(Calendar.MINUTE, appointment.getDuration());
                    Date end = calendar.getTime();
                    if (!now.after(end) && !now.before(start)) {
                        resp.setStatus("ok");
                        resp.setDescription("start");
                        Integer[] appId = new Integer[1];
                        appId[0] = appointment.getId();
                        resp.setData(appId);
                        return ResponseEntity.ok(resp);
                    }
                }
                else {
                    Date now = new Date();
                    Date start = appointment.getDatetime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(appointment.getDatetime());
                    calendar.add(Calendar.MINUTE, appointment.getDuration());
                    Date end = calendar.getTime();
                    if (!now.after(end) && !now.before(start)) {
                        resp.setStatus("ok");
                        resp.setDescription("started");
                        Integer[] appId = new Integer[1];
                        appId[0] = appointment.getId();
                        resp.setData(appId);
                        return ResponseEntity.ok(resp);
                    }
                }
            }
            resp.setStatus("ok");
            resp.setDescription("not started");
            return ResponseEntity.ok(resp);
        } else {
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{aid}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> getAppointment(@PathVariable int aid, Principal user) {
        Response resp = new Response();
        resp.setStatus("error");
        Appointment appointment = this.appointmentService.findById(aid);
        if(appointment != null) {
            resp.setStatus("ok");
            AppointmentDTO[] temp = new AppointmentDTO[1];
            temp[0] = new AppointmentDTO(appointment);
            resp.setData(temp);

            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/start/{aid}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> startAppointment(@PathVariable int aid, Principal user) {
        Response resp = new Response();
        Appointment appointment = this.appointmentService.findById(aid);
        if(appointment != null) {
            if(appointment.getReport() == null) {
                MedicalReport medicalReport = new MedicalReport();
                medicalReport.setAppointment(appointment);
                medicalReportRepository.save(medicalReport);
                appointment.setReport(medicalReport);
                appointmentService.save(appointment);
                resp.setStatus("ok");
                resp.setDescription("start");
            }
            else {
                resp.setStatus("ok");
                resp.setDescription("started");
            }
            return ResponseEntity.ok(resp);
        }
        else {
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }
}
