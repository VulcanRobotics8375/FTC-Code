/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot extends RobotCore {

    public static HardwareMap hwMap;

    public static double x;
    public static double y;

    public static double angle;

    public static double movement_x = 0;
    public static double movement_y = 0;

    public static double movePower = 0;
    public static double turnPower = 0;

    public static double strafePower = 0;

    public static void initialize() {
        front_right = hwMap.dcMotor.get("front_right");
        front_left = hwMap.dcMotor.get("front_left");
        back_right = hwMap.dcMotor.get("back_right");
        back_left = hwMap.dcMotor.get("back_left");
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(RobotConfig.IMUParameters);
        while (true) {
            if (imu.isGyroCalibrated()) break;
        }

    }


    public void run(){
        if(RobotConfig.driveType == DriveType.TANK){

        }

        if(RobotConfig.driveType == DriveType.HOLONOMIC){

        }


    }


}
