/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/*
*   Sample Autonomous program:
*   Copy this opMode, rename it and remove the @Disabled tag
*   and everything should work.
*/

@Disabled
@Autonomous(name = "foundation move -- red", group = "foundation move")
public class Auto_Foundation_Move_Red extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(!isDone) {

                //instructions go here

                isDone = true;
            }
        }

        robot.stop();

    }
}
