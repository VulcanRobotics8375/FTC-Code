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

@Autonomous(name = "norcal blue", group = "group")
public class NORCAL_Blue extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        initVision();
        //move seek back once done with debugging
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
                        case1();
                        return;
                    case 2:
                        case2();
                        return;
                    case 3:
                        case3();
                        return;
                }

                isDone = true;
            }
        }

        robot.stopAll(robot.subsystems);

    }

    void case1() {
        move(30, 50);
        turnSmall(25, 75);
        Thread s = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(700, 0.5, isStopRequested());
            }
        });
        s.start();
        double currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        double currentPos2 = robot.drivetrain.getPosition();
        sleep(500);
        robot.intake.setPowers(0.6);
        sleepOpMode(500);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 9, 50);
        robot.drivetrain.setPowers(0.3, 0);
        sleep(100);
        robot.drivetrain.setPowers(-0.3, 0);
        sleep(150);
        robot.drivetrain.setPowers(0, 0);
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(200);
                robot.arm.runToPosition(0, 0.5, isStopRequested());
            }
        });
        d.start();
        turnSmall(90, 50);
        joinThread(s);
        robot.arm.setClawPos(parseDouble(prop, "arm.clawIn") / 180.0);
        Thread u = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(300, 0.5, isStopRequested());
            }
        });
        move(75, 40);
        joinThread(d);
        u.start();
        sleep(200);
        robot.arm.setExtendPower(-1.0);
        turnSmall(180, 50);
        move(-4, 50);
        robot.arm.setClawPos(parseDouble(prop, "arm.clawOut") / 180.0);
        robot.arm.setExtendPower(1.0);
        move(-4, 50);
        joinThread(u);
        Thread reset = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(0, 0.5, isStopRequested());
            }
        });
        reset.start();
        robot.foundation.setFoundationMoveAngle(0);
        sleep(750);
        move(29, 70);
        robot.foundation.setFoundationMoveAngle(180);
        sleep(500);
        turnSmall(90, 60);
        move(-40, 50);

    }
    void case2() {
        move(30, 50);
        Thread g = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(700, 0.5, isStopRequested());
            }
        });
        g.start();
        double currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        double currentPos2 = robot.drivetrain.getPosition();
        sleep(500);
        robot.intake.setPowers(0.6);
        sleepOpMode(500);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 9, 50);
        robot.drivetrain.setPowers(0.3, 0);
        sleep(100);
        robot.drivetrain.setPowers(-0.3, 0);
        sleep(150);
        robot.drivetrain.setPowers(0, 0);
    }
    void case3() {
        move(30, 50);
        turnSmall(-25, 75);
        Thread s = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(700, 0.5, isStopRequested());
            }
        });
        s.start();
        double currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        double currentPos2 = robot.drivetrain.getPosition();
        sleep(500);
        robot.intake.setPowers(0.6);
        sleepOpMode(500);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 9, 50);
        robot.drivetrain.setPowers(0.3, 0);
        sleep(100);
        robot.drivetrain.setPowers(-0.3, 0);
        sleep(150);
        robot.drivetrain.setPowers(0, 0);
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(200);
                robot.arm.runToPosition(0, 0.5, isStopRequested());
            }
        });
        d.start();
        turnSmall(90, 50);
        joinThread(s);
        robot.arm.setClawPos(parseDouble(prop, "arm.clawIn") / 180.0);
        Thread u = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(300, 0.5, isStopRequested());
            }
        });
        move(75, 40);
        joinThread(d);
        u.start();
        sleep(200);
        robot.arm.setExtendPower(-1.0);
        turnSmall(180, 50);
        move(-4, 50);
        robot.arm.setClawPos(parseDouble(prop, "arm.clawOut") / 180.0);
        robot.arm.setExtendPower(1.0);
        move(-4, 50);
        joinThread(u);
        Thread reset = new Thread(new Runnable() {
            @Override
            public void run() {
                robot.arm.runToPosition(0, 0.5, isStopRequested());
            }
        });
        reset.start();
        robot.foundation.setFoundationMoveAngle(0);
        sleep(750);
        move(29, 70);
        robot.foundation.setFoundationMoveAngle(180);
        sleep(500);
        turnSmall(90, 60);
        move(-40, 50);
    }

    public void async() {}
}
