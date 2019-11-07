/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "loading -- blue", group = "loading")
public class Auto_Loading_Blue extends VulcanPipeline {

    private int i;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(26, 40);
                turn(83, 15);
                moveIn(-5, -15);

                findSkystone(12.5, -0.1);

                i = returnInt();
                moveIn(-1.2, -15);
                robot.drivetrain.percentSteer(-50, 10);
                sleepOpMode(1000);
                setAutoArmPos(0.15);

                switch (i) {
                    case 1:
                        turn(35, 10);
                        moveIn(25, 20);
                        turn(-35, 15);
                        moveIn(35, 30);
                        setAutoArmPos(0.9);
                        moveIn(-27, -20);
                        break;

                    case 2:
                        turn(20, 10);
                        moveIn(40, 20);
                        turn(-20, 15);
                        moveIn(35, 30);
                        setAutoArmPos(0.9);
                        moveIn(-27, -20);
                        break;

                    case 3:
                        turn(15, 10);
                        moveIn(44, 20);
                        turn(-15, 15);
                        moveIn(37, 30);
                        setAutoArmPos(0.9);
                        moveIn(-26, -20);
                        break;

                }

                isDone = true;
            }
        }

        robot.stop();

    }
}
