/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.algorithms.lineFollow;

import org.vulcanrobotics.ftcrobotcore.algorithms.Point;

public class LinePoint {

    public double x;
    public double y;
    public double speed;
    public double maxTurnSpeed;
    public double weight;


    public LinePoint(Point point, double speed, double maxTurnSpeed, double weight) {
        this.x = point.x;
        this.y = point.y;
        this.speed = speed;
        this.maxTurnSpeed = maxTurnSpeed;
        this.weight = weight;
    }

    public LinePoint(){}


    public void setPoint(LinePoint point) {
        this.x = point.x;
        this.y = point.y;
        this.speed = point.speed;
        this.maxTurnSpeed = point.maxTurnSpeed;
        this.weight = point.weight;

    }

    public Point toPoint(){

        return new Point(x, y);
    }
}
