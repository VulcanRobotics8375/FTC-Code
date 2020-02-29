/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Odometry;

import java.util.ArrayList;

public class MathFunctions {

    public static double wrapAngle(double angle) {
        while(angle < -180) {
            angle += 360;
        }
        while(angle > 180) {
            angle -= 360;
        }

        return angle;
    }

    public static ArrayList<Point> LineCircleIntersect(Point circleCenter, double radius, Point linePoint1, Point linePoint2) {

        double m1 = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);

        double y1 = linePoint1.y - circleCenter.y;

        double quadraticA = 1.0 + Math.pow(m1, 2);
        double quadraticB = (2.0 * m1 * y1);
        double quadraticC = Math.pow(radius, 2) + Math.pow(y1, 2);

        try {



        } catch (Exception e) {

        }

        ArrayList<Point> points = new ArrayList<>();

        return points;
    }


}
