/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="testDrive", group="Drive")
public class testDrive extends OpMode {
    protected Robot robot;

    public void init() {
        robot = new Robot(hardwareMap);
         telemetry.addData("Encoder Value", robot.arm.lift.getCurrentPosition());
         gamepad1.setJoystickDeadzone(0.075f);
         gamepad2.setJoystickDeadzone(0.075f);
    }

    public void start() {
        robot.drivetrain.setupIMU();
         robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drivetrain.Time.reset();
    }

    public void loop() {
        robot.drivetrain.mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0.2);
        robot.arm.setPowers(gamepad1.left_stick_y, -gamepad1.right_stick_y, gamepad2.a, gamepad2.left_stick_x, 300, 4250, 500);
         telemetry.addData("Arm Position", robot.arm.lift.getCurrentPosition());
         telemetry.addData("triggerTest", gamepad1.left_trigger);
         telemetry.addData("Time Active", robot.drivetrain.Time.time());
    }

    public void stop() {
        robot.stop();
    }

}
