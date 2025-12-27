package com.norbdev.server.controller;

import com.norbdev.server.algorithm.Newton;
import com.norbdev.server.dto.ExampleRequest;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.service.ComputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculate")
public class ComputeController {

    @Autowired
    private ComputeService computeService;

    @GetMapping("/newton")
    public ResponseEntity<ResultResponse> calculateNewton(@RequestBody ExampleRequest exampleRequest) {
        ResultResponse resultResponse = Newton.calculate(exampleRequest);
        return ResponseEntity.ok(resultResponse);
    }
}
