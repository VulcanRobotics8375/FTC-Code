/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "pidTest", group = "cringe")
public class helpmepls extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                //instructions go here
                turn(90, 80);
                move(5, 20);
                turn(45, 50);
                isDone = true;
                telemetry.addLine("Done!");
                telemetry.update();
            }
        }

        robot.stop();

    }
}
