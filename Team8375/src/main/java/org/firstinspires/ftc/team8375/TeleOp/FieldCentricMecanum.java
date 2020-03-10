package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team8375.Robot.MecanumBase;

public class FieldCentricMecanum extends OpMode {
    public MecanumBase robot;


    public void init() {
        robot = new MecanumBase(hardwareMap);
        robot.drivetrain.init();

    }

    public void start() {}

    public void loop() {
        robot.drivetrain.fieldCentricMecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

    }
}
