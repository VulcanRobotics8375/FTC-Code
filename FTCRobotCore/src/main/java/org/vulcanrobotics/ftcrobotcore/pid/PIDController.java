/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore.pid;

public class PIDController {

    public static double Kp;
    public static double Ki;
    public static double Kd;
    public static long iteration_time;

    private static double error;
    private static double lastError;
    private static double integral;
    private static double derivative;

    public static double run(double Kp, double Ki, double Kd, double input, double target){

        error = input - target;

        //trapezoidal rule
        integral += ((error - lastError) / 2.0) * (iteration_time / 1000.0);
        derivative = error - lastError;
        lastError = error;

        try{
            Thread.sleep(iteration_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Kp * error + Ki * integral + Kd * derivative;

    }

    public static double run(double input, double target){

        error = input - target;
        integral += ((error - lastError) / 2.0) * (iteration_time / 1000.0);
        derivative = error - lastError;

        try{
            Thread.sleep(iteration_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Kp * error + Ki * integral + Kd * derivative;
    }

    public void reset() {
        error = 0;
        integral = 0;
        derivative = 0;
    }



}
