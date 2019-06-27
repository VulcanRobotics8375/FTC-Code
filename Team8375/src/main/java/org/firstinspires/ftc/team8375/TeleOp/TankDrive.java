package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team8375.Subsystems.Arm;
import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="TankDrive", group="Drive")
public class TankDrive extends OpMode {
    protected Robot robot;
    public void init() {
        robot = new Robot(hardwareMap);
        telemetry.addData("Encoder Value", robot.arm.lift.getCurrentPosition());
        gamepad1.setJoystickDeadzone(0.075f);
        gamepad2.setJoystickDeadzone(0.075f);
    }

    public void start() {
        robot.drivetrain.setupIMU();
        robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drivetrain.Time.reset();
    }

    public void loop() {
        robot.drivetrain.tankDrive(gamepad1.left_stick_y, -gamepad1.right_stick_x, 0.2);
        robot.arm.setPowers(gamepad2.left_stick_y, -gamepad2.right_stick_y, gamepad2.left_trigger, gamepad2.a, 500, 2200, 400.0);
        telemetry.addData("Arm Position", robot.arm.lift.getCurrentPosition());
        telemetry.addData("Time", robot.drivetrain.Time.time());
    }

    public void stop() {
        robot.stop();
    }
}
