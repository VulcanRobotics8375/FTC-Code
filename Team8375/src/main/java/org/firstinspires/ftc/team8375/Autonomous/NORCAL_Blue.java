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

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import static org.firstinspires.ftc.team8375.dataParser.*;

/**
*   Sample Autonomous program:
*   Copy this opMode, rename it and remove the @Disabled tag
*   and everything should work.
 *   @see VulcanPipeline
*/

@Autonomous(name = "norcal red", group = "group")
public class NORCAL_Blue extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        initVision();
        seek();
        telemetry.addData("stone pos", returnInt());
        telemetry.addData("stone x", detector.getScreenPosition().x);
        telemetry.addData("stone y", detector.getScreenPosition().y);
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                //instructions go here

                switch (returnInt()) {
                    case 1:
                        move(31, 50);
                        turnSmall(23, 72);
                        Thread s = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                robot.arm.runToPosition(400, isStopRequested());
                            }
                        });
                        s.start();
                        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

                            robot.drivetrain.setPowers(0.3, 0);
                            robot.intake.run(false, 1);
                            if(isStopRequested())
                                return;
                        }
                        robot.drivetrain.setPowers(0, 0);
                        robot.intake.setPowers(parseDouble(prop, "intake.minPower"));
                        sleepOpMode(500);
                        move(-15, 50);
                        turnSmall(90, 50);
                        try {
                            s.join();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        Thread d = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                robot.arm.runToPosition(0, isStopRequested());
                            }
                        });
                        d.start();

                        return;
                }

                isDone = true;
            }
        }

        robot.stopAll(robot.subsystems);

    }
    public void async() {}
}
