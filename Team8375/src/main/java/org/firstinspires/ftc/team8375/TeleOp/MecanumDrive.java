/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.team8375.Subsystems.testRobot;

@TeleOp(name="MecanumDrive", group="Drive")
public class MecanumDrive extends OpMode {
    protected testRobot robot;

    public void init() {
        robot = new testRobot(hardwareMap);
        robot.drivetrain.init();
//         telemetry.addData("Encoder Value", robot.arm.lift.getCurrentPosition());
        robot.drivetrain.setupIMU();
         gamepad1.setJoystickDeadzone(0.075f);
         gamepad2.setJoystickDeadzone(0.075f);
    }

    public void start() {
//         robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {
        robot.drivetrain.mecanumDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x, 1);
//        robot.arm.setPowers(gamepad2.left_stick_y, -gamepad2.right_stick_y, gamepad2.a, gamepad2.left_stick_x, 300, 5000, 500);
//         telemetry.addData("Arm Position", robot.arm.lift.getCurrentPosition());
//         telemetry.addData("Time Active", robot.drivetrain.Time.time());
        telemetry.addData("output pid", robot.drivetrain.pid.getOutput());
        telemetry.addData("angle", robot.drivetrain.getImuAngle());
        telemetry.addData("error", robot.drivetrain.getError());
         telemetry.update();
    }

    public void stop() {
        robot.stop();
    }

}
