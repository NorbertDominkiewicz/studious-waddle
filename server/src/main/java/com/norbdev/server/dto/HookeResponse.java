package com.norbdev.server.dto;

import java.util.List;

public class HookeResponse {
    public double x;
    public double y;
    public int iterations;
    public List<double[]> path;

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

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public List<double[]> getPath() {
        return path;
    }

    public void setPath(List<double[]> path) {
        this.path = path;
    }
}
