/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "foundation move -- red", group = "foundation move")
public class Auto_Foundation_Move_Red extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(45, 45);
                turn(-87, 15);
                moveIn(7, 15);
                robot.foundation.setFoundationMoveAngle(30);
                sleep(800);
                moveIn(-2, -15);
                turn(-65, 15);
                moveIn(24, 30);
                robot.foundation.setFoundationMoveAngle(180);
                moveIn(-4, -20);
                turn(79, 15);
                moveIn(-26, -30);

                isDone = true;
            }
        }

        robot.stop();

    }
}
