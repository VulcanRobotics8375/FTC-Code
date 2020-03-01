/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.team8375.dataParser;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("FieldCanBeLocal")
public class Intake extends Subsystem {
    private ElapsedTime time = new ElapsedTime();
    private ElapsedTime intakeTime = new ElapsedTime();
    private int intakeOn = 1;
    //motors
    private DcMotor intake_left;
    private DcMotor intake_right;
    private Rev2mDistanceSensor irSensor;
    private double intakePower;
    private boolean stoneIn = false;

    public Intake() {}

    @Override
    public void create() {
        intake_left = hwMap.dcMotor.get("intake_left");
        intake_right = hwMap.dcMotor.get("intake_right");
        irSensor = hwMap.get(Rev2mDistanceSensor.class, "intake_sensor");

    }

    public void resetDeployTime() {
        time.reset();
    }

    public void run(boolean reverse, double isOn) {
        intakePower = dataParser.parseDouble(prop, "intake.power");
        if(isOn > 0) {
            intakeOn = 1;
        } else if(isOn == 0) {
            intakeOn = -1;
        }

        if(intakeOn > 0) {

            if (reverse) {
                intake_left.setPower(-intakePower);
                intake_right.setPower(intakePower);

            } else {
                if(getIRDistance(DistanceUnit.CM) < dataParser.parseDouble(prop, "intake.irDistance")) {
                    stoneIn = true;
                }

                if(stoneIn) {
                    intakePower = Math.pow(
                            dataParser.parseDouble(prop, "intake.minPower"),
                            ((1/dataParser.parseDouble(prop, "intake.accSpeed")) * (intakeTime.time(TimeUnit.MILLISECONDS) / 1000.0))
                    );

                    if(intakePower < 0) {
                        intakePower *= -1;
                        intakePower = Range.clip(intakePower, intakePower, -dataParser.parseDouble(prop, "intake.minPower"));
                    } else {
                        intakePower = Range.clip(intakePower, dataParser.parseDouble(prop, "intake.minPower"), intakePower);
                    }
                } else {
                    intakeTime.reset();
                }

                intake_left.setPower(intakePower);
                intake_right.setPower(-intakePower);
            }


        } else {
            if(isOn == 0) {
                stoneIn = false;
            }
            intake_left.setPower(0);
            intake_right.setPower(0);
        }

    }

    public void setPowers(double power) {
        intake_left.setPower(power);
        intake_right.setPower(-power);
    }

    public void init() {
        intake_right.setDirection(DcMotor.Direction.REVERSE);
        resetDeployTime();
    }

    public double getIRDistance(DistanceUnit unit) {
        return irSensor.getDistance(unit);
    }


    @Override
    public void stop() {
        setPowers(0);
    }

}