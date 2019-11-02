/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class Robot {
    public Drivetrain drivetrain;
    public Arm arm;
    public Intake intake;
    public Foundation foundation;
    public stoneDetect stoneDetect;

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
                hwMap.get(Servo.class, "claw"),
                hwMap.get(CRServo.class, "yaw"),
                hwMap.get(Servo.class, "level")
        );

        intake = new Intake(
                hwMap.dcMotor.get("intake_left"),
                hwMap.dcMotor.get("intake_right"),
                hwMap.get(CRServo.class, "deploy_left"),
                hwMap.get(CRServo.class, "deploy_right")
        );

        foundation = new Foundation(hwMap.get(Servo.class, "foundation_move"));

        stoneDetect = new stoneDetect(
                hwMap.get(ColorSensor.class, "detector")
        );

    }

    public void init() { }

    public void stop() {
        drivetrain.stopDriveTrain();
    }
}
