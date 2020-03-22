/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.algorithms;


import org.vulcanrobotics.ftcrobotcore.Robot.DriveType;
import org.vulcanrobotics.ftcrobotcore.Robot.Robot;
import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfig;
import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfigException;

import java.util.ArrayList;

public class LineFollower {

    public LineFollower() {}

    public static void followLine(ArrayList<Point> path, double speed, double desiredAngle) throws RobotConfigException {
        if(RobotConfig.driveType == DriveType.HOLONOMIC) {
            followLineMecanum(path, speed, desiredAngle);
        } else if(RobotConfig.driveType == DriveType.TANK) {
            followLineTankDrive(path, speed);
        }
        else {
            throw new RobotConfigException("choose a driveType in RobotConfig");
        }


    }

    private static void followLineMecanum(ArrayList<Point> path, double speed, double desiredAngle){
//        double error = MathFunctions.wrapAngle(getCurrentLineSegmentAngle(path) - Robot.angle);



    }

    private static void followLineTankDrive(ArrayList<Point> path, double speed){

    }


}
