package com.norbdev.server.algorithm;

import com.norbdev.server.dto.ExampleRequest;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.exception.ModeException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SteepestDescent {

    public static ResultResponse calculate(ExampleRequest example) {
        double[] x0 = new double[]{example.getA(), example.getB()};

        if (example.getMode().equals("analytic")) {
            return runAnalytic(x0, example.getEpsilon());
        } else if (example.getMode().equals("numeric")) {
            return runNumeric(x0, example.getEpsilon());
        }

        throw new ModeException("Unreachable mode: " + example.getMode());
    }

    private static double f(double x, double y) {
        return Math.pow(y, 3) + Math.pow(x, 2) - 9 * x * y - 3 * x + 2;
    }

    private static double dfx(double x, double y) {
        return 2 * x - 9 * y - 3;
    }

    private static double dfy(double x, double y) {
        return 3 * y * y - 9 * x;
    }

    private static double d2fx(double x, double y) {
        return 2;
    }

    private static double dxy(double x, double y) {
        return -9;
    }

    private static double d2fy(double x, double y) {
        return 6 * y;
    }

    private static double[] gradientAnalytic(double x, double y) {
        return new double[]{
                dfx(x, y),
                dfy(x, y)
        };
    }

    private static double[] gradientNumeric(double x, double y) {
        double h = 1e-8;

        double dfx_num = (f(x + h, y) - f(x - h, y)) / (2 * h);

        double dfy_num = (f(x, y + h) - f(x, y - h)) / (2 * h);

        return new double[]{dfx_num, dfy_num};
    }

    private static double[] hessianAnalytic(double x, double y) {
        return new double[]{
                d2fx(x, y),
                dxy(x, y),
                d2fy(x, y)
        };
    }

    private static double[] hessianNumeric(double x, double y) {
        double h = 1e-5;

        double d2fx_num = (f(x + h, y) - 2 * f(x, y) + f(x - h, y)) / (h * h);

        double dxy_num = (f(x + h, y + h) - f(x + h, y - h) - f(x - h, y + h) + f(x - h, y - h)) / (4 * h * h);

        double d2fy_num = (f(x, y + h) - 2 * f(x, y) + f(x, y - h)) / (h * h);

        return new double[]{d2fx_num, dxy_num, d2fy_num};
    }

    private static double calculateAlpha(double[] gradient, double[] hessian) {
        double a = gradient[0];
        double b = gradient[1];
        double c = hessian[0];
        double d = hessian[1];
        double fh = hessian[2];

        double numerator = a * a + b * b;

        double denominator = (a * c + b * d) * a + (a * d + b * fh) * b;

        return numerator / denominator;
    }

    private static boolean shouldStop(double[] xOld, double[] xNew, double[] gradient, double epsilon) {
        double gradNorm = Math.sqrt(gradient[0] * gradient[0] + gradient[1] * gradient[1]);
        if (gradNorm <= epsilon) {
            return true;
        }

        if (Math.abs(xNew[0] - xOld[0]) <= epsilon && Math.abs(xNew[1] - xOld[1]) <= epsilon) {
            return true;
        }

        return false;
    }

    private static ResultResponse runAnalytic(double[] x0, double epsilon) {
        int iterations = 0;
        double[] xCurrent = new double[]{x0[0], x0[1]};
        ResultResponse response = new ResultResponse();
        List<double[]> path = new ArrayList<>();

        path.add(new double[]{
                xCurrent[0],
                xCurrent[1],
                f(xCurrent[0], xCurrent[1])
        });

        while (true) {
            double[] gradient = gradientAnalytic(xCurrent[0], xCurrent[1]);
            double[] hessian = hessianAnalytic(xCurrent[0], xCurrent[1]);

            double alpha = calculateAlpha(gradient, hessian);

            double[] xNew = new double[]{
                    xCurrent[0] - alpha * gradient[0],
                    xCurrent[1] - alpha * gradient[1]
            };

            path.add(new double[]{
                    xNew[0],
                    xNew[1],
                    f(xNew[0], xNew[1])
            });

            if (shouldStop(xCurrent, xNew, gradient, epsilon)) {
                xCurrent = xNew;
                break;
            }

            xCurrent = xNew;
            System.out.println("[SteepestDescent] x = (" + xCurrent[0] + ", " + xCurrent[1] + ")");
            iterations++;
        }

        response.setX(xCurrent[0]);
        response.setY(xCurrent[1]);
        response.setIterations(iterations);
        response.setPath(path);

        return response;
    }

    private static ResultResponse runNumeric(double[] x0, double epsilon) {
        int iterations = 0;
        double[] xCurrent = new double[]{x0[0], x0[1]};
        ResultResponse response = new ResultResponse();
        List<double[]> path = new ArrayList<>();

        path.add(new double[]{
                xCurrent[0],
                xCurrent[1],
                f(xCurrent[0], xCurrent[1])
        });

        while (true) {
            double[] gradient = gradientNumeric(xCurrent[0], xCurrent[1]);
            double[] hessian = hessianNumeric(xCurrent[0], xCurrent[1]);

            double alpha = calculateAlpha(gradient, hessian);

            double[] xNew = new double[]{
                    xCurrent[0] - alpha * gradient[0],
                    xCurrent[1] - alpha * gradient[1]
            };

            path.add(new double[]{
                    xNew[0],
                    xNew[1],
                    f(xNew[0], xNew[1])
            });

            if (shouldStop(xCurrent, xNew, gradient, epsilon)) {
                xCurrent = xNew;
                break;
            }

            xCurrent = xNew;
            System.out.println("[SteepestDescent] x = (" + xCurrent[0] + ", " + xCurrent[1] + ")");
            iterations++;
        }

        response.setX(xCurrent[0]);
        response.setY(xCurrent[1]);
        response.setIterations(iterations);
        response.setPath(path);

        return response;
    }
}