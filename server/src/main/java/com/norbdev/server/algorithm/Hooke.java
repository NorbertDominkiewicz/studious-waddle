package com.norbdev.server.algorithm;

import com.norbdev.server.constant.AnalyticConstant;
import com.norbdev.server.dto.HookeRequest;
import com.norbdev.server.dto.HookeResponse;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.model.OptimizationType;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class Hooke {

    private boolean isBetter(double fNew, double fOld, OptimizationType type) {
        return type == OptimizationType.MIN
                ? fNew < fOld
                : fNew > fOld;
    }

    private double[] exploratoryMove(double[] x, double step, OptimizationType type) {
        double[] current = x.clone();
        double f0 = AnalyticConstant.f().apply(current[0], current[1]);

        int n = current.length;

        for (int j = 0; j < n; j++) {
            current[j] += step;
            double f = AnalyticConstant.f().apply(current[0], current[1]);

            if (isBetter(f, f0, type)) {
                f0 = f;
            } else {
                current[j] -= 2 * step;
                f = AnalyticConstant.f().apply(current[0], current[1]);

                if (isBetter(f, f0, type)) {
                    f0 = f;
                } else {
                    current[j] += step;
                }
            }
        }
        return current;
    }


    public HookeResponse compute(
            double[] start,
            double step,
            double beta,
            double epsilon,
            OptimizationType type
    ) {
        HookeResponse hookeResponse = new HookeResponse();
        double[] xB = start.clone();
        double[] x0 = start.clone();

        while (step > epsilon) {
            double[] xNew = exploratoryMove(x0, step, type);

            double fNew = AnalyticConstant.f().apply(xNew[0], xNew[1]);
            double fB = AnalyticConstant.f().apply(xB[0], xB[1]);

            if (isBetter(fNew, fB, type)) {
                double[] xB0 = xB.clone();
                xB = xNew.clone();

                for (int i = 0; i < x0.length; i++) {
                    x0[i] = 2 * xB[i] - xB0[i];
                }
            } else {
                step *= beta;
                x0 = xB.clone();
            }
        }
//        return xB;
        return hookeResponse;
    }
}
