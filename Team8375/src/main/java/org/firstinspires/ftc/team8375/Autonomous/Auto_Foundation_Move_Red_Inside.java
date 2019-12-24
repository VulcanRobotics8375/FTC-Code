/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="foundation -- red -- inside", group = "foundation move")
public class Auto_Foundation_Move_Red_Inside extends VulcanPipeline {

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        do {

            if(!isDone) {

                moveIn(45, 45);
                turn(-83, 15);
                moveIn(7, 15);
                robot.foundation.setFoundationMoveAngle(30);
                sleep(800);
                moveIn(-2, -15);
                turn(-65, 30);
                moveIn(24, 30);
                robot.foundation.setFoundationMoveAngle(180);
                moveIn(-4, -20);
                turn(85, 15);
                moveIn(-23, -30);
                turn(-35, 10);
                moveIn(-12, -15);
            }

            isDone = true;

        } while(opModeIsActive());

        robot.stop();

    }
    public void async() {}
}

