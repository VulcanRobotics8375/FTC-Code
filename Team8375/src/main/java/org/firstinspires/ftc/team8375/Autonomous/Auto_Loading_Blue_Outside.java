/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "loading -- blue -- outside", group = "loading")
public class Auto_Loading_Blue_Outside extends VulcanPipeline {

    private int i;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(26.4, 40);
                turn(83, 15);
                moveIn(-2, -15);

                findSkystone(20, -0.1);

                i = returnInt();
//                moveIn(2.3, 15);
//                robot.drivetrain.percentSteer(-50, -10);
//                sleepOpMode(1000);
//                setAutoArmPos(0.15);
                sleepOpMode(400);

                switch (i) {
                    case 1:
                        turn(90, 10);
                        moveIn(22, 20);
                        turn(-82, 10);
                        moveIn(45, 30);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-25, -20);
                        break;

                    case 2:
                        turn(90, 10);
                        moveIn(22, 20);
                        turn(-82, 10);
                        moveIn(50, 30);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-25, -20);
                        break;
                    case 3:
                        turn(90, 10);
                        moveIn(22, 20);
                        turn(-82, 10);
                        moveIn(60, 30);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-25, -20);
                        break;

                }

                isDone = true;
            }
        }

        robot.stop();

    }
    public void async() {}
}
