package com.norbdev.server.util;

import com.norbdev.server.constant.AnalyticConstant;
import com.norbdev.server.exception.ModeException;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Map;
import java.util.function.BiFunction;

public class Tool {
    private Tool() {}

    public static double [] createGradient(double x, double y, String mode) {
        if (mode.equals("analytic")) {
            return new double[] {
                    AnalyticConstant.dfx().apply(x, y),
                    AnalyticConstant.dfy().apply(x, y)
            };
        } else {
            throw new ModeException("Unreachable mode: " + mode);
        }
    }

    public static double [] createGradient(BiFunction<Double, Double, Double> function, double x, double y, String mode) {
        if (mode.equals("numeric")) {
            return new double[] {
                    PartialDerivative.calculateX(function, x, y, 1),
                    PartialDerivative.calculateY(function, x, y, 1),
            };
        } else {
            throw new ModeException("Unreachable mode: " + mode);
        }
    }

    public static double[][] createInverseHessian(double x, double y, String mode) {
        if (mode.equals("analytic")) {
            double a = AnalyticConstant.d2fx().apply(x, y);
            double b = AnalyticConstant.dxy().apply(x, y);
            double c = AnalyticConstant.dxy().apply(x, y);
            double d = AnalyticConstant.d2fy().apply(x, y);

            double det = a * d - b * c;
            double coefficient = 1 / det;

            return new double[][] {
                    {coefficient * d, -coefficient * b},
                    {-coefficient * c, coefficient * a}
            };
        } else {
            throw new ModeException("Unreachable mode: " + mode);
        }
    }

    public static BiFunction<Double, Double, Double> buildFunction(String equation) {
        try {
            return (x, y) -> {
                Expression expression = new ExpressionBuilder(equation)
                        .variables("x", "y")
                        .build()
                        .setVariables(Map.of("x", x, "y", y));
                return expression.evaluate();
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
