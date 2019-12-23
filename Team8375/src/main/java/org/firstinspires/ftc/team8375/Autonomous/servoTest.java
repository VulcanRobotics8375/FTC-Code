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

@Autonomous(name = "servo test", group = "test")
public class servoTest extends VulcanPipeline {

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {

            if(!isDone)
//                setAutoArmPos(0.3);
            deployAutoArm();
            isDone = true;

        }

    }
}
