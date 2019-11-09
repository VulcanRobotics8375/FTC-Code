/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("FieldCanBeLocal")
public class Arm {
    // variable initialization
    private DcMotor lift;
    private DcMotor pitch;
    private Servo claw;
    private CRServo yaw;
    private Servo level;

    private float LiftPos;
    private float pitchPos;
    private double levelPos;
    private double liftPower;
    private double pitchPower;

    //degrees per tick calculation
    private static final double theta = 27;
    private static final double ticks = 9850;
    private static final double coefficient = theta/ticks;
                                // degrees / 180.0
    private static final double levelBias = 95;

    private float liftHighLimit;
    private float pitchHighLimit;
    private float lastLiftPos = 0;
    private double yawClockwise;
    private double yawCounterClockwise;

    private boolean clawPressed;
    private boolean bypass;
    private int clawOn = -1;
    private int flipOn = -1;

    public Arm(DcMotor lift, DcMotor pitch, Servo claw, CRServo yaw, Servo level) {
        this.lift = lift;
        this.pitch = pitch;
        this.claw = claw;
        this.yaw = yaw;
        this.level = level;

        //motor initialization
    }

    public void run(double liftPower, double pitchPower, boolean clawButton, double yawPower, double flipPos, float limitRange, float liftHigh, float pitchHigh, double autoGain, boolean bypass) {

        //limits
        LiftPos = lift.getCurrentPosition();
        pitchPos = pitch.getCurrentPosition();
        liftHighLimit = liftHigh - limitRange;
        pitchHighLimit = pitchHigh - limitRange;

        //lower bound
//        if(liftPower > 0 && LiftPos <= limitRange){
//
//            //gradual slow down
//            this.liftPower = ((LiftPos/limitRange))/1.0;
//            lastLiftPos = LiftPos;
//        }
//
//        //upper bound
//       else if (liftPower < 0 && LiftPos >= liftHighLimit) {
//
//           //takes the distance from the upper limit and divides it by the limit range for a gradual slow down of the motor.
//            this.liftPower = -((liftHigh - LiftPos)/(limitRange))/1.0;
//            lastLiftPos = LiftPos;
//        } else {
           this.liftPower = liftPower;
//        }

        if(!bypass && !this.bypass) {
            if (pitchPower < 0 && Math.abs(pitchPos) <= limitRange) {
                this.pitchPower = (-(pitchPos / (limitRange / Math.abs(pitchPower)))) / 1.0;

            } else if (pitchPower > 0 && pitchPos >= pitchHighLimit) {
                this.pitchPower = (pitchHigh - pitchPos) / (limitRange / pitchPower) / 1.0;
            } else {
                this.pitchPower = pitchPower;
            }
        } else if(bypass) {
            this.pitchPower = pitchPower;
            this.bypass = true;
        }
        if(this.bypass && !bypass) {
            pitch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.bypass = false;
        }

       //leveler
        levelPos = (double) pitchPos * coefficient;

        if(pitchPos != 0) {
            setServoAngle(level, levelPos + levelBias);
        } else {
            setServoAngle(level, levelBias);
        }

        //claw button
        if(clawButton) {
            clawPressed = true;
        }
        if(clawPressed && !clawButton) {
            clawOn *= -1;
            clawPressed = false;
        }

        if(clawOn < 0) {
            setServoAngle(claw, 170);
        }
        else if(clawOn > 0) {
            setServoAngle(claw, 125);
        }

        //set powers
        if(yawPower != 0) {
            yaw.setPower(yawPower);
        } else {
            yaw.setPower(-0.05);
        }

        lift.setPower(this.liftPower * 0.3);
        pitch.setPower(this.pitchPower);
    }



    public void ArmMotorInit(int position) {

        //motor initialization
//        lift.setDirection(DcMotor.Direction.REVERSE);
        //there's a lot but its all important
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pitch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pitch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        if(position > 0) {
//            lift.setTargetPosition(-position);
//            lift.setPower(0.05);
//            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        } else if(position < 0) {
//            lift.setTargetPosition(position);
//            lift.setPower(-0.05);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
//
//        if(!lift.isBusy()){
//            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }

    }


    private void setServoAngle(Servo servo, double angle) {

        if(angle != 0) {
            servo.setPosition(angle / 180.0);
        } else {
            servo.setPosition(angle);
        }

    }

    public void stop() {
        yaw.setPower(0);
        lift.setPower(0);
        pitch.setPower(0);
    }

    //Testing stuff

    public double getLiftPower() {
        return liftPower;
    }
    public float getLiftPos() {
        return LiftPos;
    }
    public float getPitchPos() {
        return pitchPos;
    }
    public double getClawPos() {
        return claw.getPosition();
    }
    public double getLevelPos() {
        return level.getPosition();
    }

    public double getYawClockwise() {
        return yaw.getPower();
    }
}