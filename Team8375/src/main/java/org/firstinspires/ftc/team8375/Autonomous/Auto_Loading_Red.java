/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Deprecated
@Disabled
@Autonomous(name = "loading -- red", group = "loading")
public class Auto_Loading_Red extends VulcanPipeline {

    private int i;

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        while(opModeIsActive()) {
            if(!isDone) {

                moveIn(26.5, 40);
                turn(79, 15);

                findSkystone(12.5, 0.1);

                i = returnInt();
                moveIn(1.2, 15);
                robot.drivetrain.percentSteer(50, 10);
                sleepOpMode(1000);
//                setAutoArmPos(0.15);


                switch (i) {
                    case 1:
                        turn(-35, 10);
                        moveIn(-25, -20);
                        turn(35, 15);
                        moveIn(-40, -30);
//                        setAutoArmPos(0.9);
                        moveIn(24, 20);
                        break;

                    case 2:
                        turn(-20, 10);
                        moveIn(-40, -20);
                        turn(20, 15);
                        moveIn(-35, -30);
//                        setAutoArmPos(0.9);
                        moveIn(27, 20);
                        break;

                    case 3:
                        turn(-15, 10);
                        moveIn(-44, -20);
                        turn(15, 15);
                        moveIn(-37, -30);
//                        setAutoArmPos(0.9);
                        moveIn(26, 20);
                        break;

                        default:
                            break;
                }

                isDone = true;
            }


        }

    }

    public void async() {}

}
