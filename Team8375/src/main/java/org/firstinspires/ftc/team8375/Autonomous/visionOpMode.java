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

/*
*   Sample Autonomous program:
*   Copy this opMode, rename it and remove the @Disabled tag
*   and everything should work.
*/

@Autonomous(name = "vision", group = "test")
public class visionOpMode extends VulcanPipeline {
    private boolean started = false;

    @Override
    public void runOpMode() {
        initialize();
        initVision();
        seek();
        waitForStart();
        phoneCam.stopStreaming();
        while(opModeIsActive()) {
            if(!isDone) {
                moveIn(21, 30);
                turn(90, 50);
                deployAutoArm();
                switch(returnInt()) {
                    case 1: {

                    }
                    case 2: {

                    }
                    case 3: {

                    }
                    default: {

                    }

                }

                isDone = true;
            }
            telemetry.update();
        }

        robot.stop();

    }
    public void async() {
        if(step > 0 && !started) {
            deployAutoArm();
            started = true;
        }
    }
}
