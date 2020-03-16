/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.algorithms.holonomics;

import org.vulcanrobotics.ftcrobotcore.Robot.Robot;
import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfig;
import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfigException;
import org.vulcanrobotics.ftcrobotcore.algorithms.JoystickAcceleration;

public class mecanumDrive {

    public static double[] standardMecanumDriveController(double forward, double strafe, double turnPower, double multiplier) throws RobotConfigException {

        double vd = Math.hypot(forward, strafe);
        double theta = Math.atan2(forward, strafe) - (Math.PI / 4);

        double[] v = {
                vd * Math.sin(theta) - turnPower,
                vd * Math.cos(theta) + turnPower,
                vd * Math.cos(theta) - turnPower,
                vd * Math.sin(theta) + turnPower
        };

        if(RobotConfig.joystickAcceleration == JoystickAcceleration.NON_LINEAR)
            return new double[]{
                    multiplier * (v[0] / 1.07) * ((0.62 * Math.pow(v[0], 2)) + 0.45),
                    multiplier * (v[1] / 1.07) * ((0.62 * Math.pow(v[1], 2)) + 0.45),
                    multiplier * (v[2] / 1.07) * ((0.62 * Math.pow(v[2], 2)) + 0.45),
                    multiplier * (v[3] / 1.07) * ((0.62 * Math.pow(v[3], 2)) + 0.45)
            };
        else if(RobotConfig.joystickAcceleration == JoystickAcceleration.CUBIC)
            return new double[]{
                    multiplier * Math.pow(v[0], 3),
                    multiplier * Math.pow(v[1], 3),
                    multiplier * Math.pow(v[2], 3),
                    multiplier * Math.pow(v[3], 3)
            };
        else if(RobotConfig.joystickAcceleration == JoystickAcceleration.LINEAR)
            return new double[]{
                    multiplier * v[0],
                    multiplier * v[1],
                    multiplier * v[2],
                    multiplier * v[3],

            };
        else
            throw new RobotConfigException("no joystick acceleration entered in robotConfig" + RobotConfig.joystickAcceleration.toString());
    }

    public static double[] FieldCentricMecanumDrive(double forward, double strafe, double turn, double startAngle, double currentAngle, double multiplier) throws RobotConfigException {
        double joystickPower = Math.hypot(forward, strafe);
        double joystickAngle = Math.atan2(forward, strafe);

        double relativeAngleToField = Math.toRadians(currentAngle) + startAngle;

        double relativeAngleToJoystick = relativeAngleToField + joystickAngle;

        if(RobotConfig.joystickAcceleration == JoystickAcceleration.LINEAR)
            return new double[]{
                    joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) - turn,
                    joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) + turn,
                    joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) - turn,
                    joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) + turn,
            };
        else if(RobotConfig.joystickAcceleration == JoystickAcceleration.CUBIC)
            return new double[]{
                    Math.pow(joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) - turn, 3),
                    Math.pow(joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) + turn, 3),
                    Math.pow(joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) - turn, 3),
                    Math.pow(joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) + turn, 3)
            };
        else if(RobotConfig.joystickAcceleration == JoystickAcceleration.NON_LINEAR)
            return new double[]{
                    ((joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) - turn) / 1.07) * ((0.62 * Math.pow(joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) - turn, 2)) + 0.45),
                    ((joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) + turn) / 1.07) * ((0.62 * Math.pow(joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) + turn, 2)) + 0.45),
                    ((joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) - turn) / 1.07) * ((0.62 * Math.pow(joystickPower * Math.sin(relativeAngleToJoystick + RobotConfig.wheelAttributes.rollerAngle) - turn, 2)) + 0.45),
                    ((joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) + turn) / 1.07) * ((0.62 * Math.pow(joystickPower * Math.sin(relativeAngleToJoystick - RobotConfig.wheelAttributes.rollerAngle) + turn, 2)) + 0.45)
            };
        else
            throw new RobotConfigException("no joystick acceleration entered in robotConfig" + RobotConfig.joystickAcceleration.toString());

    }



}
