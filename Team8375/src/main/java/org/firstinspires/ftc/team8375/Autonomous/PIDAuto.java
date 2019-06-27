package org.firstinspires.ftc.team8375.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name = "PIDTest", group = "Test")
public class PIDAuto extends LinearOpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    private BNO055IMU imu;
    public BNO055IMU.Parameters parameters;
    ElapsedTime time = new ElapsedTime();

    public int iteration = 0;
    public double error;
    public double previousError;
    public double integral;
    public double derivative;
    double sensorVal;
    double distanceTravelled;
    double errorSum;
    double output;
    boolean initOver;
    boolean aPressed;
    double p = 0.001;
    double i = 0;
    double d = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        Init(hardwareMap);

        waitForStart();
        while(opModeIsActive()){

            PIDMove_In(p, i, d, 20, 5, 1);
        }

        setPowers(0, 0);
    }

    public void PIDMove_In(
        double Kp,
        double Ki,
        double Kd,
        double inches,
        long iteration_time,
        double heading
        ) throws InterruptedException {

        distanceTravelled = frontLeft.getCurrentPosition()/537.6;
        sensorVal = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        error = heading - sensorVal;
        integral += ((error + previousError)/2.0) * (iteration_time/100.0);
        derivative = (error - previousError)/(iteration_time/100.0);
        output = Kp*error + Ki*integral + Kd*derivative;
        //setPowers(output);
        previousError = error;
        //iteration time is in milliseconds

        setPowers(output, 0.1);
        sleep(iteration_time);
        //controllerTune();

        telemetry.addData("output", output);
        telemetry.addData("Kp", p);
        telemetry.addData("Ki", i);
        telemetry.addData("Kd", d);
        telemetry.update();

    }
    public void Init(HardwareMap hwMap) {
        imu = hwMap.get(BNO055IMU.class, "imu");
        parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        if(imu.initialize(parameters)) {
            while (!imu.isGyroCalibrated()) {}
        } else {
            imu.initialize(parameters);
        }

        frontLeft = hwMap.dcMotor.get("front_left");
        frontRight = hwMap.dcMotor.get("front_right");
        backLeft = hwMap.dcMotor.get("back_left");
        backRight = hwMap.dcMotor.get("back_right");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        time.reset();
        initOver = true;
    }

    public void setPowers(double power, double bias) {
        frontLeft.setPower(power + bias);
        frontRight.setPower(power - bias);
        backLeft.setPower(power + bias);
        backRight.setPower(power - bias);
    }

    public void controllerTune() {
        if(gamepad1.a && !aPressed){
            p += 0.001;
            aPressed = true;
        }
        else
        if(gamepad1.b && !aPressed) {
            p -= 0.001;
            aPressed = true;
        }
        else
        if(gamepad1.right_bumper && !aPressed){
            i += 0.0001;
            aPressed = true;
        }
        else
        if(gamepad1.left_bumper && !aPressed){
            i -= 0.0001;
            aPressed = true;
        } else
        if(gamepad1.y && !aPressed){
            d += 0.0001;
            aPressed = true;
        } else
        if(gamepad1.x && !aPressed){
            d -= 0.001;
            aPressed = true;
        } else { aPressed = false; }
    }

}
