package com.norbdev.server.algorithm;

import com.norbdev.server.dto.ExampleRequest;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.exception.ModeException;
import com.norbdev.server.util.Tool;
import org.springframework.stereotype.Component;

@Component
public class Newton {

    public static ResultResponse calculate(ExampleRequest example) {
        double [] x0 = new double[]{example.getA(), example.getB()};
        double [] x1 = new double[2];
        if (example.getMode().equals("analytic")) {
            return runAnalytic(x0, x1, example.getEpsilon());
        }
        throw new ModeException("Unreachable mode: " + example.getMode());
    }

    private static ResultResponse runAnalytic(double [] x0, double [] x1, double epsilon) {
        int iterations = 0;
        while (true) {
            double[] gradient = Tool.createGradient(x0[0], x0[1]);
            double[][] inverseHessian = Tool.createInverseHessian(x0[0], x0[1]);

            if (Math.sqrt(Math.pow(gradient[0], 2) + Math.pow(gradient[1], 2)) < epsilon) {
                return new ResultResponse(new double[]{x0[0], x0[1]}, epsilon, iterations);
            }

            x1[0] = x0[0] - (inverseHessian[0][0] * gradient[0] + inverseHessian[0][1] * gradient[1]);
            x1[1] = x0[1] - (inverseHessian[1][0] * gradient[0] + inverseHessian[1][1] * gradient[1]);

            if ((Math.abs(x1[0] - x0[0]) <= epsilon) && Math.abs(x1[1] - x0[1]) <= epsilon) {
                x0 = x1;
                return new ResultResponse(new double[]{x0[0], x0[1]}, epsilon, iterations);
            }

            x0[0] = x1[0];
            x0[1] = x1[1];
            System.out.println("[Newton] x = (" + x0[0] + ", " + x0[1] + ")");
            iterations++;
        }
    }

    private static ResultResponse runNumeric() {
        return null;
    }
}
