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
    private Servo lock_left;
    private Servo lock_right;

    public Intake(DcMotor intakeLeft, DcMotor intakeRight, CRServo deployLeft, CRServo deployRight, Servo lockLeft, Servo lockRight) {
         intake_left = intakeLeft;
         intake_right = intakeRight;

         deploy_left = deployLeft;
         deploy_right = deployRight;
         lock_left = lockLeft;
         lock_right = lockRight;
    }

    public void deploy(long time) {
        this.time.reset();

        while(this.time.now(TimeUnit.SECONDS) < time) {
            deploy_right.setPower(1);
            deploy_left.setPower(1);
        }

        if(!(this.time.now(TimeUnit.SECONDS) < time + 3.0)) {
            lock_right.setPosition(90);
            lock_left.setPosition(90);
        }

    }

    public void run(double intakePower, boolean reverse) {
        intake_left.setPower(intakePower);
        intake_right.setPower(intakePower);

        if(reverse) {
            intake_left.setPower(-intakePower);
            intake_right.setPower(-intakePower);

        }

        if(lock_left.getPosition() != 90 || lock_right.getPosition() != 90) {
            lock_right.setPosition(90);
            lock_left.setPosition(90);
        }
    }

}
