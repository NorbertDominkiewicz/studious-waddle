package com.norbdev.server.controller;

import com.norbdev.server.dto.GaussSeidelRequest;
import com.norbdev.server.dto.GaussSeidelResponse;
import com.norbdev.server.dto.HookeRequest;
import com.norbdev.server.dto.HookeResponse;
import com.norbdev.server.service.ComputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ComputeController {

    @Autowired
    private ComputeService computeService;

    @PostMapping("/hooke")
    public ResponseEntity<HookeResponse> computeHooke(
            @RequestBody HookeRequest request
    ) {
        HookeResponse response = computeService.computeHooke(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/gauss-seidel")
    public ResponseEntity<GaussSeidelResponse> computeGaussSeidel(
            @RequestBody GaussSeidelRequest request
    ) {
        return ResponseEntity.ok(
                computeService.computeGaussSeidel(request)
        );
    }
}

