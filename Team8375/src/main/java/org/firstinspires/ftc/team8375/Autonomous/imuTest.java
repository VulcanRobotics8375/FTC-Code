package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="imu test", group="test")
public class imuTest extends OpMode {
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor imu;

    public void init() {
        imu = hardwareMap.get(NavxMicroNavigationSensor.class, "imu");
        gyro = (IntegratingGyroscope)imu;
        while(imu.isCalibrating()) {
            telemetry.addData("imu", "IMU is calibrating...");

        }
        telemetry.clear();

    }

    public void loop(){
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("heading", angles.firstAngle);
        telemetry.addData("pitch", angles.secondAngle);
        telemetry.addData("roll", angles.thirdAngle);
        telemetry.update();
    }

    public void stop() {}

}
