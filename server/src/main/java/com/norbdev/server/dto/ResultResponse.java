package com.norbdev.server.dto;

public class ResultResponse {
    double[] result;
    double epsilon;
    int iterations;

    public ResultResponse(double[] result, double epsilon, int iterations) {
        this.result = result;
        this.epsilon = epsilon;
        this.iterations = iterations;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double[] getResult() {
        return result;
    }

    public void setResult(double[] result) {
        this.result = result;
    }
}
