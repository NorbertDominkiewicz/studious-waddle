package com.norbdev.server.controller;

import com.norbdev.server.algorithm.Newton;
import com.norbdev.server.dto.ExampleRequest;
import com.norbdev.server.dto.HookeRequest;
import com.norbdev.server.dto.HookeResponse;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.service.ComputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compute")
@CrossOrigin(origins = "http://localhost:5173")
public class ComputeController {

    @Autowired
    private ComputeService computeService;

    @GetMapping("/newton")
    public ResponseEntity<ResultResponse> calculateNewton(@RequestBody ExampleRequest exampleRequest) {
        ResultResponse resultResponse = Newton.calculate(exampleRequest);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping("/hooke")
    public ResponseEntity<HookeResponse> computeHooke(@RequestBody HookeRequest request) {
        HookeResponse response = computeService.computeHooke(request);
        return ResponseEntity.ok(response);
    }
}
