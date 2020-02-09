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
        telemetry.addData("stonePos", returnInt());
        telemetry.addData("stone x", detector.getScreenPosition().x);
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            seek();
            sleep(500);
            if(!isDone) {
                robot.autoArm.setFlipPos(50);
                move(20, 50);
                turn(90, 50);
                switch(returnInt()) {
                    case 1: {
//                        autoArmThread.start();
                        deployArm.start();
                        move(2, 100);
                        while (!autoArmDone) {
                            if(isStopRequested())
                                return;
                        }
                        autoArmDone = false;
                        deployArm.interrupt();
                        move(52, 50);
                        releaseArm.start();
                        turnSmall(90, 100);
                        while(!autoArmDone) {}
                        autoArmDone = false;
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(2800);
                                deployAutoArm();
                            }
                        });
                        t.start();
                        move(-76, 50);
                        while (!autoArmDone) {
                            if(isStopRequested())
                                return;
                        }
                        autoArmDone = false;
                        t.interrupt();
                        sleep(500);
                        move(67, 50);
                        releaseArm.interrupt();
                        releaseArm.run();
                        while(!autoArmDone) {
                            if(isStopRequested())
                                return;
                        }
                        move(-18, 50);
                        return;
                    }
                    case 2: {
                        Thread r = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(200);
                                deployAutoArm();
                            }
                        });
                        r.start();
                        move(-6, 70);
                        while (!autoArmDone) {

                        }
                        autoArmDone = false;
                        r.interrupt();
                        move(58, 50);
                        releaseArm.start();
                        turnSmall(90, 100);
                        while(!autoArmDone) {
                            if(isStopRequested())
                                return;
                        }
                        autoArmDone = false;
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(3300);
                                deployAutoArm();
                            }
                        });
                        t.start();
                        move(-81, 40);
                        while (!autoArmDone) {
                            telemetry.addData("done", autoArmDone);
                            telemetry.update();
                            if(isStopRequested())
                                return;
                        }
                        autoArmDone = false;
//                        t.interrupt();
                        move(71, 50);
                        releaseArm.interrupt();
                        releaseArm.run();
                        while(!autoArmDone) {}
                        move(-18, 50);
                        return;

                    }
                    case 3: {
                        Thread r = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(400);
                                deployAutoArm();
                            }
                        });
                        r.start();
                        move(-15, 70);
                        while (!autoArmDone) {

                        }
                        autoArmDone = false;
                        r.interrupt();
                        move(66, 50);
                        releaseArm.start();
                        turnSmall(90, 100);
                        while(!autoArmDone) {}
                        autoArmDone = false;
                        releaseArm.interrupt();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(1500);
                                deployAutoArm();
                            }
                        });
                        t.start();
                        move(-50, 50);
                        while(!autoArmDone){
                            if(isStopRequested())
                                return;
                        }
                        autoArmDone = false;
                        t.interrupt();
                        move(42, 50);
                        releaseArm.run();
                        while(!autoArmDone) {}
                        autoArmDone = false;
                        releaseArm.interrupt();
                        move(-10, 50);

                        return;

                    }
                    default: {

                    }

                }

                //foundation code
                isDone = true;
            }
            if(isStopRequested())
                return;
            telemetry.update();
        }

        robot.stop();

    }
    public void async() {
//        if(step > 0 && !started) {
//            deployAutoArm();
//            started = true;
//        }
    }
}
