/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="TankDrive", group="Drive")
public class TankDrive extends OpMode {
    protected Robot robot;
    private ElapsedTime runtime = new ElapsedTime();
    private boolean startDone = false;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.arm.ArmMotorInit(0);
    }

//    @Override
//    public void init_loop() {
//
//    }

    @Override
    public void start() {
        robot.drivetrain.setupIMU();
//        robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        runtime.reset();
        robot.intake.deploy(1);

    }

    public void loop() {


        robot.drivetrain.tankDrive(
                //turn
                -gamepad1.right_stick_x,
                //forward
                gamepad1.left_stick_y,
                //acceleration time
                0.5,
                0.1,
                //head switch
                gamepad1.left_bumper
        );

        robot.arm.run(
                //lift
                gamepad2.left_stick_y,
                //pitch
                gamepad2.right_stick_y,
                //claw button
                gamepad2.right_bumper,
                //flip clockwise
                gamepad2.right_trigger,
                //flip counter clockwise
                gamepad2.left_trigger,
                900,
                300,
                2150,
                9850,
                500
        );

        robot.intake.run(
                -0.5,
                //reverse
                gamepad2.a,
                //toggle
                gamepad1.dpad_left
        );

        //Telemetry

        //Drivetrain
        telemetry.addData("front Left", robot.drivetrain.getPositionFl());
        telemetry.addData("front Right", robot.drivetrain.getPositionFr());
        telemetry.addData("back Left", robot.drivetrain.getPositionBl());
        telemetry.addData("back Right", robot.drivetrain.getPositionBr());
        telemetry.addData("liftPower", robot.arm.getLiftPower());

        //Arm
        telemetry.addData("lift", robot.arm.getLiftPos());
        telemetry.addData("claw", robot.arm.getClawPos());
        telemetry.addData("pitch", robot.arm.getPitchPos());
        telemetry.addData("level", robot.arm.getLevelPos());

        //Intake
        telemetry.addData("deployLeft", robot.intake.getDeployLeftPos());
        telemetry.addData("deployRight", robot.intake.getDeployRightPos());

        telemetry.addData("Runtime", runtime.time(TimeUnit.SECONDS));

        telemetry.update();
    }

    @Override
    public void stop() {
        robot.stop();
    }

}
