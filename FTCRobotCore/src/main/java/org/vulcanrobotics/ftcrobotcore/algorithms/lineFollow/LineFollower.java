/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.algorithms.lineFollow;


import org.vulcanrobotics.ftcrobotcore.Robot.DriveType;
import org.vulcanrobotics.ftcrobotcore.Robot.Robot;
import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfig;
import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfigException;
import org.vulcanrobotics.ftcrobotcore.algorithms.MathFunctions;
import org.vulcanrobotics.ftcrobotcore.algorithms.Point;

import java.util.ArrayList;

public class LineFollower {

    public LineFollower() {}

    public static void followLine(ArrayList<LinePoint> path, double speed, double desiredAngle) {
        try {
            if (RobotConfig.driveType == DriveType.HOLONOMIC) {
                followLineMecanum(path, speed, desiredAngle);
            } else if (RobotConfig.driveType == DriveType.TANK) {
                followLineTankDrive(path, speed);
            } else {
                throw new RobotConfigException("choose a driveType in RobotConfig");
            }
        } catch(RobotConfigException e) {
            e.printStackTrace();
        }


    }

    private static void followLineMecanum(ArrayList<LinePoint> path, double speed, double desiredAngle){


    }

    private static void followLineTankDrive(ArrayList<LinePoint> path, double speed){
        LinePoint startPoint = findFollowLineAngle(path).get(0);
        LinePoint endPoint = findFollowLineAngle(path).get(1);

        double lineAngle = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);

        Robot.movePower = startPoint.speed * speed;
        Robot.turnPower =  (MathFunctions.wrapAngle(lineAngle - Robot.angle) / 180) * startPoint.maxTurnSpeed;

    }

    //gonna focus on differential for now
    private static ArrayList<LinePoint> findFollowLineAngle(ArrayList<LinePoint> path){
        ArrayList<LinePoint> lines = new ArrayList<>();

        LinePoint startLine = new LinePoint();
        LinePoint endLine = new LinePoint();

        startLine.setPoint(path.get(0));
        endLine.setPoint(path.get(1));

        double maxWeight = Double.MAX_VALUE;
        for(int i = 0; i < path.size() - 1; i++){
            LinePoint startPoint = path.get(i);
            LinePoint endPoint = path.get(i + 1);

            double endAngleToRobot = Math.atan2(endPoint.y - Robot.y, endPoint.x - Robot.x);

            double startPointWeight = Math.hypot((startPoint.x - Robot.x), (startPoint.y - Robot.y)) / startPoint.weight;
            double endPointWeight = (Math.hypot((endPoint.x - Robot.x), (endPoint.y - Robot.y)) + (endAngleToRobot - Robot.angle)) / endPoint.weight;

            double weight = (startPointWeight + endPointWeight) / 2;

            if(weight < maxWeight) {
                maxWeight = weight;
                startLine.setPoint(startPoint);
                endLine.setPoint(endPoint);
            }
        }

        lines.add(startLine);
        lines.add(endLine);

        return lines;
    }


}
