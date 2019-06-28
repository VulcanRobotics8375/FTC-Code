package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="PIDTele", group="Test")
public class PIDTest extends OpMode {
    protected Robot robot;
    double output;

    public void init() {
        robot = new Robot(hardwareMap);
        robot.drivetrain.setupIMU();
    }

    public void loop() {
        robot.drivetrain.PID(0.0005, 0, 0, 10, 0);
        output = robot.drivetrain.getOutput();
        robot.drivetrain.setPowers(gamepad1.left_stick_y, -output);

    }

    public void stop() {
        robot.drivetrain.stop();
    }

}
