/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="foundation Move -- blue", group = "foundation move")
public class Auto_Foundation_Move_Blue extends VulcanPipeline {

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        do {

            if(!isDone) {

                robot.drivetrain.percentSteer(50, 10);
                sleepOpMode(1700);
                robot.drivetrain.setPowers(0, 0);
                moveIn(28, 50);
                robot.drivetrain.percentSteer(-50, 10);
                sleepOpMode(1700);
                robot.drivetrain.setPowers(0, 0);

                robot.foundation.setFoundationMoveAngle(30);
                sleep(500);

                moveIn(-28, -50);

                robot.foundation.setFoundationMoveAngle(180);

            }

            isDone = true;

        } while(opModeIsActive());

        robot.stop();

    }
}

