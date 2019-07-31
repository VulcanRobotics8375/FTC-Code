/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

public class AutoPathTest extends LinearOpMode {

    protected Robot robot;

    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);

        waitForStart();

        do {
            robot.drivetrain.pid.init();
            robot.drivetrain.turn(0.1, 0.1, 0.1, 90);

        } while(opModeIsActive());

    }
}
