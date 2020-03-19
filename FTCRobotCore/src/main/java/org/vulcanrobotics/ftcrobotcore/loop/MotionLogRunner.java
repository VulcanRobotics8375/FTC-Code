/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.loop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.vulcanrobotics.ftcrobotcore.MotionLogger;

@Autonomous(name = "Run Motion Log", group = "RobotCoreLib")
public abstract class MotionLogRunner extends Loop {
    private MotionLogger logger1;
    private MotionLogger logger2;
    private int n = 0;

    @Override
    public void init() {
        coreInit();

        logger1 = new MotionLogger();
        logger2 = new MotionLogger();
        logger1.loadGamepadArrayFromJSON("moveset1.json");
        logger2.loadGamepadArrayFromJSON("moveset2.json");
    }

    @Override
    public void loop() {
        coreLoop();

        n++;
    }

    public Object gamepad1(String key) {
        return logger1.getValue(n, key);
    }
    public Object gamepad2(String key){
        return logger2.getValue(n, key);
    }
}
