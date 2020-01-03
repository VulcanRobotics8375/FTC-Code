/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import android.content.Context;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AutoArm {

    private Context context;
    private Servo flip;
    private Servo claw;
    private CRServo lift;

//    private double a, k;
    private static final double a = 1685.64;
    private static final double k = 937.812;
    private double liftPos;
    private boolean moveDone;

    private Properties prop;
    private ElapsedTime liftTime = new ElapsedTime();
    private boolean firstRun = true;

//    private static final double resetPos = 2;

    public AutoArm(Servo flip, Servo claw, CRServo lift) {
        this.flip = flip;
        this.claw = claw;
        this.lift = lift;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("config.properties");
            if(input != null) {
                prop = new Properties();
                prop.load(input);
            } else {

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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

    public void timeLiftWhileLoop(double power, double ms) {
        liftTime.reset();
        while(liftTime.now(TimeUnit.MILLISECONDS) < ms) {
            lift.setPower(power);
        }
    }

    public void setLiftPos(double pos, double power) {
        liftTime.reset();
        moveDone = false;
        double target = Math.abs(pos - liftPos);
        double powerCoeff = (a/power) + k;
        double time = target * powerCoeff;

        if(liftTime.now(TimeUnit.MILLISECONDS) < time) {
            lift.setPower(power);
        } else {
            moveDone = true;
            lift.setPower(0);
        }
        liftPos = pos;

    }

    public void reset() {
        setFlipPos(0);
        setClawPos(30);
    }

    public boolean isMoveDone() {
        return moveDone;
    }

    public void extendLift(double power) {
        lift.setPower(power);
    }

    public double getLiftPos() {
        return liftPos;
    }

}
