/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    // variable initialization
    public DcMotor lift;
    public DcMotor claw;
    public CRServo intake;
    public DcMotor flip;
    float LiftPos;
    float flipPos;
    float clawPos;
    float highLimit;
    float lastLiftPos = 0;
    float lastFlipPos = 0;
    double intakePower = 0.5;

    public Arm(int targetStartPosition, DcMotor Lift, DcMotor Claw, CRServo Intake, DcMotor Flip) {
        lift = Lift;
        //left = 0, right = 1 (might not be correct but I'll test it later)
        claw = Claw;
        intake = Intake;
        flip = Flip;

        //motor initialization
        ArmMotorInit(targetStartPosition);
    }
// high limit - 2375
    public void setPowers(double liftPower, double clawPower, float intakeButton, double flipPower, float limitRange, float liftHigh, double autoGain) {

        if(intakeButton > 0){
            intake.setPower(-intakePower);
        } else{
            intake.setPower(intakePower);
        }
//        if (flipButton) {
//            flip.setPosition(180);
//        } else {
//            flip.setPosition(0);
//        }

        //limits
        LiftPos = lift.getCurrentPosition();
        flipPos = flip.getCurrentPosition();
        clawPos = claw.getCurrentPosition();
        highLimit = liftHigh - limitRange;
        if(liftPower > 0 && Math.abs(LiftPos) < limitRange){
            liftPower = (-(LiftPos/limitRange))/1.0;
            lastLiftPos = LiftPos;
        }
       else if (liftPower < 0 && LiftPos <= -highLimit) {
            liftPower = ((liftHigh + LiftPos)/(limitRange/liftPower))/1.0;
            lastLiftPos = LiftPos;
        }

       if(clawPower > 0 && Math.abs(clawPos) < limitRange) {
           clawPower = (-(LiftPos/limitRange)/1.0);
       }

        //auto-correct function to make sure the arm is in the desired position when stopped.
        //sometimes the arm can sag under its own weight and this prevents that from happening
        // EDIT:: this isn't needed if you use worm gears, so it's commented out for now
//        if(Math.abs(lastFlipPos-flipPos)>10 && Math.abs(flipPower) < 0.055) {
//            float error = lastFlipPos - flipPos;
//            flipPower = -(error / autoGain);
//        }
//        if(Math.abs(lastFlipPos-flipPos) > 100) {
//            lastFlipPos = flipPos;
//        }




        //set powers
        lift.setPower(liftPower * 0.5);
        claw.setPower(clawPower);
        flip.setPower(flipPower);
    }



    public void ArmMotorInit(int position) {

        //motor initialization
        lift.setDirection(DcMotor.Direction.FORWARD);
        claw.setDirection(DcMotor.Direction.FORWARD);
        flip.setDirection(DcMotor.Direction.FORWARD);
        //there's a lot but its all important
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        claw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flip.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flip.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flip.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
}