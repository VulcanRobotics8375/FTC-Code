/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "loading -- red -- outside", group = "loading")
public class Auto_Loading_Red_Outside extends VulcanPipeline {

    private int i;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(26.8, 40);
                turn(83, 15);
                moveIn(10, 15);
//                moveIn(2, 15);

                findSkystone(20, 0.1);

                i = returnInt();
                moveIn(4, 15);
                robot.drivetrain.percentSteer(-50, -18);
                setAutoArmPos(0.15);
                sleepOpMode(900);
                robot.drivetrain.setPowers(0, 0);
                setAutoArmPos(0.15);
                sleepOpMode(400);
                moveIn(2, 20);

                switch (i) {
                    case 1:
                        turn(85, 10);
                        moveIn(22, 20);
                        turn(-82, 10);
                        moveIn(45, 30);
                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-25, -20);
                        break;

                    case 2:
                        turn(85, 10);
                        moveIn(22, 20);
                        turn(-82, 10);
                        moveIn(50, 30);
                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-25, -20);
                        break;
                    case 3:
                        turn(85, 10);
                        moveIn(22, 20);
                        turn(-82, 10);
                        moveIn(60, 30);
                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-25, -20);
                        break;

                }

                isDone = true;
            }
        }

        robot.stop();

    }
}
