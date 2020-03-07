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

@Autonomous(name = "norcal blue", group = "group")
public class NORCAL_Blue extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        initVision();
        //move seek back once done with debugging
        telemetry.addData("stone pos", returnInt());
        telemetry.addData("stone x", detector.getScreenPosition().x);
        telemetry.addData("stone y", detector.getScreenPosition().y);
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            seek();
            if(!isDone) {

                //instructions go here
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(opModeIsActive()) {
                            robot.foundation.deployCapstone(false, false);
                            if (isStopRequested())
                                return;
                        }
                    }
                });
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

            telemetry.addData("pos", returnInt());
            telemetry.update();
        }

        robot.stopAll(robot.subsystems);

    }


    /**
     * case 1
     */
    void case1() {
        move(32, 50);
        turnSmall(30, 80);
        robot.drivetrain.setPowers(0, 0);
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
        robot.intake.setPowers(0);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 11, 50);
        turnSmall(90, 50);
        move(45, 40);
        robot.intake.setPowers(-1.0);
        move(-56, 40);
        robot.intake.setPowers(0);
        turnSmall(-10, 50);
        currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        currentPos2 = robot.drivetrain.getPosition();
        sleep(500);
        robot.intake.setPowers(0);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 11, 50);
        robot.drivetrain.setPowers(0, 0);
        turnSmall(90, 50);
        move(56, 40);
        robot.intake.setPowers(-1.0);
        move(-16, 50);

    }


    /**
     * case 2
     */
    void case2() {
        move(32, 50);
        turnSmall(-5, 90);
        robot.drivetrain.setPowers(0, 0);
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
        robot.intake.setPowers(0);
        sleep(500);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 11, 50);
        turnSmall(90, 50);
        move(45, 40);
        robot.intake.setPowers(-1.0);
        move(-65, 40);
        robot.intake.setPowers(0);
        turnSmall(-15, 50);
        currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        currentPos2 = robot.drivetrain.getPosition();
        sleep(500);
        robot.intake.setPowers(0);
        sleep(500);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 8, 50);
        robot.drivetrain.setPowers(0, 0);
        turnSmall(90, 50);
        move(65, 40);
        robot.intake.setPowers(-1.0);
        move(-16, 50);

    }


    /**
     * case 3
     */
    void case3() {
        move(10, 50);
        turnSmall(-25, 80);
        move(20, 50);
        turnSmall(0, 80);
        robot.drivetrain.setPowers(0, 0);
        double currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        double currentPos2 = robot.drivetrain.getPosition();
        sleep(750);
        robot.intake.setPowers(0);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 11, 50);
        turnSmall(90, 50);
        move(55, 40);
        robot.intake.setPowers(-1.0);
        move(-72, 40);
        robot.intake.setPowers(0);
        turnSmall(-10, 50);
        currentPos1 = robot.drivetrain.getPosition();
        while(robot.intake.getIRDistance(DistanceUnit.CM) > 15) {

            robot.drivetrain.setPowers(0.4, 0);
            robot.intake.setPowers(0.7);
            if(isStopRequested())
                return;
        }
        robot.drivetrain.setPowers(0, 0);
        currentPos2 = robot.drivetrain.getPosition();
        sleep(500);
        robot.intake.setPowers(0);
        move((((currentPos2 - currentPos1) * -1.0)/537.6) * ((100.0 / 25.4) * Math.PI) - 11, 50);
        robot.drivetrain.setPowers(0, 0);
        turnSmall(90, 50);
        move(72, 40);
        robot.intake.setPowers(-1.0);
        move(-16, 50);
    }

    public void async() {}
}
