package com.norbdev.server.service;

import com.norbdev.server.algorithm.GaussSeidel;
import com.norbdev.server.algorithm.Hooke;
import com.norbdev.server.algorithm.Newton;
import com.norbdev.server.dto.*;
import com.norbdev.server.model.OptimizationType;
import com.norbdev.server.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class ComputeService {

    @Autowired
    private Hooke hooke;

    @Autowired
    private GaussSeidel gaussSeidel;

    public HookeResponse computeHooke(HookeRequest request) {

        double[] start = {
                request.getX(),
                request.getY()
        };

        OptimizationType type = OptimizationType.valueOf(request.getType().toUpperCase());

        BiFunction<Double, Double, Double> function = Tool.buildFunction(request.getEquation());

        double beta = 0.5;

        return hooke.compute(
                start,
                request.getStep(),
                beta,
                request.getEpsilon(),
                type,
                function
        );
    }

    public GaussSeidelResponse computeGaussSeidel(GaussSeidelRequest request) {
        return gaussSeidel.compute(
                request.getEquation(),
                request.getX(),
                request.getY(),
                request.getEpsilon()
        );
    }
}

