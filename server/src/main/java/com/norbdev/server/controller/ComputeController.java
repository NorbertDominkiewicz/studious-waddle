package com.norbdev.server.controller;

import com.norbdev.server.algorithm.Newton;
import com.norbdev.server.algorithm.SteepestDescent;
import com.norbdev.server.dto.*;
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

    @PostMapping("/steepest-descent")
    public ResponseEntity<ResultResponse> computeSteepestDescent(
            @RequestBody ExampleRequest request
    ) {
        ResultResponse response = SteepestDescent.calculate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/newton")
    public ResponseEntity<ResultResponse> computeNewton(
            @RequestBody ExampleRequest request
    ) {
        ResultResponse response = Newton.calculate(request);
        return ResponseEntity.ok(response);
    }
}

