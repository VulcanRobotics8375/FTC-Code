/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class VulcanPID {
    private BNO055IMU imu;
    private BNO055IMU.Parameters parameters;
    private Orientation angles;
    private double integral = 0;
    private double derivative = 0;
    private double output = 0;
    private double previousError = 0;
    private double previousHeading = 0;
    private double integratedHeading = 0;
    private double startHeading = 0;
    private double lastTime;
    private ElapsedTime timer = new ElapsedTime();

    public VulcanPID(BNO055IMU IMU) {
        imu = IMU;
    }

//    public void runOpMode() {
//        telemetry.addData("VulcanPID output", output);
//    }

    public void init() {
        integral = 0;
        derivative = 0;
        output = 0;
        previousError = 0;
        previousHeading = 0;
        lastTime = 0;
        timer.reset();
    }

    public double getIntegratedHeading() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        imu.getPosition();
        double currentHeading = AngleUnit.DEGREES.normalize(angles.firstAngle);
        double deltaHeading = currentHeading - previousHeading;

        if (deltaHeading < -180) {
            deltaHeading += 360;
        } else if (deltaHeading >= 180) {
            deltaHeading -= 360;
        }

        integratedHeading += deltaHeading;
        previousHeading = currentHeading;

        return integratedHeading;
    }

    public double getOutput() {
        return output;
    }

    public BNO055IMU.CalibrationStatus getCalibrationStatus() {
        return imu.getCalibrationStatus();
    }

    public double initHeading() {
        startHeading = getIntegratedHeading();
        return startHeading;

    }

    //pid calculations
    public void run(double heading, VulcanPIDCoefficients vals, double iterationTime) {

        double sensorVal = getIntegratedHeading() + initHeading();

        lastTime = timer.time(TimeUnit.MILLISECONDS);
        double error = sensorVal - heading;
        integral += Range.clip(((error + previousError) / 2.0) * (iterationTime), -100, 100);
        derivative = (error - previousError);
        if(Math.abs(error) < 10) {
            output = Range.clip(
                vals.getCoefficient(PIDCoefficient.Kp) * error +
                        vals.getCoefficient(PIDCoefficient.Ki) * integral +
                        vals.getCoefficient(PIDCoefficient.Kd) * derivative,
                    -40, 40);
        } else {
            output = Range.clip(
                    vals.getCoefficient(PIDCoefficient.Kp) * error +
                            vals.getCoefficient(PIDCoefficient.Ki) * integral +
                            vals.getCoefficient(PIDCoefficient.Kd) * derivative,
                    -100, 100);
        }
        previousError = error;
    }

    public void run(double inches, double pos, VulcanPIDCoefficients vals) {

        double error = pos - inches;
        integral += Range.clip(((error + previousError) / 2.0) * ((timer.time(TimeUnit.MILLISECONDS) - lastTime) / 1000.0), -100, 100);
        derivative = (error - previousError);
        if(Math.abs(error) < 10) {
            output = Range.clip(
                    vals.getCoefficient(PIDCoefficient.Kp) * error +
                            vals.getCoefficient(PIDCoefficient.Ki) * integral +
                            vals.getCoefficient(PIDCoefficient.Kd) * derivative,
                    -40, 40);
        } else {
            output = Range.clip(
                    vals.getCoefficient(PIDCoefficient.Kp) * error +
                            vals.getCoefficient(PIDCoefficient.Ki) * integral +
                            vals.getCoefficient(PIDCoefficient.Kd) * derivative,
                    -100, 100);
        }
        previousError = error;
        lastTime = timer.time(TimeUnit.MILLISECONDS);

    }

}