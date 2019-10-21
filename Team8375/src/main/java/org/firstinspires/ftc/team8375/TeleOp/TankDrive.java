/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

import java.util.concurrent.TimeUnit;

@TeleOp(name="TankDrive", group="Drive")
public class TankDrive extends OpMode {
    protected Robot robot;
    private ElapsedTime runtime = new ElapsedTime();
    private boolean startDone = false;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.arm.ArmMotorInit(0);
    }

//    @Override
//    public void init_loop() {
//
//    }

    @Override
    public void start() {
        robot.drivetrain.setupIMU();

        while(!startDone) {
            robot.intake.deploy(1);


            startDone = true;
        }
//        robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        runtime.reset();

    }

    public void loop() {
        robot.drivetrain.tankDrive(-gamepad1.right_stick_x, gamepad1.left_stick_y,0.5, 0.1, gamepad1.left_bumper);
        robot.arm.run(gamepad2.left_stick_y, gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.left_bumper, 900, 300, 2150, 9850, 500);
        robot.intake.run(-0.5, gamepad2.a, gamepad1.dpad_left);

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

        telemetry.addData("Runtime", runtime.time(TimeUnit.SECONDS));

        telemetry.update();
    }

    @Override
    public void stop() {
        robot.stop();
    }

    public void setTelemetryOn() {
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

        telemetry.addData("Runtime", runtime.time(TimeUnit.SECONDS));
    }
}
