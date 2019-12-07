/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Foundation {
    private Servo foundationMove;
    private Servo capStone;
    int capStonePos = 1;
    private boolean button;

    public Foundation(Servo foundationMove, Servo capStone) {
        this.foundationMove = foundationMove;
        this.capStone = capStone;
    }

    public void deployCapstone(boolean button) {
        if(button) {
            this.button = true;
        }
        if(this.button && !button) {
            capStonePos *= -1;
            this.button = false;
        }
        if(capStonePos > 0) {
            capStone.setPosition(1);
        } else {
            capStone.setPosition(0.7);
        }
    }

    public void setFoundationMoveAngle(double angle) {
        foundationMove.setPosition(angle/180.0);
    }

}
