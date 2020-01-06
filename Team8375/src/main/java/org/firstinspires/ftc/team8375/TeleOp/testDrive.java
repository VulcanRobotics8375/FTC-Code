/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="testDrive", group="Test")
public class testDrive extends OpMode {
    protected Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.arm.ArmMotorInit(0);
        robot.drivetrain.init();

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        robot.arm.motorTest(gamepad2.left_stick_y, gamepad2.right_stick_y);


        //Drivetrain
        telemetry.addData("front Left", robot.drivetrain.getPositionFl());
        telemetry.addData("front Right", robot.drivetrain.getPositionFr());
        telemetry.addData("back Left", robot.drivetrain.getPositionBl());
        telemetry.addData("back Right", robot.drivetrain.getPositionBr());

        //Arm
        telemetry.addData("lift", robot.arm.getLiftPos());
        telemetry.addData("claw", robot.arm.getClawPos());
        telemetry.addData("pitch", robot.arm.getPitchPos());
        telemetry.addData("level", robot.arm.getLevelPos());

        //Intake
        telemetry.addData("deployLeft", robot.intake.getDeployLeftPos());
        telemetry.addData("deployRight", robot.intake.getDeployRightPos());

        telemetry.update();
    }

    public void stop() {
        robot.stop();
    }

}
