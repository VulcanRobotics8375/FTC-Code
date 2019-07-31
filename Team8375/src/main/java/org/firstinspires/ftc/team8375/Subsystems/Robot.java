/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team8375.TeleOp.MecanumDrive;

public class Robot {
    public Drivetrain drivetrain;
    public Arm arm;


    public Robot(HardwareMap hwMap) {
        drivetrain = new Drivetrain(
                hwMap.dcMotor.get("front_left"),
                hwMap.dcMotor.get("front_right"),
                hwMap.dcMotor.get("back_left"),
                hwMap.dcMotor.get("back_right"),
                hwMap.get(BNO055IMU.class, "imu")
        );

        arm = new Arm(
                0,
                hwMap.dcMotor.get("lift"),
                hwMap.dcMotor.get("extend_left"),
                hwMap.dcMotor.get("extend_right"),
                hwMap.crservo.get("intake"),
                hwMap.servo.get("flip")
        );

    }

    public void stop() {
        drivetrain.stop();
    }
}
