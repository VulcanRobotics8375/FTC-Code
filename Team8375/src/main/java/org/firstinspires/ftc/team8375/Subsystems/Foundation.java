/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;


import com.qualcomm.robotcore.hardware.Servo;
import static org.firstinspires.ftc.team8375.dataParser.*;

public class Foundation extends Subsystem {
    private Servo foundationLeft;
    private Servo foundationRight;
    private Servo capStone;
    private boolean override;
    int capStonePos = 1;
    int overridePos = 1;
    private boolean button;

    public Foundation() {}

    @Override
    public void create() {
        foundationLeft = hwMap.get(Servo.class, "foundation_left");
        foundationRight = hwMap.get(Servo.class, "foundation_right");
        capStone = hwMap.get(Servo.class, "capStone");
    }

    public void deployCapstone(boolean button, boolean override) {
        double capPosition;
        if(button && !this.button) {
            capStonePos *= -1;
            this.button = true;
        }
        if(this.button && !button) {
            this.button = false;
        }
        if(capStonePos > 0) {
            capPosition = parseDouble(prop, "foundation.capStoneIn");
        } else {
            capPosition = parseDouble(prop, "foundation.capStoneOut");
        }
        if(override && !this.override) {
            overridePos *= -1;
            this.override = true;
        }
        if(!override && this.override) {
            this.override = false;
        }
        if(overridePos < 0) {
            if(capPosition == parseDouble(prop, "foundation.capStoneIn")) {
                capPosition = 1;
            } else if(capPosition == parseDouble(prop, "foundation.capStoneOut")){
                capPosition = 0;
            }
        }
        capStone.setPosition(capPosition);
    }

    public void setFoundationMoveAngle(double angle) {
        foundationLeft.setPosition(angle/180.0);
        foundationRight.setPosition(1 - (angle/180));
    }

    @Override
    public void stop() {}

    public int getCapStonePos() {
        return capStonePos;
    }

}
