/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class AutoArm {

    private Servo flip;
    private Servo claw;
    private CRServo lift;

    private int liftPos = -1;

    private ElapsedTime liftTime = new ElapsedTime();
    private boolean firstRun = true;

//    private static final double resetPos = 2;

    public AutoArm(Servo flip, Servo claw, CRServo lift) {
        this.flip = flip;
        this.claw = claw;
        this.lift = lift;
    }

    public void setServoAngle(Servo servo, double angle) {
        servo.setPosition(angle / 180.0);
    }

    public void setFlipPos(double angle) {
        setServoAngle(flip, angle);
    }
    public void setClawPos(double angle) {
        setServoAngle(claw, angle);
    }

    public void setLiftPower(double power) {
        lift.setPower(power);
    }

    public void setLiftTime(double power, double ms) {
        if(firstRun) {
            liftTime.reset();
            firstRun = false;
        }
        if(liftTime.time(TimeUnit.MILLISECONDS) <= ms) {
            setLiftPower(power);
        } else {
            setLiftPower(0);
            firstRun = true;
        }
    }

    public void reset() {
        setFlipPos(0);
        setClawPos(30);
    }

    public void extendLift(double power) {
        lift.setPower(power);
        liftPos *= -1;
    }

    public int getLiftPos() {
        return liftPos;
    }

}
