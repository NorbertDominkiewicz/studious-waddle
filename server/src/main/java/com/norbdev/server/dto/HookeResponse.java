package com.norbdev.server.dto;

public class HookeResponse {
    double x;
    double y;
    double iterations;
    double[] xs;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getIterations() {
        return iterations;
    }

    public void setIterations(double iterations) {
        this.iterations = iterations;
    }

    public double[] getXs() {
        return xs;
    }

    public void setXs(double[] xs) {
        this.xs = xs;
    }
}
