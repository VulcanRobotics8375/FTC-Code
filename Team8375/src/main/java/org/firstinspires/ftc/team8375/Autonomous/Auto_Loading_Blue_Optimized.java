/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "loading -- blue -- optimized", group = "loading")
public class Auto_Loading_Blue_Optimized extends VulcanPipeline {

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
                        turn(185, 15);
                        moveIn(-45, -70);
                        turn(180, 13);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-22, -30);
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
                        turn(185, 10);
                        moveIn(-48, -20);
                        turn(180, 10);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-22, -20);
                        break;

                    case 3:
                        turn(185, 10);
                        moveIn(-50, -20);
                        turn(180, 10);
//                        setAutoArmPos(0.9);
                        sleepOpMode(500);
                        moveIn(-26, -20);
                        break;

                }

                isDone = true;
            }
        }

        robot.stop();

    }
    public void async() {}
}
