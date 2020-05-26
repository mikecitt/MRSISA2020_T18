package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @GetMapping(path = "/")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(roomService.findAll(), HttpStatus.OK);
    }

    @PostMapping(
            path = "/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Map<String, String>> addRoom(@RequestBody Room newRoom) {
        HashMap<String, String> response = new HashMap<>();

        try {
            roomService.save(newRoom);
            response.put("message", "true");
        } catch (Exception ex) {
            response.put("message", "false");
            System.out.println(ex);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{roomNumber}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public  ResponseEntity<Void> deleteRoom(@PathVariable int roomNumber) {
        Room room = roomService.findByRoomNumber(roomNumber);

        if(room != null) {
            roomService.remove(room.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{roomNumber}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Room> getRoom(@PathVariable int roomNumber) {
        return new ResponseEntity<>(roomService.findByRoomNumber(roomNumber), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ResponseReport> updateRoom(@RequestBody Room roomUpdates) {
        ResponseReport report = new ResponseReport("error", "Forbidden action, contact admin.");

        if(this.roomService.save(roomUpdates) != null) {
            report.setStatus("ok");
            report.setMessage("Profile successfully updated.");
            return ResponseEntity.ok(report);
        }

        report.setMessage(null);

        return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
    }
}
