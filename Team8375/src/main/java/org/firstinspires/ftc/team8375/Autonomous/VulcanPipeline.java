/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

public abstract class VulcanPipeline extends LinearOpMode {
    private double speed = 0;
    private double pidOut;
    private double integral = 0;
    private double derivative = 0;
    private double previousError = 0;
    private int step = 0;
    private int i;
    protected Robot robot;
    private ElapsedTime stoneTime = new ElapsedTime();

    public boolean isDone = false;

    public void initialize() {
        robot = new Robot(hardwareMap);
        robot.drivetrain.init();
        robot.drivetrain.resetEncoders(DcMotor.RunMode.RUN_USING_ENCODER);
        isDone = false;
    }

    public abstract void runOpMode();


    //turn
    public void turn(double angle, double speed) {
        this.speed = speed;
        do {
            if (Math.abs(robot.drivetrain.getError()) <= 20) {
                if(this.speed > 0) {
                    this.speed--;
                    this.speed = Range.clip(this.speed, 5, 100);
                } else if(speed < 0) {
                    this.speed++;
                    this.speed = Range.clip(this.speed, -100, -5);
                }

                sleep(7);
            }
            robot.drivetrain.turn(angle, this.speed);
            sleep(10);
            telemetry.addData("imu", robot.drivetrain.getImuAngle());
            updateTelemetry();

        } while(!robot.drivetrain.isTurnDone());
        robot.drivetrain.setPowers(0, 0);
        robot.drivetrain.setImuOffset(robot.drivetrain.getImuAngle());
        step++;
    }

    //moveIn
    public void moveIn(double inches, double speed, double turn) {
        robot.drivetrain.moveIn(inches, speed, turn);
        step++;
    }
    public void moveIn(double inches, double speed) {
        moveIn(inches, speed, 0);

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
        updateTelemetry();
    }

    public void findSkystone(double threshold, double power) {
        stoneTime.reset();
        robot.SkystoneDetect.resetScore();

        while (!robot.SkystoneDetect.detect()) {
            robot.SkystoneDetect.setScorerThreshold(threshold);
            robot.drivetrain.setPowers(power, 0);
            robot.SkystoneDetect.resetScore();
            sleep(100);
            telemetry.addData("score", robot.SkystoneDetect.getScorer());

        }
        robot.drivetrain.setPowers(0, 0);
        step++;

        if(stoneTime.milliseconds() <= 2000) {
            i = 1;
        } else if(stoneTime.milliseconds() >= 2000 && stoneTime.milliseconds() < 4000) {
            i = 2;
        } else if(stoneTime.milliseconds() > 4000) {
            i = 3;
        }


    }

    public void setAutoArmPos(double pos){
        while(robot.intake.getAutoArm() != pos) {
            robot.intake.autoArm(pos);
        }
    }

    public void sleepOpMode(long millis) {
        sleep(millis);
    }

    public int returnInt() {
        return i;
    }

    public void updateTelemetry() {

        telemetry.addData("step", step);

        telemetry.addData("Runtime", getRuntime());

        telemetry.update();

    }

}
