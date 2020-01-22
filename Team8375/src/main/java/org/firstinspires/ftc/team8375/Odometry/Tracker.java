/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Odometry;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class Tracker extends Thread {

    public double x = 0;
    public double y = 0;
    private double imuRadius;
    private BNO055IMU imu;
    private long iterationTime = 7;
    private double previousVelocity;
    private double velocity;
    private boolean running = true;


    public Tracker(BNO055IMU imu) {
        this.imu = imu;
    }

    @Override
    public void run() {
        while(running) {
            velocity = previousVelocity + (imu.getAcceleration().zAccel * (iterationTime / 1000.0));

            velocity -= imu.getAngularVelocity().zRotationRate * imuRadius;

            x += (velocity * (iterationTime / 1000.0)) * Math.cos(imu.getAngularOrientation().firstAngle);
            y += (velocity * (iterationTime / 1000.0)) * Math.sin(imu.getAngularOrientation().firstAngle);

            previousVelocity = velocity;

            try {
                Thread.sleep(iterationTime / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetPosition(double x, double y) {
        this.x = x;
        this.y =  y;
    }
    public void resetPosition() {
        this.x = 0;
        this.y = 0;
    }

    public void setRunMode(boolean running) {
        this.running = running;
    }


}
