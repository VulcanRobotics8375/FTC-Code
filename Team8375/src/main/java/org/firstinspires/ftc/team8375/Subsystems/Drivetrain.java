/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

//import android.test.FlakyTest;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@SuppressWarnings("FieldCanBeLocal")
public class Drivetrain {
    private DcMotor fl, fr, bl, br;
    private BNO055IMU imu;
    private BNO055IMU.Parameters parameters;
    private int inverse = 1;
    private double imuAngle;
    private double imuOffset;
    private double turnHeading;
    private double error;
    private boolean buttonPressed = false;
    private boolean turnDone = false;
    private double position;
    private double movePower;
    private double turnPower;
    private double mPower;
    private double tPower;
    private double divisor;
    private double accLim;
    private double turnCoefficient;
    private double percent;
    private ElapsedTime Time = new ElapsedTime();
    private double output = 0;
    private boolean motorIsBusy;
    public PID pid;

    public Drivetrain(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, BNO055IMU IMU) {
        fl = frontLeft;
        fr = frontRight;
        bl = backLeft;
        br = backRight;
        imu = IMU;

    }

//    public void runOpMode() {
//        telemetry.addData("fl Position", fl.getCurrentPosition());
//        telemetry.addData("fr Position", fr.getCurrentPosition());
//        telemetry.addData("bl Position", bl.getCurrentPosition());
//        telemetry.addData("br Position", br.getCurrentPosition());
//        telemetry.update();
//    }

    public void init(DcMotor.RunMode runMode, DcMotor.ZeroPowerBehavior zeroPowerBehavior, boolean imu) {

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        fl.setMode(runMode);
        fr.setMode(runMode);
        bl.setMode(runMode);
        br.setMode(runMode);

        fl.setZeroPowerBehavior(zeroPowerBehavior);
        fr.setZeroPowerBehavior(zeroPowerBehavior);
        bl.setZeroPowerBehavior(zeroPowerBehavior);
        br.setZeroPowerBehavior(zeroPowerBehavior);

        if(imu) {
            setupIMU();
            pid = new PID(this.imu);
        }

    }

    public void init() {
        init(DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT, true);
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

//        if(forward == 0 && strafe == 0 && turn == 0) {
//            Time.reset();
//        }

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

    public void tankDrive(float leftPower, float rightPower, double acc, double greyZone, boolean headSwitchButton) {
        divisor = (acc/1.07)*((0.62*Math.pow(acc, 2))+0.45);
        // modifies the controller input for a more natural feel
        // graph for acceleration curve - https://www.desmos.com/calculator/gdwizzld3f
        movePower = (leftPower/1.07)*((0.62*Math.pow(leftPower, 2))+0.45) * inverse;
        turnPower = (rightPower/1.07)*((0.62*Math.pow(rightPower, 2))+0.45);
        if(Math.abs(movePower) == 0 && Math.abs(turnPower) == 0) {
            Time.reset();
        }
        mPower = movePower;
        tPower = turnPower;

        if(headSwitchButton) {
            buttonPressed = true;
        }

        if(buttonPressed && !headSwitchButton) {
            buttonPressed = false;

            inverse *= -1;
        }

//        same acceleration curve, but based on time instead of controller input.
//         limits the speed at which the robot accelerates

        if(Math.abs(movePower) > greyZone || Math.abs(turnPower) > greyZone) {
            accLim = (Time.time() / 1.07) * ((0.62 * Math.pow(Time.time(), 2)) + 0.45) / divisor;

            //logic gates to determine when to use time-based or controller-based power
            if (accLim < Math.abs(movePower) && accLim < Math.abs(turnPower)) {
                movePower = accLim;
                turnPower = accLim;
            } else if (accLim < Math.abs(movePower)) {
                movePower = accLim;
            } else if (accLim < Math.abs(turnPower)) {
                turnPower = accLim;
            }
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

        //set powers
        fl.setPower(movePower + turnPower);
        bl.setPower(movePower + turnPower);
        fr.setPower(movePower - turnPower);
        br.setPower(movePower - turnPower);
    }


    /**
     *
     * Autonomous Functions
     *
     */

    public void moveIn(double inches, double speed, double turn) {

        double wheelSize = (100.0/25.4) * Math.PI;
        int targetPos = (int) Math.round((inches/wheelSize) * 537.6);

        setTargetPos(targetPos);

        resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);

        setPowers(speed/100.0, turn);

        while(motorIsBusy()) {

        }

        setPowers(0, 0);

        resetEncoders(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void moveIn(double inches, double speed) {

        moveIn(inches, speed, 0);
    }

    public void turn(double heading, double speed) {
        imuAngle = getImuAngle();
        turnHeading = heading + imuOffset;
        error = turnHeading - imuAngle;

        if(heading < 0) {
            percent = -100;
        } else {
            percent = 100;
        }

        if (imuAngle != turnHeading && Math.abs(error) > 15) {
            percentSteer(percent, speed);
        }

        if(Math.abs(error) < 5) {
            turnDone = true;
        } else {
            turnDone = false;
        }
    }

    public void setImuOffset(double offset) {
        imuOffset = offset;
    }

    public double getImuAngle() {
        imuAngle = pid.getIntegratedHeading();

        return imuAngle;
    }


    /**
     *
     * Miscellanious Functions
     *
     */

    public void percentSteer(double percent, double speed) {

        if(percent < 0) {
            turnCoefficient = ((percent * (0.02 * speed)) + speed)/100.0;
            fr.setPower(speed/100.0);
            br.setPower(speed/100.0);

            fl.setPower(turnCoefficient);
            bl.setPower(turnCoefficient);

        } else if(percent >= 0) {
            turnCoefficient = ((-(percent * (0.02 * speed))) + speed)/100.0;
            fr.setPower(turnCoefficient);
            br.setPower(turnCoefficient);

            fl.setPower(speed/100.0);
            fl.setPower(speed/100.0);


        }

    }

    public void setPowers(double forward, double turn) {
        fl.setPower(forward - turn);
        fr.setPower(forward + turn);
        bl.setPower(forward - turn);
        br.setPower(forward + turn);
    }

    public double getError() { return error; }

    public boolean isTurnDone() { return turnDone; }

    public double getPositionFl() {
        return fl.getCurrentPosition();
    }
    public double getPositionBl() {
        return bl.getCurrentPosition();
    }
    public double getPositionBr() {
        return br.getCurrentPosition();
    }
    public double getPositionFr() {
        return fr.getCurrentPosition();
    }
    public double getOutput() {
        return output;
    }

    //in inches
    private double getDrivetrainPos() {

        position = fr.getCurrentPosition() + fl.getCurrentPosition() / 2.0;


        return position;
    }

    public void resetEncoders(DcMotor.RunMode runMode) {

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setRunMode(runMode);
    }

    public void setRunMode(DcMotor.RunMode runMode) {
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

    public boolean motorIsBusy() {
        if(fl.isBusy() || fr.isBusy() || bl.isBusy() || br.isBusy()) {
            return motorIsBusy();
        }

        return motorIsBusy;
    }

        public void stop() {

        setPowers(0, 0);

    }
}
