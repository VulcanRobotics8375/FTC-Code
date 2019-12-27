/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="auto arm", group="test")
public class ArmTest extends LinearOpMode {
    private Robot robot;

    private double power = 1;
    private long ms = 2000;

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean start = false;

    public void runOpMode() {
        robot = new Robot(hardwareMap);
//        Thread t = new Thread();

        waitForStart();
        while (opModeIsActive()) {
//        telemetry.addData("thread", Thread.currentThread());
            if (gamepad1.dpad_up && !up) {
                ms += 50;
                up = true;
            }
            if (up && !gamepad1.dpad_up) {
                up = false;
            }

            if (gamepad1.dpad_down && !down) {
                ms -= 50;
                down = true;
            }
            if (down && !gamepad1.dpad_down) {
                down = false;
            }

            if (gamepad1.dpad_left && !left) {
                power -= 0.1;
                left = true;
            }
            if (left && !gamepad1.dpad_left) {
                left = false;
            }

            if (gamepad1.dpad_right && !right) {
                power += 0.1;
                right = true;
            }
            if (right && !gamepad1.dpad_right) {
                right = false;
            }

            if (gamepad1.a && !start) {
                start = true;
            }
            if (start && !robot.autoArm.isMoveDone()) {
                robot.autoArm.setLiftPos(0.5, power);
            } if(start && robot.autoArm.isMoveDone()) {
                start = false;
            }

            telemetry.addData("power", power);
            telemetry.addData("ms", ms);
            telemetry.update();
        }
    }

}
