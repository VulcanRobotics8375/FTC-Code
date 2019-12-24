/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name = "detectorTest", group = "test")
public class detectorTest extends LinearOpMode {
    protected Robot robot;
    private boolean detected;

    public void runOpMode() {
        robot = new Robot(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("hue", robot.SkystoneDetect.getHSV(0));
            telemetry.addData("saturation", robot.SkystoneDetect.getHSV(1));
            telemetry.addData("Value", robot.SkystoneDetect.getHSV(2));

            detected = robot.SkystoneDetect.detect();

            sleep(100);

            telemetry.addData("detected", detected);
            telemetry.addData("score", robot.SkystoneDetect.getScorer());

            robot.SkystoneDetect.resetScore();

            telemetry.update();
        }

    }
}
