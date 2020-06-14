package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.*;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.service.AppointmentService;
import com.tim18.bolnicar.service.ClinicAdminService;
import com.tim18.bolnicar.service.ClinicService;
import com.tim18.bolnicar.service.DoctorService;
import com.tim18.bolnicar.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping(path = "/")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<RoomDTO>> getRooms(Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        List<RoomDTO> roomDTOList = new ArrayList<>();
        if(clinicAdmin != null && clinicAdmin.getClinic() != null)
            for(Room room : clinicAdmin.getClinic().getRooms()) {
                roomDTOList.add(new RoomDTO(room));
            }

        return new ResponseEntity<>(roomDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/availableExamination/{dateTime}/{duration}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<RoomDTO>> getAvailableExaminationRooms(@PathVariable String dateTime, @PathVariable int duration, Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        List<RoomDTO> roomDTOList = new ArrayList<RoomDTO>();
        if(clinicAdmin != null) {
            try {
                roomDTOList = roomService
                        .freeRoomsByDateInterval(clinicAdmin.getClinic(), dateTime, duration);
                return new ResponseEntity<>(roomDTOList, HttpStatus.OK);
            } catch (ParseException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/availableRooms/{appointmentId}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response> getAvailableRooms(@PathVariable int appointmentId, Principal user) {
        Response resp = new Response();
        resp.setStatus("error");
        ClinicAdmin clinicAdmin = this.clinicAdminService.findSingle(user.getName());
        Appointment appointment = this.appointmentService.findById(appointmentId);

        if(clinicAdmin != null && appointment != null) {
            Doctor doctor = appointment.getDoctor();
            if(doctor != null) {
                Date current = appointment.getDatetime();
                List<RoomDTO> freeRooms = new ArrayList<RoomDTO>();
                while(true) {
                    freeRooms = this.roomService
                            .findRoomsFreeForDay(clinicAdmin.getClinic(), this.doctorService
                            .getFreeDayTime(current, doctor.getId(), appointment.getDuration()));

                    if(freeRooms.size() > 0)
                        break;

                    Calendar c = Calendar.getInstance();
                    c.setTime(current);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    current = c.getTime();
                }
                resp.setData(freeRooms.toArray());
                resp.setStatus("ok");
            }
            else {
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(resp);
    }

    @PostMapping(
            path = "/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addRoom(@RequestBody Room newRoom, Principal user) {
        HashMap<String, String> response = new HashMap<>();
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        if(clinicAdmin != null && clinicAdmin.getClinic() != null) {
            newRoom.setClinic(clinicAdmin.getClinic());
            try {
                roomService.save(newRoom);
                response.put("message", "true");
            } catch (Exception ex) {
                response.put("message", "false");
                System.out.println(ex);
            }
        }
        else {
            response.put("message", "false");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{roomNumber}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public  ResponseEntity<Void> deleteRoom(@PathVariable int roomNumber, Principal user) {
        Room room = roomService.findByRoomNumber(roomNumber);
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        if(room != null && clinicAdmin != null && clinicAdmin.getClinic() != null
                && clinicAdmin.getClinic().getRooms().contains(room)) {
            roomService.remove(room.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{roomNumber}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable int roomNumber, Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        Room room = roomService.findByRoomNumber(roomNumber);
        if(clinicAdmin.getClinic().getRooms().contains(room))
            return new ResponseEntity<>(new RoomDTO(room), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ResponseReport> updateRoom(@RequestBody Room roomUpdates, Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        ResponseReport report = new ResponseReport("error", "Forbidden action, contact admin.");
        Room room = this.roomService.findOne(roomUpdates.getId());
        if(room != null && clinicAdmin != null && clinicAdmin.getClinic() != null && clinicAdmin.getClinic().getRooms().contains(room)) {
            if(roomUpdates.getType() != room.getType()) { // ako je doslo do promene tipa sobe, spreciti ukoliko ima appointmenta
                if(appointmentService.findRoomsAppointments(room).size() > 0) {
                    return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
                }
            }
            roomUpdates.setClinic(room.getClinic());
            if(this.roomService.save(roomUpdates) != null) {
                report.setStatus("ok");
                report.setMessage("Profile successfully updated.");
                return ResponseEntity.ok(report);
            }
            else {
                report.setMessage(null);

                return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            report.setMessage(null);

            return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping(value = "/events/{roomNumber}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Response> getEvents(@PathVariable Integer roomNumber, Principal user) {
        ClinicAdmin clinicAdmin = clinicAdminService.findSingle(user.getName());
        List<Event> events = null;
        Room room = roomService.findByRoomNumber(roomNumber);
        Response resp = new Response();
        resp.setStatus("error");

        if(clinicAdmin != null && clinicAdmin.getClinic() != null && room != null) {
            if(clinicAdmin.getClinic().getRooms().contains(room)) {
                events = Event.convertToEvents(appointmentService.findRoomsAppointments(room));

                resp.setData(events.toArray());
                resp.setStatus("ok");
                return ResponseEntity.ok(resp);
            }
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
