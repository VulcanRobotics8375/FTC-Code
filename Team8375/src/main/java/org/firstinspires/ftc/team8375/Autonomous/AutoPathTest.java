/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@Autonomous(name="autoTest", group = "test")
public class AutoPathTest extends LinearOpMode {

    protected Robot robot;
    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);

        robot.drivetrain.resetEncoders(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drivetrain.pid.initIMU();
        telemetry.addData("calibration status", robot.drivetrain.pid.getCalibrationStatus().toString());
        telemetry.update();
        waitForStart();

        do {
//            robot.drivetrain.pid.initHeading();
//            robot.drivetrain.turn(0.32, 0, 0, 90);
//            robot.drivetrain.moveIn(20, 0.4);

            telemetry.addData("fl", robot.drivetrain.getPositionFl());
            telemetry.addData("fr", robot.drivetrain.getPositionFr());
            telemetry.addData("bl", robot.drivetrain.getPositionBl());
            telemetry.addData("br", robot.drivetrain.getPositionBr());


            telemetry.update();

        } while(opModeIsActive());

        robot.drivetrain.stopDriveTrain();

    }
}
