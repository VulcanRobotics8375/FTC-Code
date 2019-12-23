/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="foundation -- blue -- outside", group = "foundation move")
public class Auto_Foundation_Move_Blue_Outside extends VulcanPipeline {

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        do {

            if(!isDone) {

                moveIn(45, 45);
                turn(83, 15);
                moveIn(7, 15);
                robot.foundation.setFoundationMoveAngle(30);
                sleep(800);
                moveIn(-2, -15);
                turn(65, 15);
                moveIn(24, 30);
                robot.foundation.setFoundationMoveAngle(180);
                moveIn(-9, -20);
                turn(47, 15);
                moveIn(31, 30);
                turn(35, 10);
                moveIn(15, 15);
            }

            isDone = true;

        } while(opModeIsActive());

        robot.stop();

    }
    public void async() {}
}

