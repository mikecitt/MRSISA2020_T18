package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    //TODO: enable just for workers and patient?
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Principal user) {
        return ResponseEntity.ok(this.userService.getProfile(user.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseReport> updateProfile(@RequestBody UserDTO profileUpdates, Principal user) {
        ResponseReport report = new ResponseReport("error", "Forbidden action, contact admin.");

        if (!profileUpdates.getEmailAddress().equals(user.getName()))
            return new ResponseEntity(report, HttpStatus.BAD_REQUEST);

        if (this.userService.updateProfile(user.getName(), profileUpdates)) {
            report.setStatus("ok");
            report.setMessage("Profile successfully updated.");
            return ResponseEntity.ok(report);
        }

        report.setMessage(null);

        return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
    }
}
