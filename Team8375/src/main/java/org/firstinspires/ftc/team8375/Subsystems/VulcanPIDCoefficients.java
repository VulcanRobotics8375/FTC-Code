/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

enum PIDCoefficient {
    Kp,
    Ki,
    Kd
}

public class VulcanPIDCoefficients {
    private double Kp, Ki, Kd;
    private double threshold;
    public VulcanPIDCoefficients(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.threshold = 10;
    }

    public VulcanPIDCoefficients(double Kp, double Ki, double Kd, double threshold) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public Double getCoefficient(PIDCoefficient k) {
        if(k == PIDCoefficient.Kp) {
            return Kp;
        } else if(k == PIDCoefficient.Ki) {
            return Ki;
        } else if(k == PIDCoefficient.Kd) {
            return Kd;
        } else {
            return null;
        }
    }

    public void setCoefficients(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }
}
