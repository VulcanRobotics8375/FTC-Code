/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
    public AutoArm autoArm;

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
                hwMap.get(Rev2mDistanceSensor.class, "intake_sensor")
        );

        autoArm = new AutoArm(
                hwMap.get(Servo.class, "auto_flip"),
                hwMap.get(Servo.class, "auto_claw"),
                hwMap.get(CRServo.class, "auto_lift")
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
