package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private ExaminationTypeService examinationTypeService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<DoctorDTO>> getDoctors(Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        List<DoctorDTO> doctors = new ArrayList<>();
        if(clinicAdmin != null && clinicAdmin.getClinic() != null) {
            List<Doctor> doctorsFromClinic= doctorService.findDoctorsFromClinic(clinicAdmin.getClinic().getId());
            for(Doctor d : doctorsFromClinic)
                doctors.add(new DoctorDTO(d));

            return ResponseEntity.ok(doctors);
        }
        return new ResponseEntity<>(doctors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/available/{dateTime}/{duration}/{specId}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<DoctorDTO>> getAvailableDoctors(@PathVariable String dateTime, @PathVariable int duration, @PathVariable int specId, Principal user) {
        ClinicAdmin clinicAdmin = this.clinicAdminService.findSingle(user.getName());
        if(clinicAdmin != null && clinicAdmin.getClinic() != null) {
            List<DoctorDTO> doctors = new ArrayList<>();
            List<DoctorDTO> busyDoctors = new ArrayList<>();

            for (MedicalWorker medicalWorker : clinicAdmin.getClinic().getWorkers()) { // ucitavanje svih lekara iz klinike
                if(medicalWorker instanceof Doctor) {
                    if(((Doctor) medicalWorker).getSpecialization().contains(examinationTypeService.findOne(specId)))
                        doctors.add(new DoctorDTO((Doctor) medicalWorker));
                }
            }

            for (Appointment appointment : clinicAdmin.getClinic().getAppointments()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                try {
                    Date date = sdf.parse(dateTime);
                    Date dateEnd = new Date(date.getTime() + TimeUnit.MINUTES.toMillis(duration));
                    Date appointmentDate = appointment.getDatetime();
                    Date appointmentDateEnd = new Date(appointmentDate.getTime() + TimeUnit.MINUTES.toMillis(appointment.getDuration().longValue()));
                    if(date.before(appointmentDateEnd) && appointmentDate.before(dateEnd))
                        busyDoctors.add(new DoctorDTO(appointment.getDoctor()));

                } catch (Exception ignored) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            doctors.removeAll(busyDoctors);

            return ResponseEntity.ok(doctors);
        }

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/available/{dateTime}/{duration}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Response> isDoctorAvailable(@PathVariable String dateTime, @PathVariable int duration, Principal user) {
        Response resp = new Response();
        resp.setStatus("error");
        Doctor doctor = this.doctorService.findOne(user.getName());
        if(doctor != null && doctor.getClinic() != null) {
            resp.setDescription("true");
            for (Appointment appointment : appointmentService.findAllDoctorsAppointments(doctor)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                try {
                    Date date = sdf.parse(dateTime);
                    Date dateEnd = new Date(date.getTime() + TimeUnit.MINUTES.toMillis(duration));
                    Date appointmentDate = appointment.getDatetime();
                    Date appointmentDateEnd = new Date(appointmentDate.getTime() + TimeUnit.MINUTES.toMillis(appointment.getDuration().longValue()));
                    if(date.before(appointmentDateEnd) && appointmentDate.before(dateEnd)) {
                        resp.setDescription("false");
                        break;
                    }

                } catch (Exception ignored) {
                    return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                }
            }

            resp.setStatus("ok");
            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody Doctor newDoctor, Principal user) {
        HashMap<String, String> response = new HashMap<>();
        ClinicAdmin cadmin = clinicAdminService.findSingle(user.getName());
        newDoctor.setClinic(cadmin.getClinic());

        if(doctorService.register(newDoctor)) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        Doctor doctor = doctorService.findOne(id);

        if (doctor != null) {
            doctorService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/freetime/{doctorId}/{date}")
    public ResponseEntity<List<TimeIntervalDTO>> getFreeTime(@PathVariable Integer doctorId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Response resp = new Response();

        // check if duration is always the same
        return ResponseEntity.ok(this.doctorService.getFreeDayTime(date, doctorId, 30));
    }
}
