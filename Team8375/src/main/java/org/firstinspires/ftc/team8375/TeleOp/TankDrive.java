/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.team8375.Subsystems.Arm;
import org.firstinspires.ftc.team8375.Subsystems.Drivetrain;
import org.firstinspires.ftc.team8375.Subsystems.Robot;
import org.firstinspires.ftc.team8375.Subsystems.Subsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@TeleOp(name="TankDrive", group="Drive")
public class TankDrive extends OpMode {
    protected Robot robot;
    private boolean buttonPressed;
    private int inverse = 1;
    private Properties prop;
    private List<Subsystem> subsystems = new ArrayList<>();
    private Arm arm = new Arm();

    @Override
    public void init() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("config.properties");
            if(input != null) {
                telemetry.addLine("inputstream loaded");
                prop = new Properties();
                prop.load(input);
                telemetry.update();
            } else {

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        subsystems.add(arm);
        robot = new Robot(hardwareMap);
        telemetry.addLine("robot loaded");
        arm.ArmMotorInit(0);
        robot.drivetrain.init();
        telemetry.update();

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
                //slow mode
                gamepad1.right_bumper
                //head switch
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
                //pitch reset
                gamepad2.y,
                //reset button
                gamepad2.x,
                //claw tilt up
                gamepad2.dpad_up,
                //claw tilt down
                gamepad2.dpad_down,
                //flip give
                gamepad2.right_stick_x
        );

        robot.intake.run(
                //reverse
                gamepad2.a,
                //toggle
                gamepad2.right_trigger
        );

        if(gamepad2.left_bumper) {
            robot.foundation.setFoundationMoveAngle(Double.parseDouble(prop.getProperty("foundation.deployed")));
        } else {
            robot.foundation.setFoundationMoveAngle(Double.parseDouble(prop.getProperty("foundation.retracted")));
        }

        robot.foundation.deployCapstone(gamepad1.b);

        if(gamepad1.left_bumper && !buttonPressed) {
            inverse *= -1;
            buttonPressed = true;
        }
        else if(buttonPressed && !gamepad1.left_bumper) {
            buttonPressed = false;
        }
        if(inverse > 0) {
//            robot.intake.autoArm(0.85);
        } else if(inverse < 0) {
//            robot.intake.autoArm(0.7);
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
        telemetry.addData("intake_sensor", robot.intake.getIRDistance(DistanceUnit.CM));

        telemetry.addData("dataStream test", prop.getProperty("arm.theta"));
        telemetry.addData("Runtime", getRuntime());

        telemetry.update();
    }

    @Override
    public void stop() {
//        robot.stop();
    }

}
