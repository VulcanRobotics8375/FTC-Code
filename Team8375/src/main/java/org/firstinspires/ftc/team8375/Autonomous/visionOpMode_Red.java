/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "vision-red", group = "test")
public class visionOpMode_Red extends VulcanPipeline {
    private boolean started = false;

    @Override
    public void runOpMode() {
        initialize();
        initVision();
        sleepOpMode(2000);
        seek();
        telemetry.addData("stonePos", returnInt());
        telemetry.addData("stone x", detector.getScreenPosition().x);
        telemetry.update();
        waitForStart();
        phoneCam.stopStreaming();
        while(opModeIsActive()) {
            if(!isDone) {
                robot.autoArm.setFlipPos(50);
                turn(-10, 100);
                move(19, 50);
                turn(90 - 12, 50);
                switch(returnInt()) {
                    case 3: {
//                        autoArmThread.start();
                        Thread f = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(250);
                                deployAutoArm();
                            }
                        });
                        f.start();
                        move(-9, 100);
                        while (!autoArmDone) {

                        }
                        autoArmDone = false;
                        f.interrupt();
                        sleep(600);
                        move(-52, 45);
                        releaseArm.start();
                        turnSmall(78, 100);
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
                        move(76, 45);
                        while (!autoArmDone) {}
                        autoArmDone = false;
                        t.interrupt();
                        sleep(600);
                        move(-67, 45);
                        releaseArm.interrupt();
                        releaseArm.run();
                        while(!autoArmDone) {}
                        move(18, 50);
                        return;
                    }
                    case 2: {
                        Thread r = new Thread(new Runnable() {
                            @Override
                            public void run() {
//                                sleep(200);
                                deployAutoArm();
                            }
                        });
                        r.start();
                        move(-1.5, 100);
                        while (!autoArmDone) {

                        }
                        autoArmDone = false;
                        r.interrupt();
                        move(-58, 50);
                        releaseArm.start();
                        turnSmall(78, 100);
                        while(!autoArmDone) {}
                        autoArmDone = false;
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(3500);
                                deployAutoArm();
                            }
                        });
                        t.start();
                        move(81, 40);
                        while (!autoArmDone) {
                            telemetry.addData("done", autoArmDone);
                            telemetry.update();
                        }
                        autoArmDone = false;
//                        t.interrupt();
                        move(-71, 50);
                        releaseArm.interrupt();
                        releaseArm.run();
                        while(!autoArmDone) {}
                        move(15, 50);
                        return;

                    }
                    case 1: {
                        deployArm.start();
                        move(8, 70);
                        while (!autoArmDone) {

                        }
                        autoArmDone = false;
                        deployArm.interrupt();
                        move(-68, 50);
                        releaseArm.start();
                        turnSmall(78, 100);
                        while(!autoArmDone) {}
                        autoArmDone = false;
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(2900);
                                deployAutoArm();
                            }
                        });
                        t.start();
                        move(88, 50);
                        while (!autoArmDone) {}
                        autoArmDone = false;
                        t.interrupt();
                        sleep(500);
                        move(-79, 50);
                        releaseArm.interrupt();
                        releaseArm.run();
                        while(!autoArmDone) {}
                        move(18, 50);
                        return;

                    }
                    default: {

                    }

                }

                //foundation code

                isDone = true;
            }
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
