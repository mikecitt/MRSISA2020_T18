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
import com.tim18.bolnicar.repository.NurseRepository;
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
import java.text.ParseException;
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

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private PatientService patientService;


    @PostMapping("/{aid}/{pid}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ResponseReport> bookAppointment(@PathVariable Integer aid,
                                                          @PathVariable Integer pid) {
        try {
            if (this.appointmentService.bookAppointment(aid, pid))
                return new ResponseEntity<>(new ResponseReport("ok", "Appointment booked."), HttpStatus.OK);
        } catch (Exception e) {
        }

        return new ResponseEntity<>(new ResponseReport("error", "Input is not valid."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/book/{aid}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> bookAppointment(@PathVariable Integer aid, Principal principal) {
        boolean status = false;
        Response resp = new Response();
        resp.setStatus("ok");

        try {
            status = this.appointmentService.bookAppointment(aid, principal.getName());
        } catch (Exception e) {
            resp.setStatus("error");
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }

        //boolean status = this.appointmentService.bookAppointment(aid, principal.getName());
        //Response resp = new Response();
       // resp.setStatus("ok");

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
            appointment.setPrice(appointment.getType().getPrice() * (1 - appointment.getDiscount()));
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
            boolean flag;
            try {
                flag = this.appointmentService.bookAppointment(requestAppointment.getAppointmentId(), principal.getName());
            } catch (Exception e) {
                flag = false;
            }
            //boolean flag =
            //        this.appointmentService.bookAppointment(requestAppointment.getAppointmentId(), principal.getName());
            resp.setStatus(flag ? "ok" : "error");
            // resp.setDescription(flag ? "" : "");
            if (!flag)
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        } else {
            // create new
            Appointment app = null;
            try {
                app = this.appointmentService.addAppointmentRequest(requestAppointment, principal.getName());
            } catch (Exception e) {
            }
            ///Appointment app = this.appointmentService.addAppointmentRequest(requestAppointment, principal.getName());
            resp.setStatus(app != null ? "ok" : "error");

            if (app == null)
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

            Object[] objArray = this.clinicAdminService.getAllEmails(app.getClinic()).toArray();
            String[] stringArray = Arrays.copyOf(objArray,
                    objArray.length, String[].class);
            if (stringArray.length > 0)
                this.emailService.sendMessages(
                        stringArray,
                        "[INFO] TERMINI",
                        "Poštovani,\n\nNovi zahtev je dodat, opis je u priloženom\n" +
                             this.appointmentService.appointmentInfo(app)
                );
        }

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/request-doc/{patientId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> makeRequestByDoctor(@RequestBody AppointmentPredefDTO requestAppointment, @PathVariable Integer patientId, Principal principal) {
        Response resp = new Response();
        resp.setStatus("error");

        Doctor doctor = doctorService.findOne(principal.getName());
        if(doctor == null)
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        Patient patient = patientService.getPatient(patientId);
        if(patient == null)
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        if(!patientService.isDoctorPatient(patient, doctor.getId()))
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
        appointmentRequestDTO.setClinicId(doctor.getClinic().getId());
        appointmentRequestDTO.setDoctorId(doctor.getId());
        appointmentRequestDTO.setRoomType(requestAppointment.getRoomType());
        appointmentRequestDTO.setExaminationTypeId(requestAppointment.getType());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(requestAppointment.getDatetime()));
        } catch (ParseException e) {
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        appointmentRequestDTO.setStart(calendar.getTime());
        calendar.add(Calendar.MINUTE, requestAppointment.getDuration());
        appointmentRequestDTO.setEnd(calendar.getTime());


        Appointment app = this.appointmentService.addAppointmentRequest(appointmentRequestDTO, patient.getEmailAddress());
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

    @GetMapping("/diagnosis")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> getDiagnosis() {
        Response resp = new Response();
        resp.setStatus("ok");
        List<DiagnosisDTO> diagnosiss = new ArrayList<DiagnosisDTO>();
        for(MedicalDiagnosis diagnosis : this.diagnosisService.findAll()) {
            diagnosiss.add(new DiagnosisDTO(diagnosis));
        }
        resp.setData(diagnosiss.toArray());

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/drug")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> getDrug() {
        Response resp = new Response();
        resp.setStatus("ok");
        List<DrugDTO> drugs = new ArrayList<DrugDTO>();
        for(Drug drug : this.drugService.findAll()) {
            drugs.add(new DrugDTO(drug));
        }
        resp.setData(drugs.toArray());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/saveAppointment")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> saveAppointment(@RequestBody AppointmentSave appointment) {
        Response resp = new Response();
        resp.setStatus("ok");
        Appointment app = this.appointmentService.findById(appointment.getAppointmentId());

        if(app != null) {
            app.getReport().setDescription(appointment.getDescription());
            app.getReport().setDiagnoses(new HashSet<MedicalDiagnosis>(appointment.getDiagnosis()));
            Set<Recipe> recipe = new HashSet<Recipe>();
            for(Drug drug : appointment.getRecipe()) {
                Recipe r = new Recipe();
                r.setDrug(drug);
                r.setSealed(false);
                recipe.add(r);
            }
            app.getReport().setRecipes(recipe);

            this.appointmentService.save(app);
        }
        else {
            resp.setStatus("error");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/recipes")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<Response> getRecipes(Principal user) {
        Response resp = new Response();
        resp.setStatus("error");
        Nurse nurse = this.nurseService.findOne(user.getName());

        if(nurse != null) {
            List<RecipeDTO> recepis = new ArrayList<RecipeDTO>();
            for(Appointment app : nurse.getClinic().getAppointments()) {
                Date now = new Date();
                Date start = app.getDatetime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(app.getDatetime());
                calendar.add(Calendar.MINUTE, app.getDuration());
                Date end = calendar.getTime();
                if(now.after(end)) {
                    for(Recipe r : app.getReport().getRecipes()) {
                        RecipeDTO temp = new RecipeDTO(r);
                        temp.setGivenBy(app.getDoctor().getFirstName() + " " +
                                app.getDoctor().getLastName());
                        temp.setAppointmentId(app.getId());
                        recepis.add(temp);
                    }
                }
            }

            resp.setStatus("ok");
            resp.setData(recepis.toArray());

            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/recipe/{aid}/{rid}")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<Response> checkRecipe(@PathVariable int aid, @PathVariable int rid, Principal user) {
        Response resp = new Response();
        resp.setStatus("error");
        Nurse nurse = this.nurseService.findOne(user.getName());
        Appointment app = this.appointmentService.findById(aid);

        if(nurse != null && app != null) {
            resp.setStatus("ok");
            for(Recipe recipe : app.getReport().getRecipes()) {
                if(recipe.getId() == rid) {
                    recipe.setSealed(true);
                    recipe.setNurse(nurse);
                    break;
                }
            }
            this.appointmentService.save(app);
            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
