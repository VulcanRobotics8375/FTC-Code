/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class PID {
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

    public PID(BNO055IMU IMU) { imu = IMU; }

//    public void runOpMode() {
//        telemetry.addData("PID output", output);
//    }

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
    public double run(double Kp, double Ki, double Kd, long iterationTime, double heading) {

        double sensorVal = getIntegratedHeading() + startHeading;

        double error = sensorVal - heading;

        if(error < 5 && error > -5) {
            integral = 0;
//            output = 0;
        } else {
            integral += ((error + previousError) / 2.0) * ((timer.time(TimeUnit.MILLISECONDS) - lastTime) / 1000.0);
            derivative = (error - previousError);
            output = Range.clip(Kp * error + Ki * integral + Kd * derivative, -1.0, 1.0);
            previousError = error;
        }
        lastTime = timer.time(TimeUnit.MILLISECONDS);
        return output;
    }
    //default params
    public double run(double Kp, double Ki, double Kd, long iterationTime) {
        return run(Kp, Ki, Kd, iterationTime, 0);
    }

    public double run(double Kp, double Ki, double Kd) {
        return run(Kp, Ki, Kd, 10, 0);
    }

}
