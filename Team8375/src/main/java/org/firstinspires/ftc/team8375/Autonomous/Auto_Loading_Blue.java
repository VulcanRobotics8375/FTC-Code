/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@Autonomous(name = "yeet", group = "yeet")
public class Auto_Loading_Blue extends VulcanPipeline {
    private boolean stone;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        robot.drivetrain.resetEncoders(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drivetrain.setupIMU();
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {


            moveIn(10, 50);
            turn(90, 30);

            robot.SkystoneDetect.setScorerThreshold(12);

            while(!robot.SkystoneDetect.detect()) {
                robot.drivetrain.setPowers(0.2, 0);
                robot.SkystoneDetect.resetScore();
            }

            turn(90, 30);

        }

        robot.drivetrain.stopDriveTrain();
    }



}
