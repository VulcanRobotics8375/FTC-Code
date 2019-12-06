/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@TeleOp(name="TankDrive", group="Drive")
public class TankDrive extends OpMode {
    protected Robot robot;
    private boolean buttonPressed;
    private int inverse = 1;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.arm.ArmMotorInit(0);
        robot.drivetrain.init();
    }

//    @Override
//    public void init_loop() {
//
//    }

    @Override
    public void start() {
//        robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {

        robot.intake.deploy(gamepad1.dpad_left, gamepad1.dpad_right);

        robot.drivetrain.tankDrive(
                //forward
                gamepad1.left_stick_y * inverse,
                //turn
                -gamepad1.right_stick_x,
                //acceleration time
                0.5,
                //slow mode
                gamepad1.right_bumper,
                //head switch
                gamepad1.left_bumper
        );


        robot.arm.run(
                //lift
                -gamepad2.left_stick_y,
                //pitch
                -0.5 * gamepad2.right_stick_y,
                //claw button
                gamepad2.right_bumper,
                //flip button
                gamepad2.b,
                600,
                3750,
                -800,
                870,
                500,
                gamepad2.y,
                gamepad2.x,
                gamepad2.dpad_up,
                gamepad2.dpad_down,
                gamepad2.right_stick_x
        );

        robot.intake.run(
                -1,
                //reverse
                gamepad1.a,
                //toggle
                gamepad2.a
        );

        if(gamepad2.left_bumper) {
            robot.foundation.setFoundationMoveAngle(30);
        } else {
            robot.foundation.setFoundationMoveAngle(180);
        }

        if(gamepad1.left_bumper) {
            buttonPressed = true;
        }
        else if(buttonPressed) {
            inverse *= -1;
            buttonPressed = false;
        }
        if(inverse > 0) {
            robot.intake.autoArm(0.85);
        } else if(inverse < 0) {
            robot.intake.autoArm(0.7);
        }

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
        telemetry.addData("resetStep", robot.arm.getResetStep());
        telemetry.addData("resetIsDone", robot.arm.isResetDone());

        //Intake
        telemetry.addData("deployLeft", robot.intake.getDeployLeftPos());
        telemetry.addData("deployRight", robot.intake.getDeployRightPos());

        telemetry.addData("Runtime", getRuntime());

        telemetry.update();
    }

    @Override
    public void stop() {
        robot.stop();
    }

}
