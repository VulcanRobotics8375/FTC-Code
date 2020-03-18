/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.loop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.vulcanrobotics.ftcrobotcore.MotionLogger;

@TeleOp(name = "motion logger", group = "RobotCoreLib")
public class MotionLoggerOpMode extends OpMode {
    private MotionLogger logger1;
    private MotionLogger logger2;
    private int n = 0;

    @Override
    public void init() {
        logger1 = new MotionLogger();
        logger2 = new MotionLogger();

    }

    @Override
    public void loop() {

        //loop stuff here

        updateMotionLogger();
    }

    @Override
    public void stop() {
        logger1.copyGamepadArrayToJSON("moveset1.json");
        logger2.copyGamepadArrayToJSON("moveset2.json");
    }

    private void updateMotionLogger() {
        logger1.saveGamepadState(n, gamepad1);
        logger2.saveGamepadState(n, gamepad2);
        n++;
    }
}
