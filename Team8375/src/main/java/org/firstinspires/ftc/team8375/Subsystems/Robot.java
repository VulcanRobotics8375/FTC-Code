/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class Robot {
    public Drivetrain drivetrain;
    public Arm arm;
    public Intake intake;
    public Foundation foundation;
    public SkystoneDetect SkystoneDetect;

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
                hwMap.get(Servo.class, "yaw"),
                hwMap.get(Servo.class, "level")
        );
        intake = new Intake(
                hwMap.dcMotor.get("intake_left"),
                hwMap.dcMotor.get("intake_right"),
                hwMap.get(CRServo.class, "deploy_left"),
                hwMap.get(CRServo.class, "deploy_right"),
                hwMap.get(Servo.class, "auto_arm"),
                hwMap.get(Rev2mDistanceSensor.class, "intake_sensor")
        );

        foundation = new Foundation(
                hwMap.get(Servo.class, "foundation_move"),
                hwMap.get(Servo.class, "capStone")
        );

        SkystoneDetect = new SkystoneDetect(
                hwMap.get(ColorSensor.class, "stone_detector")
        );

    }

    public void stop() {
        drivetrain.stop();
        arm.stop();
        intake.stop();
    }
}
