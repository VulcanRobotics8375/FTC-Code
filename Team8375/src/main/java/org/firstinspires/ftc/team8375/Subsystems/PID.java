/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Disabled
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
    private double startHeading;

    public PID(BNO055IMU IMU) { imu = IMU; }

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

    public void initIMU(BNO055IMU.SensorMode mode, BNO055IMU.AngleUnit angleUnit, BNO055IMU.AccelUnit accelUnit, boolean loggingEnabled) {
        parameters = new BNO055IMU.Parameters();
        parameters.mode = mode;
        parameters.angleUnit = angleUnit;
        parameters.accelUnit = accelUnit;
        parameters.loggingEnabled = loggingEnabled;
        if(imu.initialize(parameters)) {
            while (!imu.isGyroCalibrated()) {}
        } else {
            imu.initialize(parameters);

        }
    }

    public void initIMU() {
        initIMU(
            BNO055IMU.SensorMode.IMU,
            BNO055IMU.AngleUnit.DEGREES,
            BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC,
            false
        );
    }

    public void init() {
        startHeading = getIntegratedHeading();

    }

    //pid calculations
    public double run(double Kp, double Ki, double Kd, float iterationTime, double heading) {

        double sensorVal = getIntegratedHeading() + startHeading;

        double error = sensorVal - heading;

        if(error < 5 && error > -5) {
            integral = 0;
            output = 0;
        } else {
            integral += ((error + previousError) / 2.0) * (iterationTime / 100.0);
            derivative = (error - previousError);
            output = Kp * error + Ki * integral + Kd * derivative;
            previousError = error;
        }
        return output;
    }
    //default params
    public void run(double Kp, double Ki, double Kd, float iterationTime) {
        run(Kp, Ki, Kd, iterationTime, 0);
    }

    public void run(double Kp, double Ki, double Kd) {
        run(Kp, Ki, Kd, 10, 0);
    }

}
