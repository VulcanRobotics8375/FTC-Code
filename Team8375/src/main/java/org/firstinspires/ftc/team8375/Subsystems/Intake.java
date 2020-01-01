/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
    //b --
    private final double b = 0.5;
    private final double min = 0.2;

    Properties prop;

    private Rev2mDistanceSensor irSensor;

    public Intake(DcMotor intakeLeft, DcMotor intakeRight, CRServo deployLeft, CRServo deployRight, Rev2mDistanceSensor irSensor) {
        intake_left = intakeLeft;
        intake_right = intakeRight;

        deploy_left = deployLeft;
        deploy_right = deployRight;

        this.irSensor = irSensor;

        try (InputStream input = Intake.class.getClassLoader().getResourceAsStream("values/config.properties")) {
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
                if(getIRDistance(DistanceUnit.CM) < 15.0) {
                    this.intakePower = Math.pow(min, ((1/b) * (intakeTime.time(TimeUnit.MILLISECONDS) / 1000.0)));

                    if(intakePower < 0) {
                        this.intakePower *= -1;
                        this.intakePower = Range.clip(this.intakePower, intakePower, -min);
                    } else {
                        this.intakePower = Range.clip(this.intakePower, min, intakePower);
                    }
                } else {
                    intakeTime.reset();
                    this.intakePower = intakePower;
                }
                intake_left.setPower(this.intakePower);
                intake_right.setPower(-this.intakePower);
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

    public double getDeployLeftPos() {
        return deploy_left.getPower();
    }
    public double getDeployRightPos() {
        return deploy_right.getPower();
    }

}