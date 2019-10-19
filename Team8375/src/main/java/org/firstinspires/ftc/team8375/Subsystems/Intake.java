package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("FieldCanBeLocal")
public class Intake {

    private ElapsedTime time = new ElapsedTime();
    //motors
    private DcMotor intake_left;
    private DcMotor intake_right;

    //servos
    private CRServo deploy_left;
    private CRServo deploy_right;

    public Intake(DcMotor intakeLeft, DcMotor intakeRight, CRServo deployLeft, CRServo deployRight) {
         intake_left = intakeLeft;
         intake_right = intakeRight;

         deploy_left = deployLeft;
         deploy_right = deployRight;
    }

    public void deploy(long time) {
        this.time.reset();

        while(this.time.now(TimeUnit.SECONDS) < time) {
            deploy_right.setPower(1.0);
            deploy_left.setPower(1.0);
        }

    }

    public void run(double intakePower, boolean reverse) {
        intake_left.setPower(intakePower);
        intake_right.setPower(intakePower);

        if(reverse) {
            intake_left.setPower(-intakePower);
            intake_right.setPower(-intakePower);

        }

    }

    public double getDeployLeftPos() {
        return deploy_left.getPower();
    }
    public double getDeployRightPos() {
        return deploy_right.getPower();
    }

}
