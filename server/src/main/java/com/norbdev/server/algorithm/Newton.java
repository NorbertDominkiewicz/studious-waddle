package com.norbdev.server.algorithm;

import com.norbdev.server.dto.ExampleRequest;
import com.norbdev.server.dto.ResultResponse;
import com.norbdev.server.util.Tool;
import org.springframework.stereotype.Component;

@Component
public class Newton {
    private Newton(){}

    public static ResultResponse calculate(ExampleRequest example) {
        double [] x0 = new double[]{example.getA(), example.getB()};
        double [] x1 = new double[2];

        while (true) {
            double[] gradient = example.getMode().equals("analytic") ?
                    Tool.createGradient(example.getA(), example.getB(), example.getMode()) :
                    Tool.createGradient(Tool.buildFunction(example.getEquation()), example.getA(), example.getB(), example.getMode());
            double[][] inverseHessian = Tool.createInverseHessian(example.getA(), example.getB(), example.getMode());

            }
        }
    }
}
