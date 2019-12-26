/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
        armTime.reset();
        while(opModeIsActive()) {
            if(!isDone) {

                armTime.reset();
                //instructions go here

                move(18, 30);
                turn(-90, 80);

//                turn(90, 70);
                move(5, 5);
//                turn(45, 50);

                isDone = true;
                telemetry.addLine("Done!");
                telemetry.update();
            }
        }

        robot.stop();

    }

    public void async() {
        if(armTime.now(TimeUnit.MILLISECONDS) > 4000 && armTime.now(TimeUnit.MILLISECONDS) < 8200) {
            deployAutoArm();
        }
        if(armTime.now(TimeUnit.MILLISECONDS) > 9000 && armTime.now(TimeUnit.MILLISECONDS) < 13200) {
            releaseAutoArm();
        }
    }
}
