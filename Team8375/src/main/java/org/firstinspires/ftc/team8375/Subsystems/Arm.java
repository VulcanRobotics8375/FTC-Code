/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
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
    private Servo yaw;
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
    private static final double levelBias = 0.1;

    private float liftHighLimit;
    private float pitchHighLimit;
    private float lastLiftPos = 0;

    private boolean clawPressed;
    private boolean flipPressed;
    private int clawOn = -1;

    public Arm(DcMotor lift, DcMotor pitch, Servo claw, Servo yaw, Servo level) {
        this.lift = lift;
        this.pitch = pitch;
        this.claw = claw;
        this.yaw = yaw;
        this.level = level;

        //motor initialization
    }

    public void run(double liftPower, double pitchPower, boolean clawButton, boolean flipButton, double flipPos, float limitRange, float liftHigh, float pitchHigh, double autoGain) {

        //limits
        LiftPos = lift.getCurrentPosition();
        pitchPos = pitch.getCurrentPosition();
        liftHighLimit = liftHigh - limitRange;
        pitchHighLimit = pitchHigh - limitRange;

        //lower bound
        if(liftPower > 0 && LiftPos <= limitRange){

            //gradual slow down
            this.liftPower = (LiftPos/limitRange)/1.0;
            lastLiftPos = LiftPos;
        }

        //upper bound
       else if (liftPower < 0 && LiftPos >= liftHighLimit) {

           //takes the distance from the upper limit and divides it by the limit range for a gradual slow down of the motor.
            this.liftPower = -((liftHigh - LiftPos)/(limitRange/liftPower))/1.0;
            lastLiftPos = LiftPos;
        } else {
           this.liftPower = liftPower;
        }

        if(pitchPower < 0 && Math.abs(pitchPos) <= limitRange){
            this.pitchPower = (-(pitchPos/limitRange))/1.0;

        }
        else if (pitchPower > 0 && pitchPos >= pitchHighLimit) {
            this.pitchPower = (pitchHigh - pitchPos)/(limitRange/pitchPower)/1.0;
        } else {
            this.pitchPower = pitchPower;
        }

       //leveler
        levelPos = (double) pitchPos * coefficient;
        setServoAngle(level, levelPos + levelBias);

        //claw button
        if(clawButton) {
            clawPressed = true;
        }
        if(clawPressed && !clawButton) {
            clawOn *= -1;
            if(clawOn < 0) {
                setServoAngle(claw, 0);
            }
            if(clawOn > 0) {
                setServoAngle(claw, 90);
            }
        }

    //flip Limits
    if(LiftPos >= flipPos) {

        //flip button -- only works if the lift is higher than a specific position
        if (flipButton && LiftPos >= flipPos) {
            flipPressed = true;
        }
        if (flipPressed && !flipButton) {

            yaw.setPosition(0);
            flipPressed = false;
        }
    }

        //auto-correct function to make sure the arm is in the desired position when stopped.
        //sometimes the arm can sag under its own weight and this prevents that from happening

        /* EDIT:: this isn't needed in most applications, so it's commented out for now */

//        if(Math.abs(lastFlipPos-flipPos)>10 && Math.abs(flipPower) < 0.055) {
//            float error = lastFlipPos - flipPos;
//            flipPower = -(error / autoGain);
//        }
//        if(Math.abs(lastFlipPos-flipPos) > 100) {
//            lastFlipPos = flipPos;
//        }




        //set powers
        lift.setPower(this.liftPower * 0.5);
        pitch.setPower(this.pitchPower);
    }



    public void ArmMotorInit(int position) {

        //motor initialization
        lift.setDirection(DcMotor.Direction.FORWARD);
        //there's a lot but its all important
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pitch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pitch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(position > 0) {
            lift.setTargetPosition(-position);
            lift.setPower(0.05);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if(position < 0) {
            lift.setTargetPosition(position);
            lift.setPower(-0.05);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if(!lift.isBusy()){
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }


    private void setServoAngle(Servo servo, double angle) {
        servo.setPosition(angle/180.0);

    }

    //Testing stuff
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
    public double getYawPos() {
        return yaw.getPosition();
    }
}