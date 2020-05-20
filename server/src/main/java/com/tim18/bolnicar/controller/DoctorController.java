package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.DoctorDTO;
import com.tim18.bolnicar.dto.Event;
import com.tim18.bolnicar.model.Doctor;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.service.AppointmentService;
import com.tim18.bolnicar.service.DoctorService;
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
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(path="/")
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<Doctor> doctors = this.doctorService.findAll();
        List<DoctorDTO> response = new ArrayList<>();

        for (Doctor doctor : doctors) {
            response.add(new DoctorDTO(doctor));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            path="/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody Doctor newDoctor) {
        HashMap<String, String> response = new HashMap<>();
        System.out.println(newDoctor);
        Doctor doctor = new Doctor();
        try {
            doctor = doctorService.save(newDoctor);
            response.put("message", "true");

        } catch(Exception e) {
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

    @GetMapping(value = "/timeoff")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Map<String, List<TimeOff>>> getTimeOffs(Principal user) {
        HashMap<String, List<TimeOff>> timeOffs = new HashMap<String, List<TimeOff>>();

        Doctor doctor = doctorService.findOne(user.getName());

        if(doctor != null) {
            timeOffs.put("data", new ArrayList<TimeOff>(doctor.getCalendar()));
        }
        return ResponseEntity.ok(timeOffs);
    }

    @GetMapping(value = "/events")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Map<String, List<Event>>> getEvents(Principal user) {
        HashMap<String, List<Event>> events = new HashMap<String, List<Event>>();

        Doctor doctor = doctorService.findOne(user.getName());

        if(doctor != null) {
            events.put("events", Event.convertToEvents(
                    appointmentService.findDoctorsAppointments(doctor)));
        }
        return ResponseEntity.ok(events);
    }
}
