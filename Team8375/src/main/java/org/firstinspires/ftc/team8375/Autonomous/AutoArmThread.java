/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.team8375.Subsystems.Robot;

public class AutoArmThread extends Thread {
    private Robot robot;

    public AutoArmThread(HardwareMap hwMap) {
        robot = new Robot(hwMap);
    }

    public void run() {
        deploy();
    }

    public synchronized void deploy() {
        synchronized (this) {
            robot.autoArm.flip.setPosition(130 / 180.0);
            robot.autoArm.claw.setPosition(170 / 180.0);
            robot.autoArm.setLiftPower(1);
            try {
                sleep(3100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.autoArm.flip.setPosition(135 / 180.0);
            robot.autoArm.setClawPos(90);
            robot.autoArm.setLiftPower(-1);
            try {
                sleep(3100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.autoArm.setFlipPos(52);
            robot.autoArm.setLiftPower(0);
        }
    }

}
