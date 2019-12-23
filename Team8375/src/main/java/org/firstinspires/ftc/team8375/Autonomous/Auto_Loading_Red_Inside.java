/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "loading -- red -- inside", group = "loading")
public class Auto_Loading_Red_Inside extends VulcanPipeline {

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

                findSkystone(20, 0.1);

                i = returnInt();
                moveIn(3, 15);
//                robot.drivetrain.percentSteer(-50, -18);
//                setAutoArmPos(0.15);
//                sleepOpMode(900);
//                robot.drivetrain.setPowers(0, 0);
//                setAutoArmPos(0.15);
                sleepOpMode(400);
//                moveIn(2, 20);
                switch (i) {
                    case 1:
                        turn(-180, 15);
                        moveIn(45, 70);
                        turn(-180, 13);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(22, 30);
//                        setAutoArmPos(0.15);
//                        sleepOpMode(400);
//                        turn(180, 15);
//                        moveIn(-75, -65);
//                        turn(180, 25);
//                        setAutoArmPos(0.9);
//                        sleepOpMode(500);
//                        moveIn(-22, -40);
                        break;

                    case 2:
                        turn(-180, 10);
                        moveIn( 50, 50);
                        turn(-180, 10);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(22, 20);
                        break;

                    case 3:
                        turn(-180, 10);
                        moveIn(52, 50);
                        turn(-180, 10);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(26, 20);
                        break;

                }

                isDone = true;
            }
        }

        robot.stop();

    }
    public void async() {}
}
