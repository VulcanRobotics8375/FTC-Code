/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Robot;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * for all static methods we use throughout our code.
 *  import static org.firstinspires.ftc.team8375.Robot.staticMethods.*;
 */

public  class staticMethods {


    /**
     * @param s The Servo you are setting the position of. This works statically because you only need the pointers of the servo for it to work anyway
     * @param angle angle of the servo in degrees, assumes 180.0
     */
    public static void setServoAngleDegrees(Servo s, double angle) {
        if(angle != 0) {
            s.setPosition(angle / 180.0);
        } else {
            s.setPosition(angle);
        }
    }


    /**
     * @param s the servo that is being affected.
     * @param angle the desired angle.
     * @param maxAngle the max angle of the servo, for REV servos that can have weird limits.
     */
    public static void setServoAngleCustom(Servo s, double angle, double maxAngle) {
        if(angle != 0) {
            s.setPosition(angle / maxAngle);
        } else {
            s.setPosition(angle);
        }
    }


    /**
     * rounds decimal numbers.
     * @param value the number you want to round.
     * @param precision the number of decimals in the output.
     * @return returns value with the number of decimals set by precision.
     */
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


    /**
     * @param ticks number of ticks.
     * @param ticksPerRev number of ticks per revolution the encoder is.
     *                    neverest 20's-- 537.6
     * @return returns number of revolutions based off of ticks and ticksPerRev.
     */
    public static double encoderTicksToRevolutions(double ticks, double ticksPerRev) {
        return ticks / ticksPerRev;
    }

    public static double revolutionsToInchesFromDiameter(double revolutions, double wheelDiameter) {
        return revolutions * (wheelDiameter * Math.PI);
    }

    public static double encoderTicksToInchesFromDiameter(double ticks, double ticksPerRev, double wheelDiameter) {
        double revolutions = encoderTicksToRevolutions(ticks, ticksPerRev);
        return revolutionsToInchesFromDiameter(revolutions, wheelDiameter);
    }

    public static double revolutionsToInchesFromCircumference(double revolutions, double wheelCircumference) {
        return revolutions * (wheelCircumference);
    }

    public static double encoderTicksToInchesFromCircumference(double ticks, double ticksPerRev, double wheelCircumference) {
        double revolutions = encoderTicksToRevolutions(ticks, ticksPerRev);
        return revolutionsToInchesFromCircumference(revolutions, wheelCircumference);
    }

    public static void newThread(Runnable r) {
        Thread t = new Thread(r);
        t.start();
    }

    public static void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }



}
