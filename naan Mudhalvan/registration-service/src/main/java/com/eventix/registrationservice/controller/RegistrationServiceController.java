package com.eventix.registrationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RegistrationServiceController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("registration-service OK");
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from registration-service");
    }
}
