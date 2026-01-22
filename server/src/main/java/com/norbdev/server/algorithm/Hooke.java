package com.norbdev.server.algorithm;

import com.norbdev.server.constant.AnalyticConstant;
import com.norbdev.server.dto.HookeRequest;
import com.norbdev.server.dto.HookeResponse;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.model.OptimizationType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class Hooke {

    private boolean isBetter(double fNew, double fOld, OptimizationType type) {
        return type == OptimizationType.MIN
                ? fNew < fOld
                : fNew > fOld;
    }

    private double[] exploratoryMove(
            double[] x,
            double step,
            OptimizationType type,
            BiFunction<Double, Double, Double> function
    ) {
        double[] current = x.clone();
        double f0 = function.apply(current[0], current[1]);

        int n = current.length;

        for (int j = 0; j < n; j++) {
            current[j] += step;
            double f = function.apply(current[0], current[1]);

            if (isBetter(f, f0, type)) {
                f0 = f;
            } else {
                current[j] -= 2 * step;
                f = function.apply(current[0], current[1]);

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
            OptimizationType type,
            BiFunction<Double, Double, Double> function
    ) {
        HookeResponse response = new HookeResponse();
        List<double[]> path = new ArrayList<>();

        double[] xB = start.clone();
        double[] x0 = start.clone();
        int iterations = 0;

        path.add(new double[]{
                xB[0],
                xB[1],
                function.apply(xB[0], xB[1])
        });

        while (step > epsilon) {
            iterations++;

            double[] xNew = exploratoryMove(x0, step, type, function);

            double fNew = function.apply(xNew[0], xNew[1]);
            double fB = function.apply(xB[0], xB[1]);

            if (isBetter(fNew, fB, type)) {
                double[] xB0 = xB.clone();
                xB = xNew.clone();

                path.add(new double[]{xB[0], xB[1], fNew});

                for (int i = 0; i < x0.length; i++) {
                    x0[i] = 2 * xB[i] - xB0[i];
                }
            } else {
                step *= beta;
                x0 = xB.clone();
            }
        }

        response.x = xB[0];
        response.y = xB[1];
        response.iterations = iterations;
        response.path = path;

        return response;
    }
}
