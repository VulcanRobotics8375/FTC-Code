/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

//import android.test.FlakyTest;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Drivetrain {
    private DcMotor fl, fr, bl, br;
    private BNO055IMU imu;
    private BNO055IMU.Parameters parameters;
    private double position;
    private double movePower;
    private double turnPower;
    private double mPower;
    private double tPower;
    private double divisor;
    double targetAngle;
    private Orientation angles;
    public ElapsedTime Time = new ElapsedTime();
    private double sensorVal;
    private double integral = 0;
    private double derivative = 0;
    private double output = 0;
    private double previousError = 0;
    private double previousHeading = 0;
    private double integratedHeading = 0;
    private boolean ranFirstCycle = false;
//    ElapsedTime lastTime;

    public PID pid;

    public Drivetrain(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, BNO055IMU IMU) {
        pid = new PID(imu);
        fl = frontLeft;
        fr = frontRight;
        bl = backLeft;
        br = backRight;
        imu = IMU;

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setupIMU() {
        parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        if(imu.initialize(parameters)) {
            while (!imu.isGyroCalibrated()) {}
        } else {
            imu.initialize(parameters);

        }
    }

    /**
     *
     * TeleOp Functions
     *
     */

    //graph explaining how mecanum works - https://www.desmos.com/calculator/nhqgggjnj6
    public void mecanumDrive(double forward, double turn, double strafe, double multiplier) {
        double vd = Math.hypot(forward, strafe);
        double theta = Math.atan2(forward, strafe) - (Math.PI / 4);

        if(forward == 0 && strafe == 0 && turn == 0) {
            Time.reset();
        }

//        double accLim = (Time.time()/1.07)*((0.62*Math.pow(Time.time(), 2))+0.45);

//        if(forward < 0 || turn < 0 || strafe < 0) {
//
//        }
//
//        if(turn == 0) {
//            //targetAngle = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//            pid.run(0.01, 0.001, 0.01, 10, targetAngle);
//            turn = pid.getOutput();
//        } else {
//            targetAngle = pid.getIntegratedHeading();
//        }

        double[] v = {
                vd * Math.sin(theta) - turn,
                vd * Math.cos(theta) + turn,
                vd * Math.cos(theta) - turn,
                vd * Math.sin(theta) + turn
        };

        double[] motorOut = {
                multiplier * (v[0] / 1.07) * ((0.62 * Math.pow(v[0], 2)) + 0.45),
                multiplier * (v[1] / 1.07) * ((0.62 * Math.pow(v[1], 2)) + 0.45),
                multiplier * (v[2] / 1.07) * ((0.62 * Math.pow(v[2], 2)) + 0.45),
                multiplier * (v[3] / 1.07) * ((0.62 * Math.pow(v[3], 2)) + 0.45)
        };



        fr.setPower(motorOut[0]);
        fl.setPower(motorOut[1]);
        br.setPower(motorOut[2]);
        bl.setPower(motorOut[3]);
    }

    public void tankDrive(float leftPower, float rightPower, double acceleration) {
        //double error = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - targetAngle;
        divisor = (Math.pow(Math.E, (acceleration/0.519298))-0.947908)/5.89074;
        // modifies the controller input for a more natural feel
        // graph for acceleration curve - https://bit.ly/2M4Iybm
        movePower = (leftPower/1.07)*((0.62*Math.pow(leftPower, 2))+0.45);
        turnPower = (rightPower/1.07)*((0.62*Math.pow(rightPower, 2))+0.45);
        if(movePower == 0 && turnPower == 0){
            Time.reset();
        }
        mPower = movePower;
        tPower = turnPower;

        //same acceleration curve, but based on time instead of controller input.
        // limits the speed at which the robot accelerates
        double accLim = (Time.time()/1.07)*((0.62*Math.pow(Time.time(), 2))+0.45);

        //logic gates to determine when to use time-based or controller-based power
        if(accLim < Math.abs(movePower) && accLim < Math.abs(turnPower)){
            movePower = accLim;
            turnPower = accLim;
        }
        else if(accLim < Math.abs(movePower)){
            movePower = accLim;
        } else if(accLim < Math.abs(turnPower)){
            turnPower = accLim;
        }

        //makes sure the motors are going the correct way
        if(mPower < 0 || tPower < 0){
            if(movePower == accLim){
                movePower = -movePower;
            }
            if(turnPower == accLim){
                turnPower = -turnPower;
            }
        }
//        if(tPower != 0){
//            targetAngle = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//        }
//        if (turnPower == 0){
//            PID(0.001, 0, 0, 10, targetAngle);
//            turnPower = output;
//        }
        //set powers
        fl.setPower(turnPower + movePower);
        bl.setPower(turnPower + movePower);
        fr.setPower(turnPower - movePower);
        br.setPower(turnPower - movePower);
    }


    /**
     *
     * Autonomous Functions
     *
     */

    public void MoveIn(double inches, double speed) {

        resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);

        do {


        } while(getDrivetrainPos() < inches);

    }

    public void strafeIn(double Kp, double Ki, double Kd, double inches, double speed) {


    }


    /**
     *
     * Miscellanious Functions
     *
     */

    public void setPowers(double forward, double turn) {
        fl.setPower(-forward - turn);
        fr.setPower(-forward + turn);
        bl.setPower(-forward - turn);
        br.setPower(-forward + turn);
    }

    public double getOutput() {
        return output;
    }

    //in inches
    private double getDrivetrainPos() {


        //         Absolute Value of motor ticks for strafing compatibility                                                                                 divide by 4 to average out encoders
        position = (Math.abs(fl.getCurrentPosition()) + (Math.abs(fr.getCurrentPosition())) + (Math.abs(bl.getCurrentPosition())) + (Math.abs(br.getCurrentPosition()))) / 4.0;


        return position;
    }

    public void resetEncoders(DcMotor.RunMode runMode) {

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(runMode);
        fr.setMode(runMode);
        bl.setMode(runMode);
        br.setMode(runMode);

    }

    public void setTargetPos(int pos) {
        fl.setTargetPosition(pos);
        fr.setTargetPosition(pos);
        bl.setTargetPosition(pos);
        br.setTargetPosition(pos);
    }

        public void stop() {
    }
}

