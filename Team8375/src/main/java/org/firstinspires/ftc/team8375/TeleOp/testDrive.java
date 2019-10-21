/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
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
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

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
        telemetry.addData("yaw", robot.arm.getYawPos());

        //Intake
        telemetry.addData("deployLeft", robot.intake.getDeployLeftPos());
        telemetry.addData("deployRight", robot.intake.getDeployRightPos());

        telemetry.update();
    }

    public void stop() {
        robot.stop();
    }

}
