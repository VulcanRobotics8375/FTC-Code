/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "testOpMode", group = "cringe")
public class helpmepls extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                armTime.reset();
                //instructions go here
//                turn(90, 70);
//                move(5, 20);
//                turn(45, 50);
                deployAutoArm();
                isDone = true;
                telemetry.addLine("Done!");
                telemetry.update();
            }
        }

        robot.stop();

    }

    public void async() {
        if(armTime.now(TimeUnit.MILLISECONDS) < 1000) {

        }
    }
}
