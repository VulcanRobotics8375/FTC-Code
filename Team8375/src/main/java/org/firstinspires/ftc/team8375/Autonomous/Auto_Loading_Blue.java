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
            moveIn(5, 30);

            robot.skystoneDetect.setScorerThreshold(7);
            for(int i = 0; i < 6; i++) {
                moveIn(1, 15);
                stone = robot.skystoneDetect.detect();
                if(!stone) {
                    break;
                }

                sleep(100);
            }

            turn(90, 30);

        }
    }



}
