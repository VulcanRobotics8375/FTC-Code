/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "dylanMotorLimit", group = "example")
public class dylanLimitCode extends OpMode {
    private DcMotor lift;
    private Servo claw;
    private double inputPower, lowLimit;
    private double highLimit = 10000;
    private double slowdownDistance = 500;
    private double highSlowDownPoint = highLimit - slowdownDistance;
    private double lowSlowDownPoint = lowLimit + slowdownDistance;
    private double liftPos;
    private double slowdownRange = lowSlowDownPoint - lowLimit;
    private double distanceFromLimit;
    private double slowdownFactor;
    private double slowdown(double liftPos, double slowdownRange, double input, double limit){
        distanceFromLimit = Math.abs(limit - liftPos);
        slowdownFactor = distanceFromLimit/slowdownRange;
        return(input*slowdownFactor);
    }

    @Override
    public void init() {
        //initialization code
        lift = hardwareMap.dcMotor.get("lift");
        claw = hardwareMap.servo.get("claw");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void init_loop() {
        //optional
    }

    @Override
    public void start() {
        //runs once when the start button is pressed
    }

    @Override
    public void loop() {
        inputPower = gamepad2.left_stick_y;
        liftPos = lift.getCurrentPosition();

        if(liftPos > lowSlowDownPoint && liftPos < highSlowDownPoint) {
            lift.setPower(inputPower);
        }
        if(liftPos < lowSlowDownPoint && liftPos > lowLimit){
            lift.setPower(slowdown(liftPos, slowdownRange, inputPower, lowLimit));
        }
        if (liftPos > highSlowDownPoint && liftPos < highLimit) {
            lift.setPower(slowdown(liftPos, slowdownRange, inputPower, highLimit));
        }
        if (liftPos <= lowLimit){
            lift.setPower(0.1);
        }
        if (liftPos >= highLimit){
            lift.setPower(-0.1);
        }

    }
}
