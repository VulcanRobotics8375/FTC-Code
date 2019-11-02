/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@Autonomous(name="autoTest", group = "test")
public class AutoPathTest extends LinearOpMode {

    private boolean isDone = false;
    private int event = 1;
    private double accSpeed = 0;
    private double pidOut;
    private double integral = 0;
    private double derivative = 0;
    private double previousError = 0;
    protected Robot robot;
    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);

        robot.drivetrain.resetEncoders(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drivetrain.setupIMU();
        telemetry.update();
        waitForStart();

        do {

            robot.drivetrain.moveIn(5, 50);


            telemetry.addData("fl", robot.drivetrain.getPositionFl());
            telemetry.addData("fr", robot.drivetrain.getPositionFr());
            telemetry.addData("bl", robot.drivetrain.getPositionBl());
            telemetry.addData("br", robot.drivetrain.getPositionBr());


            telemetry.update();

        } while(opModeIsActive());

        robot.drivetrain.stopDriveTrain();

    }

    private void pid(double Kp, double Ki, double Kd, long iterationTime, double heading) {
        double sensorVal = robot.drivetrain.pid.getIntegratedHeading() + robot.drivetrain.pid.initHeading();

        double error = sensorVal - heading;
            integral += ((error + previousError) / 2.0) * (iterationTime / 100.0);
            integral = Range.clip(integral, -1, 1);
            derivative = (error - previousError);
            pidOut = Kp * error + Ki * integral + Kd * derivative;
            previousError = error;

        pidOut = Range.clip(pidOut, -1.0, 1.0);
        sleep(iterationTime);
    }


    private void turn(double angle, double speed) {
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
}

