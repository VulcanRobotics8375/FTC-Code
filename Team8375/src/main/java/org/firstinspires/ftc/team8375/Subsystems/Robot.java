/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;

public class Robot {
    public Drivetrain drivetrain;
    public Arm arm;
    public Intake intake;

    public Robot(HardwareMap hwMap) {
        drivetrain = new Drivetrain(
                hwMap.dcMotor.get("front_left"),
                hwMap.dcMotor.get("front_right"),
                hwMap.dcMotor.get("back_left"),
                hwMap.dcMotor.get("back_right"),
                hwMap.get(BNO055IMU.class, "imu")
        );

        arm = new Arm(
                hwMap.dcMotor.get("lift"),
                hwMap.dcMotor.get("pitch"),
                hwMap.servo.get("claw"),
                hwMap.servo.get("yaw"),
                hwMap.servo.get("level")
        );

        intake = new Intake(
                hwMap.dcMotor.get("intake_left"),
                hwMap.dcMotor.get("intake_right"),
                hwMap.crservo.get("deploy_left"),
                hwMap.crservo.get("deploy_right"),
                hwMap.servo.get("lock_left"),
                hwMap.servo.get("lock_right")
        );

    }

    public void init() { }

    public void stop() {
        drivetrain.stopDriveTrain();
    }
}
