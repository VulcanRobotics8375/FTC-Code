/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@Autonomous(name = "yeet", group = "yeet")
public class Auto_Loading_Red extends VulcanPipeline {
    private boolean stone;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        initialize();

        waitForStart();

        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(18, 50);
                turn(90, 15);

                robot.SkystoneDetect.resetScore();

                while (!robot.SkystoneDetect.detect()) {
                    robot.SkystoneDetect.setScorerThreshold(12.5);
                    robot.drivetrain.setPowers(0.1, 0);
                    robot.SkystoneDetect.resetScore();
                    sleep(100);

                }
                robot.drivetrain.setPowers(0, 0);

                turn(90, 10);
                robot.intake.setPowers(-0.4);
                moveIn(-8, -50);
                robot.intake.setPowers(0);
                moveIn(8, 30);
                turn(-90, 10);
                moveIn(-50, -50);
                robot.intake.setPowers(0.4);
                robot.intake.setPowers(0);
//                moveIn(20, 20);

                isDone = true;
            }


        }

        robot.stop();
    }



}
