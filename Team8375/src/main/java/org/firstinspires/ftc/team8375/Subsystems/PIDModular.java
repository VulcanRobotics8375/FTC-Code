package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class PIDModular {
    private DcMotor[] motor;
    private BNO055IMU imu;
    private String sensor;
    HardwareMap hardwareMap;

    private double error;
    private double previousError;
    private double integral;
    private double derivative;
    private double sensorVal;
    private double output;
    private double power= 0;

    private double a;
    private double b;
    private double c;

    private int iteration;
    private double inches;

    public PIDModular(String motorName0, String motorName1, String sensorName){
        motor[0] = hardwareMap.dcMotor.get(motorName0);
        motor[1] = hardwareMap.dcMotor.get(motorName1);
        if(sensorName.equals("imu")) {
            imu = hardwareMap.get(BNO055IMU.class, "imu");

        }
        sensor = sensorName;
        iteration = 0;
    }
    public void run(
            double Kp,
            double Ki,
            double Kd,
            double bias,
            double heading,
            long iteration_time,
            AxesOrder axes
    ) throws InterruptedException {
        if(sensor.equals("imu")){
            //axes order = ZYX
            sensorVal = imu.getAngularOrientation(AxesReference.EXTRINSIC, axes, AngleUnit.DEGREES).firstAngle;
        } else {
        }

        while(power < bias) {
            error = sensorVal - heading;
            //integral uses trapezoidal rule - https://en.wikipedia.org/wiki/Numerical_integration#Methods_for_one-dimensional_integrals
            //Essentially creates a pseudo trapezoid with the relative linear slope of the curve from the last point to the current point,
            //then finds the area of that trapezoid. I might change to iterated rule at some point but it might cause lag if I
            // can't get matrices to work properly
            integral += (((error + previousError) / 2) * iteration_time);
            //iterated rule maybe??? we'll see lol i tried
            // integral = integral + ((error/2)+(error+((iteration*iteration_time)/iteration))+(previousError/2));
            derivative = (error - previousError) / iteration_time;
            //bias is the desired power output
            output = Kp * error + Ki * integral + Kd * derivative + bias;

            if (output < 0) {
                motor[0].setPower(power / 100);
                motor[1].setPower(((0.02 * power) + power) / 100);
            } else if (output > 0) {
                motor[0].setPower(((0.02 * power) + power) / 100);
                motor[1].setPower(bias / 100);
            } else if (output == 0) {
                motor[0].setPower(bias / 100);
                motor[1].setPower(bias / 100);
            }

            previousError = error;
            iteration++;
            power++;
            Thread.sleep(iteration_time);
        }
    }

}

