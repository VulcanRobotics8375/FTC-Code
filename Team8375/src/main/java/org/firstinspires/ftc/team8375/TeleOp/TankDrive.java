/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="TankDrive", group="Drive")
public class TankDrive extends OpMode {
    protected Robot robot;
    public void init() {
        robot = new Robot(hardwareMap);
        telemetry.addData("Encoder Value", robot.arm.lift.getCurrentPosition());
//        gamepad1.setJoystickDeadzone(0.075f);
//        gamepad2.setJoystickDeadzone(0.075f);
//        robot.arm.flip.setTargetPosition(100);
//        robot.arm.flip.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.arm.flip.setPower(0.05);
//        if(!robot.arm.flip.isBusy()) {
//            robot.arm.flip.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
    }

    public void start() {
        robot.drivetrain.setupIMU();
        robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drivetrain.Time.reset();
    }

    public void loop() {
        robot.drivetrain.tankDrive(-gamepad1.right_stick_x, gamepad1.left_stick_y,0.2);
        robot.arm.setPowers(gamepad2.left_stick_y, gamepad2.right_stick_x, gamepad2.a, gamepad2.left_stick_x * 0.05, 500, 3500, 400.0);
        telemetry.addData("lift Position", robot.arm.lift.getCurrentPosition());
        telemetry.addData("Claw Position", robot.arm.claw.getCurrentPosition());
        telemetry.addData("Flip Position", robot.arm.flip.getCurrentPosition());
        telemetry.addData("Time", robot.drivetrain.Time.time());
        telemetry.update();
    }

    public void stop() {
        robot.stop();
    }
}
