/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "detector test", group = "test")
public class detectorTest extends VulcanPipeline {

    public void runOpMode() {
        initialize();
        waitForStart();
        isDone = false;
        while(opModeIsActive()) {

            if(!isDone) {
//
                robot.SkystoneDetect.resetScore();

                while (!robot.SkystoneDetect.detect()) {
                    robot.SkystoneDetect.setScorerThreshold(12.5);
                    robot.drivetrain.setPowers(0.1, 0);
                    robot.SkystoneDetect.resetScore();
                    sleep(100);

                    telemetry.addData("score", robot.SkystoneDetect.getScorer());
                    telemetry.update();
                }
                robot.drivetrain.setPowers(0, 0);
                isDone = true;
            }
            telemetry.addData("detected", robot.SkystoneDetect.detect());
            sleep(10);

            telemetry.addData("score", robot.SkystoneDetect.getScorer());
            robot.SkystoneDetect.resetScore();
            telemetry.update();


        }


    }
}
