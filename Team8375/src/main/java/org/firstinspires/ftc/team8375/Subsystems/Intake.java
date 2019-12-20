/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("FieldCanBeLocal")
public class Intake {

    private ElapsedTime time = new ElapsedTime();
    private ElapsedTime intakeTime = new ElapsedTime();
    private boolean onPressed;
    private int intakeOn = 1;
    private boolean reset = false;
    //motors
    private DcMotor intake_left;
    private DcMotor intake_right;
    private double intakePower;

    //servos
    private CRServo deploy_left;
    private CRServo deploy_right;
    private Servo autoArm;
    //b --
    private final double b = 1.0;
    private final double min = 0.2;

    private Rev2mDistanceSensor irSensor;

    public Intake(DcMotor intakeLeft, DcMotor intakeRight, CRServo deployLeft, CRServo deployRight, Servo autoArm, Rev2mDistanceSensor irSensor) {
        intake_left = intakeLeft;
        intake_right = intakeRight;

        this.autoArm = autoArm;
        deploy_left = deployLeft;
        deploy_right = deployRight;

        this.irSensor = irSensor;
    }

    public void resetDeployTime() {
        time.reset();
    }

    public void deploy(boolean left, boolean right) {
        if(left) {
            deploy_right.setPower(-1.0);
            deploy_left.setPower(1.0);
        } else if(right) {
            deploy_right.setPower(1.0);
            deploy_left.setPower(-1.0);
        } else {
            deploy_right.setPower(0.05);
            deploy_left.setPower(0);
        }

    }

    public void run(double intakePower, boolean reverse, double isOn) {
        this.intakePower = intakePower;
        if(isOn > 0) {
            intakeOn = 1;
        } else if(isOn == 0) {
            intakeOn = -1;
        }

        if(intakeOn > 0) {

            if (reverse) {
                intake_left.setPower(-this.intakePower);
                intake_right.setPower(-this.intakePower);

            } else {
                if(getIRDistance(DistanceUnit.CM) < 10.0) {
                    this.intakePower = Math.pow(min, ((1/b) * intakeTime.time(TimeUnit.SECONDS)));
                    if(intakePower < 0) {
                        this.intakePower *= -1;
                        this.intakePower = Range.clip(this.intakePower, intakePower, min);
                    } else {
                        this.intakePower = Range.clip(this.intakePower, min, intakePower);
                    }
                } else {
                    intakeTime.reset();
                    this.intakePower = intakePower;
                }
                intake_left.setPower(this.intakePower);
                intake_right.setPower(this.intakePower);
            }



        } else {
            intake_left.setPower(0);
            intake_right.setPower(0);
        }

    }

    public void setPowers(double power) {
        intake_left.setPower(power);
        intake_right.setPower(power);
    }

    public void init() {
        intake_right.setDirection(DcMotor.Direction.REVERSE);
        resetDeployTime();
    }

    public double getIRDistance(DistanceUnit unit) {
        return irSensor.getDistance(unit);
    }

    public void stop() {
        setPowers(0);
        deploy_left.setPower(0);
        deploy_right.setPower(0);
    }

    public void autoArm(double pos) {
        autoArm.setPosition(pos);

    }

    public double getAutoArm() {
        return autoArm.getPosition();
    }

    public double getDeployLeftPos() {
        return deploy_left.getPower();
    }
    public double getDeployRightPos() {
        return deploy_right.getPower();
    }

}