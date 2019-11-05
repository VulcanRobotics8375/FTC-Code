/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name = "detectorTest", group = "test")
public class detectorTest extends LinearOpMode {
    protected Robot robot;
    private boolean detected;

    public void runOpMode() {
        robot = new Robot(hardwareMap);

        robot.SkystoneDetect.setScorerThreshold(12.5);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("hue", robot.SkystoneDetect.getHSV(0));
            telemetry.addData("saturation", robot.SkystoneDetect.getHSV(1));
            telemetry.addData("Value", robot.SkystoneDetect.getHSV(2));

            detected = robot.SkystoneDetect.detect();

            sleep(100);

            telemetry.addData("detected", detected);
            telemetry.addData("score", robot.SkystoneDetect.getScorer());

            robot.SkystoneDetect.resetScore();

            telemetry.update();
        }

    }
}
