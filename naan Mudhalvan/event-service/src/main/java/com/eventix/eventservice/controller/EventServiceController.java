package com.eventix.eventservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class EventServiceController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("event-service OK");
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from event-service");
    }
}
