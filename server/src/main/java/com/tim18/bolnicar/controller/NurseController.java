package com.tim18.bolnicar.controller;

import com.tim18.bolnicar.dto.ResponseReport;
import com.tim18.bolnicar.model.MedicalWorker;
import com.tim18.bolnicar.model.Nurse;
import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private NurseService nurseService;
}
