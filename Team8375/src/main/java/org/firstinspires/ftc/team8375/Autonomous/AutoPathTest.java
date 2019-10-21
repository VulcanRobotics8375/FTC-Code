/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
        robot.drivetrain.pid.initIMU();
        telemetry.addData("calibration status", robot.drivetrain.pid.getCalibrationStatus().toString());
        telemetry.update();
        waitForStart();

        do {
//            robot.drivetrain.pid.initHeading();
//            robot.drivetrain.turn(0.32, 0, 0, 90);
            switch (event){
                case 1:
                    robot.drivetrain.moveIn(20, 40, 0);
                    isDone = true;
                    event++;
                    break;
                case 2:

            }

            telemetry.addData("fl", robot.drivetrain.getPositionFl());
            telemetry.addData("fr", robot.drivetrain.getPositionFr());
            telemetry.addData("bl", robot.drivetrain.getPositionBl());
            telemetry.addData("br", robot.drivetrain.getPositionBr());


            telemetry.update();

        } while(opModeIsActive());

        robot.drivetrain.stopDriveTrain();

    }

    private void pid(double Kp, double Kd, double Ki, double power, long iterationTime, double heading) {
        double sensorVal = robot.drivetrain.pid.getIntegratedHeading() + robot.drivetrain.pid.initHeading();

        double error = sensorVal - heading;
        if(error < 5 && error > -5) {
            integral = 0;
            pidOut = 0;
        } else {
            integral += ((error + previousError) / 2.0) * (iterationTime / 100.0);
            derivative = (error - previousError);
            pidOut = Kp * error + Ki * integral + Kd * derivative;
            previousError = error;
        }

        pidOut = (0.02 * power)/100;
        sleep(iterationTime);
    }


    private void turn(double angle, double speed) {
        accSpeed = speed;
        do {
            robot.drivetrain.turn(angle, speed, accSpeed);
            if (robot.drivetrain.getError() <= 15) {
                accSpeed--;
                sleep(7);
            }
        } while(!robot.drivetrain.isTurnDone());
    }
}

