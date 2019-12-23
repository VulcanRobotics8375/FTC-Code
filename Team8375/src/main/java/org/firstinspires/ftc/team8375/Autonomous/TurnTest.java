/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

@Autonomous(name = "turn test", group = "test")
public class TurnTest extends VulcanPipeline {


    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);

        initialize();

        waitForStart();
        while(opModeIsActive()) {

            robot.drivetrain.percentSteer(100, 10);
            telemetry.addData("angle", robot.drivetrain.getImuAngle());
            telemetry.update();
        }

        robot.stop();

    }
    public void async() {}
}
