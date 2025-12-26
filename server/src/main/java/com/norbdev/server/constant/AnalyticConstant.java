package com.norbdev.server.constant;

import java.util.function.BiFunction;

public class AnalyticConstant {
    private AnalyticConstant() {}

    public static BiFunction<Double, Double, Double> f() {
        return (x, y) -> y * y * y + x * x - 9 * x * y - 3 * x + 2;
    }

    public static BiFunction<Double, Double, Double> dfx() {
        return (x, y) -> 2 * x - 9 * y - 3;
    }

    public static BiFunction<Double, Double, Double> dfy() {
        return (x, y) -> 3 * y * y - 9 * x;
    }

    public static BiFunction<Double, Double, Double> dxy() {
        return (x, y) -> -9.0;
    }

    public static BiFunction<Double, Double, Double> d2fx() {
        return (x, y) -> 2.0;
    }

    public static BiFunction<Double, Double, Double> d2fy() {
        return (x, y) -> 6 * y;
    }
}
