package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="PIDTele", group="Test")
public class PIDTest extends LinearOpMode {
    protected Robot robot;
    double output;
    long iterationTime = 10;

    public void runOpMode() {
        robot = new Robot(hardwareMap);
        robot.drivetrain.setupIMU();
        robot.pid.getCalibrationStatus();
        waitForStart();

        do {
            robot.drivetrain.PID(0.01, 0.001, 0.01, iterationTime, 0);
            output = robot.drivetrain.getOutput();
            robot.drivetrain.setPowers(gamepad1.left_stick_y, -output);
            telemetry.addData("output", robot.drivetrain.getOutput());
            telemetry.addData("angle", robot.drivetrain.getRobotAngle());
            telemetry.update();
            sleep(iterationTime);
        } while(opModeIsActive());

        robot.drivetrain.stop();

    }

}
