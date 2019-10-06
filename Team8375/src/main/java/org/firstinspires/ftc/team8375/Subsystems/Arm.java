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

    //degrees per tick calculation
    private static final double theta = 1;
    private static final double ticks = 20.0;
    private static final double coefficient = theta/ticks;

    private float highLimit;
    private float lastLiftPos = 0;

    private boolean buttonPressed;
    private int clawOn = -1;

    public Arm(DcMotor lift, DcMotor pitch, Servo claw, Servo yaw, Servo level) {
        this.lift = lift;
        this.pitch = pitch;
        this.claw = claw;
        this.yaw = yaw;
        this.level = level;

        //motor initialization
    }

    public void run(double liftPower, double pitchPower, boolean clawButton, double flipPos, float limitRange, float liftHigh, double autoGain) {

        //limits
        LiftPos = lift.getCurrentPosition();
        highLimit = liftHigh - limitRange;
        if(liftPower > 0 && Math.abs(LiftPos) < limitRange){
            liftPower = (-(LiftPos/limitRange))/1.0;
            lastLiftPos = LiftPos;
        }
       else if (liftPower < 0 && LiftPos <= -highLimit) {
            liftPower = ((liftHigh + LiftPos)/(limitRange/liftPower))/1.0;
            lastLiftPos = LiftPos;
        }

       //leveler
        pitchPos = pitch.getCurrentPosition();
        levelPos = (double) pitchPos * coefficient;
        level.setPosition(levelPos);

        //claw button
        if(clawButton) {
            buttonPressed = true;
        }
        if(buttonPressed && !clawButton) {
            clawOn *= -1;
            if(clawOn < 0) {
                claw.setPosition(0);
            }
            if(clawOn > 0) {
                claw.setPosition(90);
            }
        }

        //yaw position - tells the robot when to flip the claw around
        if(LiftPos >= flipPos) {
            yaw.setPosition(180);
        } else {
            yaw.setPosition(0);
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
        lift.setPower(liftPower * 0.5);
        pitch.setPower(pitchPower);
    }



    public void ArmMotorInit(int position) {

        //motor initialization
        lift.setDirection(DcMotor.Direction.FORWARD);
        //there's a lot but its all important
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
}