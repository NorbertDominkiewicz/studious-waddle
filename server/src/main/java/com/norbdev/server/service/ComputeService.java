package com.norbdev.server.service;

import com.norbdev.server.algorithm.Hooke;
import com.norbdev.server.algorithm.Newton;
import com.norbdev.server.dto.HookeRequest;
import com.norbdev.server.dto.HookeResponse;
import com.norbdev.server.dto.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputeService {

    @Autowired
    private Hooke hooke;

    public HookeResponse computeHooke(HookeRequest request) {
        HookeResponse response = new HookeResponse();
//        double result = hooke.compute();
        return response;
    }
}
