/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Odometry;

import static org.firstinspires.ftc.team8375.Odometry.MathFunctions.*;
import static java.lang.Math.*;

public class Movement {

    public void runToPosition(Point point, double movementSpeed, Tracker robot) {

        double relativeYDistanceToPoint = point.y - robot.y;
        double relativeXDistanceToPoint = point.x - robot.x;

        double relativeDistanceToPoint = hypot(relativeXDistanceToPoint, relativeYDistanceToPoint);

        double absoluteAngleToTarget = atan2(relativeYDistanceToPoint, relativeXDistanceToPoint);

        double robotAngleToTarget = wrapAngle(absoluteAngleToTarget - robot.getIntegratedHeading());

        double turnSpeed = (robotAngleToTarget/360) * movementSpeed;

        robot.moveSpeed = movementSpeed;
        robot.turnSpeed = turnSpeed;

    }
}
