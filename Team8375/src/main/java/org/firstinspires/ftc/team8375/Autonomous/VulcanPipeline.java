/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

public abstract class VulcanPipeline extends LinearOpMode {
    private double accSpeed;
    protected Robot robot;

    public abstract void runOpMode();


    //turn
    public void turn(double angle, double speed) {
        accSpeed = speed;
        do {
            robot.drivetrain.turn(angle, speed, accSpeed);
            if (robot.drivetrain.getError() <= 15) {
                accSpeed--;
                accSpeed = Range.clip(accSpeed, 10, 100);
                sleep(7);
            }
        } while(!robot.drivetrain.isTurnDone());
    }

    //moveIn
    public void moveIn(double inches, double speed, double turn) {
        robot.drivetrain.moveIn(inches, speed, turn);
    }
    public void moveIn(double inches, double speed) {
        robot.drivetrain.moveIn(inches, speed);
    }

}
