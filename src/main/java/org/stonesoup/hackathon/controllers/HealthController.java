package org.stonesoup.hackathon.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stonesoup.hackathon.dtos.ApiResponse;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String,String>> check() {
        return ResponseEntity.ok(
                Map.of("status","ok")
        );
    }
}
