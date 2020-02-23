/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;

//import android.test.FlakyTest;

import android.content.Context;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team8375.dataParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("FieldCanBeLocal")
public class Drivetrain extends Subsystem {
    private DcMotor fl, fr, bl, br;
    public BNO055IMU imu;
    private BNO055IMU.Parameters parameters;

    //doubles
    private double
            imuAngle,
            imuOffset,
            turnHeading,
            error,
            position,
            movePower,
            turnPower,
            mPower,
            tPower,
            divisor,
            turnCoefficient,
            lastMovePower,
            lastTurnPower,
            targetAngle,
            percent;

    private int inverse = 1;
    private boolean buttonPressed = false;
    private boolean turnDone = false;
    private double accLim = 0;
    private double deAcc = 0;
    private ElapsedTime Time = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();
    private double output = 0;
    private boolean motorIsBusy;
    public VulcanPID pid;

    public Drivetrain() {}

    public void create() {
        fl = hwMap.dcMotor.get("front_left");
        fr = hwMap.dcMotor.get("front_right");
        bl = hwMap.dcMotor.get("back_left");
        br = hwMap.dcMotor.get("back_right");
        imu = hwMap.get(BNO055IMU.class, "imu");
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
            pid = new VulcanPID(this.imu);
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
        turnPower = turn;

//        if(forward == 0 && strafe == 0 && turn == 0) {
//            Time.reset();
//        }
//
//        double accLim = (Time.time()/1.07)*((0.62*Math.pow(Time.time(), 2))+0.45);
//
//        if(forward < 0 || turn < 0 || strafe < 0) {
//
//        }
//
        if(turn == 0) {
//            targetAngle = getImuAngle();
//            turnPower = pid.run(0.001, 0, 0, 10, targetAngle);
        } else {
            targetAngle = getImuAngle();
        }

        double[] v = {
                vd * Math.sin(theta) - turnPower,
                vd * Math.cos(theta) + turnPower,
                vd * Math.cos(theta) - turnPower,
                vd * Math.sin(theta) + turnPower
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

    public void tankDrive(float movePower, float turnPower, boolean slowModeButton) {
        divisor = (dataParser.parseDouble(prop, "drivetrain.accSpeed")/1.07)*((0.62*Math.pow(dataParser.parseDouble(prop, "drivetrain.accSpeed"), 2))+0.45);
        // modifies the controller input for a more natural feel
        // graph for acceleration curve - https://www.desmos.com/calculator/gdwizzld3f
        this.movePower = (movePower/1.07)*((0.62*Math.pow(movePower, 2))+0.45);
        this.turnPower = (turnPower/1.07)*((0.62*Math.pow(turnPower, 2))+0.45);
        if(Math.abs(this.movePower) == 0 && Math.abs(this.turnPower) == 0) {
            Time.reset();
        }
        mPower = this.movePower;
        tPower = this.turnPower;

//        if(headSwitchButton) {
//            buttonPressed = true;
//        }
//
//        if(buttonPressed && !headSwitchButton) {
//            buttonPressed = false;
//
//            inverse *= -1;
//        }

//        same acceleration curve, but based on time instead of controller input.
//         limits the speed at which the robot accelerates


//        if(Math.abs(this.movePower) > 0 || Math.abs(this.turnPower) > 0) {
//            accLim = (Time.time() / 1.07) * ((0.62 * Math.pow(Time.time(), 2)) + 0.45) / divisor;
//
//            //logic gates to determine when to use time-based or controller-based power
//            if (accLim < Math.abs(this.movePower) && accLim < Math.abs(this.turnPower)) {
//                this.movePower = accLim;
//                this.turnPower = accLim;
//            } else if (accLim < Math.abs(this.movePower)) {
//                this.movePower = accLim;
//            } else if (accLim < Math.abs(this.turnPower)) {
//                this.turnPower = accLim;
//            }
//        } else {
//            accLim = 0;
//        }
//
//
//        //makes sure the motors are going the correct way
//        if (mPower < 0 || tPower < 0) {
//            if (this.movePower == accLim) {
//                this.movePower = -this.movePower;
//            }
//            if (this.turnPower == accLim) {
//                this.turnPower = -this.turnPower;
//            }
//        }


//        if(inverse < 0) {
//            if(tPower < 0 || mPower < 0) {
////                if(movePower == accLim) {
////                    movePower *= -1;
////                }
//                if(turnPower == accLim) {
//                    turnPower *= -1;
//                }
//            }
//        }

        lastMovePower = this.movePower;
        lastTurnPower = this.turnPower;

        //set powers
        if(slowModeButton) {
            this.movePower *= dataParser.parseDouble(prop, "drivetrain.slowSpeed");
            this.turnPower *= dataParser.parseDouble(prop, "drivetrain.slowSpeed");
        } else {
            this.turnPower *= dataParser.parseDouble(prop, "drivetrain.turnSpeed");
        }


        fl.setPower(Range.clip(this.movePower + this.turnPower, -1.0, 1.0));
        bl.setPower(Range.clip(this.movePower + this.turnPower, -1.0, 1.0));
        fr.setPower(Range.clip(this.movePower - this.turnPower, -1.0, 1.0));
        br.setPower(Range.clip(this.movePower - this.turnPower, -1.0, 1.0));
    }

    public void drive(float movePower, float turnPower) {

        this.movePower = movePower;
        this.turnPower = turnPower;

        setPowers(this.movePower, this.turnPower);
    }


    /**
     *
     * Autonomous Functions
     *
     */

    public void moveIn(double inches, double speed, double turn) {

        double wheelSize = (dataParser.parseDouble(prop, "drivetrain.wheelDiameter")/25.4) * Math.PI;
        int targetPos = (int) Math.round((inches/wheelSize) * dataParser.parseDouble(prop, "drivetrain.tpr"));

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

    public void turnPercent(double speed, double turn) {
        fl.setPower((-turn * (0.01 * speed)) / 100.0);
        fr.setPower((turn * (0.01 * speed)) / 100.0);
        bl.setPower((-turn * (0.01 * speed)) / 100.0);
        br.setPower((turn * (0.01 * speed)) / 100.0);
    }

    public void movePercent(double speed, double percent) {
        setPowers((percent * (0.01 * speed)) / 100.0, 0);
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

    public double getPosition() {
        double pos = (getPositionBl() + getPositionBr() + getPositionFl() + getPositionFr()) / 4.0;

        return pos;
    }
    public double getOutput() {
        return output;
    }

    public int getInverse() {
        return inverse;
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

    @Override
    public void stop() {
    setPowers(0, 0);
    }
}
