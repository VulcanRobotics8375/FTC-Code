/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.Robot.profiles;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.vulcanrobotics.ftcrobotcore.Robot.RobotConfig;
import org.vulcanrobotics.ftcrobotcore.Robot.wheels.WheelType;
import org.vulcanrobotics.ftcrobotcore.teleop.JoystickAcceleration;

public class standardMecanum extends RobotConfig {

    public void initialize() {
        //4 inches in mm
        wheelAttributes.radius = 101.6;
        wheelAttributes.wheelType = WheelType.HOLONOMIC;
        wheelAttributes.rollerAngle = Math.PI / 4.0;
        joystickAcceleration = JoystickAcceleration.LINEAR;
        robotAngleOffset = 0;
        IMUParameters.mode = BNO055IMU.SensorMode.IMU;
        IMUParameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        IMUParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        IMUParameters.loggingEnabled = false;
    }


}
