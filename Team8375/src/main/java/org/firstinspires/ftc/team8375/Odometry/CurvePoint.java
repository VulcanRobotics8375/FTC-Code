/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Odometry;

public class CurvePoint {
    public double x;
    public double y;
    public double turnSpeed;
    public double moveSpeed;
    public double followDistance;
    public double pointLength;
    public double slowDownTurnRadius;
    public double slowDownTurnAmount;

    public CurvePoint(double x, double y, double turnSpeed, double moveSpeed, double followDistance, double pointLength, double slowDownTurnAmount, double slowDownTurnRadius) {
        this.x = x;
        this.y = y;
        this.turnSpeed = turnSpeed;
        this.moveSpeed = moveSpeed;
        this.followDistance = followDistance;
        this.pointLength = pointLength;
        this.slowDownTurnRadius = slowDownTurnRadius;
        this.slowDownTurnAmount = slowDownTurnAmount;
    }


    public CurvePoint(CurvePoint point) {
        point.x = x;
        point.y = y;
        point.turnSpeed = turnSpeed;
        point.moveSpeed = moveSpeed;
        point.followDistance = followDistance;
        point.pointLength = pointLength;
        point.slowDownTurnRadius = slowDownTurnRadius;
        point.slowDownTurnAmount = slowDownTurnAmount;

    }

    public static Point toPoint(CurvePoint point) {
        return new Point(point.x, point.y);
    }
}
