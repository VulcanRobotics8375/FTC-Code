/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Foundation {
    private Servo foundationMove;

    public Foundation(Servo foundationMove) {
        this.foundationMove = foundationMove;
    }

    public void setFoundationMoveAngle(double angle) {
        foundationMove.setPosition(angle/180.0);
    }

}
