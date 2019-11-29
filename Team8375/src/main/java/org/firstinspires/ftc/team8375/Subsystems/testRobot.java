/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class testRobot {
    public Drivetrain drivetrain;

    public testRobot(HardwareMap hwMap) {
        drivetrain = new Drivetrain(
                hwMap.dcMotor.get("front_left"),
                hwMap.dcMotor.get("front_right"),
                hwMap.dcMotor.get("back_left"),
                hwMap.dcMotor.get("back_right"),
                hwMap.get(BNO055IMU.class, "imu")
        );
    }

    public void stop() {
        drivetrain.stop();
    }
}
