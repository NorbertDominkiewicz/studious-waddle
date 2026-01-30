    package com.norbdev.server.algorithm;

    import com.norbdev.server.dto.GaussSeidelResponse;
    import com.norbdev.server.util.PartialDerivative;
    import com.norbdev.server.util.Tool;
    import org.springframework.stereotype.Component;

    import java.util.function.BiFunction;

    @Component
    public class GaussSeidel {

        private double newtonForX(
                BiFunction<Double, Double, Double> f,
                double x0,
                double y
        ) {
            double x = x0;

            for (int i = 0; i < 50; i++) {
                double dfx = PartialDerivative.calculateX(f, x, y, 1);
                double d2fx = PartialDerivative.calculateX(f, x, y, 2);

                if (Math.abs(d2fx) < 1e-12) break;

                x = x - dfx / d2fx;
            }
            return x;
        }

        private double newtonForY(
                BiFunction<Double, Double, Double> f,
                double x,
                double y0
        ) {
            double y = y0;

            for (int i = 0; i < 50; i++) {
                double dfy = PartialDerivative.calculateY(f, x, y, 1);
                double d2fy = PartialDerivative.calculateY(f, x, y, 2);

                if (Math.abs(d2fy) < 1e-12) break;

                y = y - dfy / d2fy;
            }
            return y;
        }

        public GaussSeidelResponse compute(
                String equation,
                double x0,
                double y0,
                double epsilon
        ) {
            BiFunction<Double, Double, Double> f = Tool.buildFunction(equation);

            GaussSeidelResponse response = new GaussSeidelResponse();

            double x = x0;
            double y = y0;
            int iterations = 0;

            while (true) {
                iterations++;

                x = newtonForX(f, x, y);
                y = newtonForY(f, x, y);

                double gradX = PartialDerivative.calculateX(f, x, y, 1);
                double gradY = PartialDerivative.calculateY(f, x, y, 1);

                double norm = Math.sqrt(gradX * gradX + gradY * gradY);

                response.getPath().add(new double[]{
                        x,
                        y,
                        f.apply(x, y)
                });

                if (norm < epsilon || iterations > 100) {
                    break;
                }
            }

            response.setX(x);
            response.setY(y);
            response.setIterations(iterations);

            return response;
        }
    }
