package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.dto.UserDTO;
import com.tim18.bolnicar.dto.UserTokenState;
import com.tim18.bolnicar.model.User;
import com.tim18.bolnicar.security.TokenUtils;
import com.tim18.bolnicar.security.auth.JwtAuthenticationRequest;
import com.tim18.bolnicar.service.PatientService;
import com.tim18.bolnicar.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PatientService patientService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {
        System.out.printf("Username %s\n", authenticationRequest.getUsername());
        System.out.printf("Password %s\n", authenticationRequest.getPassword());

        //
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<ResponseReport> register(@RequestBody UserDTO user) {
        //TODO: exception
        boolean flag = patientService.registerPatient(user);

        if (flag) {
            return new ResponseEntity<>(
                    new ResponseReport("ok", "Your registration request is successfully created."),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new ResponseReport("error", "Invalid input."),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/hoho")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Poruka");
    }
}
