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

    private final int highLimit = 10000;
    private final int slowdownDistance = 500;

    private double inputPower, liftPos;
    private int lowLimit = 0;
    private boolean clawbutton;

    private double highSlowDownPoint = highLimit - slowdownDistance;
    private double lowSlowDownPoint = lowLimit + slowdownDistance;
    private double slowdownRange = lowSlowDownPoint - lowLimit;

    private double slowdown(double input, double liftPos, double limit, double slowdownRange){
        double distanceFromLimit = Math.abs(limit - liftPos);
        double slowdownFactor = distanceFromLimit / slowdownRange;
        return(input* slowdownFactor);
    }
    private void openClaw(){
        claw.setPosition(90);
    }
    private void closeClaw(){
        claw.setPosition(0);
    }
    private void yeet(){
        while(liftPos < highSlowDownPoint){
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setTargetPosition(highLimit);
        }
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setPower(0);
        openClaw();
    }
    @Override
    public void init() {
        lift = hardwareMap.dcMotor.get("lift");
        claw = hardwareMap.servo.get("claw");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        //limit code
        inputPower = gamepad2.left_stick_y;
        liftPos = lift.getCurrentPosition();

        if(liftPos > lowSlowDownPoint && liftPos < highSlowDownPoint) {
            lift.setPower(inputPower);
        }
        if(liftPos < lowSlowDownPoint){
            lift.setPower(slowdown(inputPower, liftPos, lowLimit, slowdownRange));
        }
        if (liftPos > highSlowDownPoint) {
            lift.setPower(slowdown(inputPower, liftPos, highLimit, slowdownRange));
        }
        //claw code
        clawbutton = gamepad2.x;
        if (clawbutton = true){
            openClaw();
        }
        else{
            closeClaw();
        }
        //yeet code
        if (gamepad2.y = true){
            yeet();
        }
    }
}
