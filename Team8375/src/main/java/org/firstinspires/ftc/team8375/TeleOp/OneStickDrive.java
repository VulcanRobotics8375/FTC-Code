/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@Disabled
@Deprecated
@TeleOp(name="One Stick Tank", group="Drive")
public class OneStickDrive extends OpMode {
    protected Robot robot;
    private boolean firstRun;

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
        firstRun = true;
//        robot.arm.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.arm.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {

        if(firstRun) {
            robot.intake.resetDeployTime();
            firstRun = false;
        }

        robot.intake.deploy(gamepad1.dpad_left, gamepad1.dpad_right);

        robot.drivetrain.tankDrive(
                //forward
                gamepad1.right_stick_y,
                //turn
                -gamepad1.right_stick_x,
                //acceleration time
                gamepad1.right_bumper
                //head switch
        );


//        robot.arm.run(
//                //lift
//                gamepad2.left_stick_y,
//                //pitch
//                -gamepad2.right_stick_y,
//                //claw button
//                gamepad2.right_bumper,
//                //flip joystick
//                -gamepad2.right_stick_x,
//                900,
//                300,
//                2150,
//                9850,
//                500,
//                gamepad2.y
//        );

        robot.intake.run(
                //reverse
                gamepad1.a,
                //toggle
                gamepad2.right_trigger
        );

        if(gamepad2.left_bumper) {
            robot.foundation.setFoundationMoveAngle(30);
        } else {
            robot.foundation.setFoundationMoveAngle(180);
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
