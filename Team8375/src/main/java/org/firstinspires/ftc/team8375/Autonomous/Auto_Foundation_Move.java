/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="foundation Move", group = "auto")
public class Auto_Foundation_Move extends VulcanPipeline {

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        do {

            if(!isDone) {
                moveIn(30, 50);

                robot.foundation.setFoundationMoveAngle(30);
                sleep(500);

                moveIn(-32, -50);

                robot.foundation.setFoundationMoveAngle(180);


            }

            isDone = true;

        } while(opModeIsActive());

        robot.stop();

    }
}

