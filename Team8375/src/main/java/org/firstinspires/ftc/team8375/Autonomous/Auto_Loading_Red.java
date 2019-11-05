/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "yeet", group = "yeet")
public class Auto_Loading_Red extends VulcanPipeline {

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(18, 50);
                turn(90, 15);

                findSkystone(12.5);

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
