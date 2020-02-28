/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Odometry;


public class Movement {

    public void runToPosition(Point point, double movementSpeed, Tracker robot) {
        double distanceToTarget = Math.hypot(point.x - robot.x, point.y - robot.y);

        double absoluteAngleToTarget = Math.atan2(point.y-robot.y, point.x-robot.x);

        double relativeAngleToTarget = MathFunctions.wrapAngle(absoluteAngleToTarget - robot.getIntegratedHeading());

        double relativeXToPoint = Math.cos(relativeAngleToTarget) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToTarget) * distanceToTarget;


        double xPowerToPoint = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint)) * movementSpeed;
        double yPowerToPoint = relativeYToPoint / (Math.abs(relativeXToPoint + relativeYToPoint)) * movementSpeed;



    }
}
