package com.norbdev.server.dto;

public class GaussSeidelRequest {
    private String equation;
    private double x;
    private double y;
    private double epsilon;

    public String getEquation() { return equation; }
    public void setEquation(String equation) { this.equation = equation; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getEpsilon() { return epsilon; }
    public void setEpsilon(double epsilon) { this.epsilon = epsilon; }
}
