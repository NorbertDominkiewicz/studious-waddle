package com.norbdev.server.algorithm;

import com.norbdev.server.constant.AnalyticConstant;
import com.norbdev.server.dto.ExampleRequest;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.exception.ModeException;
import com.norbdev.server.util.PartialDerivative;
import com.norbdev.server.util.Tool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class Newton {

    public static ResultResponse calculate(ExampleRequest example) {
        double [] x0 = new double[]{example.getA(), example.getB()};
        double [] x1 = new double[2];

        if (example.getMode().equals("analytic")) {
            return runAnalytic(x0, x1, example.getEpsilon());
        } else if (example.getMode().equals("numeric")) {
            BiFunction<Double, Double, Double> function = Tool.buildFunction(example.getEquation());
            return runNumeric(x0, x1, example.getEpsilon(), function);
        }

        throw new ModeException("Unreachable mode: " + example.getMode());
    }

    private static ResultResponse runAnalytic(double [] x0, double [] x1, double epsilon) {
        int iterations = 0;
        ResultResponse response = new ResultResponse();
        List<double[]> path = new ArrayList<>();

        path.add(new double[]{
                x0[0],
                x0[1],
                AnalyticConstant.f().apply(x0[0], x0[1])
        });

        while (true) {
            double[] gradient = Tool.createGradient(x0[0], x0[1]);
            double[][] inverseHessian = Tool.createInverseHessian(x0[0], x0[1]);

            if (Math.sqrt(Math.pow(gradient[0], 2) + Math.pow(gradient[1], 2)) < epsilon) {
                break;
            }

            x1[0] = x0[0] - (inverseHessian[0][0] * gradient[0] + inverseHessian[0][1] * gradient[1]);
            x1[1] = x0[1] - (inverseHessian[1][0] * gradient[0] + inverseHessian[1][1] * gradient[1]);

            path.add(new double[]{
                    x1[0],
                    x1[1],
                    AnalyticConstant.f().apply(x1[0], x1[1])
            });

            if ((Math.abs(x1[0] - x0[0]) <= epsilon) && Math.abs(x1[1] - x0[1]) <= epsilon) {
                x0 = x1;
                break;
            }

            x0[0] = x1[0];
            x0[1] = x1[1];
            System.out.println("[Newton] x = (" + x0[0] + ", " + x0[1] + ")");
            iterations++;
        }

        response.setX(x0[0]);
        response.setY(x0[1]);
        response.setIterations(iterations);
        response.setPath(path);

        return response;
    }

    private static ResultResponse runNumeric(double [] x0, double [] x1, double epsilon, BiFunction<Double, Double, Double> function) {
        int iterations = 0;
        ResultResponse response = new ResultResponse();
        List<double[]> path = new ArrayList<>();

        path.add(new double[]{
                x0[0],
                x0[1],
                function.apply(x0[0], x0[1])
        });

        while (true) {
            double[] gradient = Tool.createGradient(function, x0[0], x0[1], "numeric");
            double[][] inverseHessian = createInverseHessianNumeric(function, x0[0], x0[1]);

            if (Math.sqrt(Math.pow(gradient[0], 2) + Math.pow(gradient[1], 2)) < epsilon) {
                break;
            }

            x1[0] = x0[0] - (inverseHessian[0][0] * gradient[0] + inverseHessian[0][1] * gradient[1]);
            x1[1] = x0[1] - (inverseHessian[1][0] * gradient[0] + inverseHessian[1][1] * gradient[1]);

            path.add(new double[]{
                    x1[0],
                    x1[1],
                    function.apply(x1[0], x1[1])
            });

            if ((Math.abs(x1[0] - x0[0]) <= epsilon) && Math.abs(x1[1] - x0[1]) <= epsilon) {
                x0 = x1;
                break;
            }

            x0[0] = x1[0];
            x0[1] = x1[1];
            System.out.println("[Newton Numeric] x = (" + x0[0] + ", " + x0[1] + ")");
            iterations++;
        }

        response.setX(x0[0]);
        response.setY(x0[1]);
        response.setIterations(iterations);
        response.setPath(path);

        return response;
    }

    private static double[][] createInverseHessianNumeric(BiFunction<Double, Double, Double> function, double x, double y) {
        double a = PartialDerivative.calculateX(function, x, y, 2);
        double b = PartialDerivative.calculateXY(function, x, y);
        double c = PartialDerivative.calculateXY(function, x, y);
        double d = PartialDerivative.calculateY(function, x, y, 2);

        double det = a * d - b * c;

        if (Math.abs(det) < 1e-10) {
            throw new ArithmeticException("Hessian is singular (determinant near zero)");
        }

        double coefficient = 1.0 / det;

        return new double[][] {
                {coefficient * d, -coefficient * b},
                {-coefficient * c, coefficient * a}
        };
    }
}