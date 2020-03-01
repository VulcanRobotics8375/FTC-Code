package org.firstinspires.ftc.team8375.Subsystems;


import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.team8375.dataParser.*;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("FieldCanBeLocal")
public class Arm extends Subsystem {
    private Servo adjust, claw;
    private DcMotor lift_left, lift_right;
    private CRServo extend;
    private ElapsedTime flipTime = new ElapsedTime();
    private double liftLeftPos, adjustPos, flipPos, liftRightPos, extendPower, liftLeftPower, liftRightPower, liftHighLimit;
    private boolean clawButton, reset, flipButton, upButton;
    private int clawOn, flipOn;
    public Arm() {}

    @Override
    public void create() {
        adjust = hwMap.get(Servo.class, "adjust");
        claw = hwMap.get(Servo.class, "claw");
        extend = hwMap.get(CRServo.class, "extend");
        lift_left = hwMap.dcMotor.get("lift_left");
        lift_right = hwMap.dcMotor.get("lift_right");

        lift_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_left.setDirection(DcMotorSimple.Direction.REVERSE);
        lift_right.setDirection(DcMotorSimple.Direction.FORWARD);
        liftHighLimit = parseDouble(prop, "arm.liftHigh") - parseDouble(prop, "arm.limitRange");
        clawOn = 1;
        flipOn = 1;
    }
    @Override
    public void stop() {
        lift_left.setPower(0);
        lift_right.setPower(0);
        extend.setPower(0);
    }

    public void run(double liftPower, double extendPower, double flipPower, boolean flipButton, boolean clawButton, boolean upButton, boolean reset) {
        liftLeftPos = lift_left.getCurrentPosition();
        liftRightPos = lift_right.getCurrentPosition();


        if(upButton && !this.upButton) {
           this.upButton = true;
        }
        if(!upButton && this.upButton) {
            if(!lift_left.isBusy() && !lift_right.isBusy()) {
                if (liftLeftPos < parseDouble(prop, "arm.intakePos") && liftRightPos < parseDouble(prop, "arm.intakePos")) {
                    lift_left.setTargetPosition(parseInt(prop, "arm.intakePos"));
                    lift_right.setTargetPosition(parseInt(prop, "arm.intakePos"));

                    lift_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    lift_right.setPower(0.2);
                    lift_left.setPower(0.2);
                } else {
                    lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    lift_left.setPower(0);
                    lift_right.setPower(0);
                }
            }
        }
        if((liftPower != 0 || extendPower != 0) && this.upButton) {
            this.upButton = false;
            lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift_left.setPower(0);
            lift_right.setPower(0);
        }

        //most code goes here
        if(!this.upButton) {

            //adjust accel/movement code
            if(flipButton && !this.flipButton) {
                flipOn *= -1;
                flipPos = 0;
                this.flipButton = true;
            }
            if(!flipButton && this.flipButton) {
                this.flipButton = false;
            }

            if (flipPower > 0) {
                if(flipTime.time(TimeUnit.MILLISECONDS) > (30 / flipPower)) {
                    flipPos += 0.01;
                    flipTime.reset();
                }
            } else if (flipPower < 0) {
                if(flipTime.time(TimeUnit.MILLISECONDS) > (30 / Math.abs(flipPower))) {
                    flipPos -= 0.01;
                    flipTime.reset();
                }
            }


            if (liftPower > 0 && liftLeftPos >= liftHighLimit) {

                this.liftLeftPower = (parseDouble(prop, "arm.liftHigh") - liftLeftPos) / parseDouble(prop, "arm.limitRange") * liftPower;

            } else if (liftPower < 0 && liftLeftPos < parseDouble(prop, "arm.limitRange")) {

                this.liftLeftPower = (liftLeftPos / parseDouble(prop, "arm.limitRange")) * liftPower;

            } else {
                this.liftLeftPower = liftPower;
            }
            if(liftPower > 0 && liftRightPos >= liftHighLimit) {

                this.liftRightPower = ((parseDouble(prop, "arm.liftHigh") - liftRightPos) / parseDouble(prop, "arm.limitRange")) * liftPower;

            } else if(liftPower < 0 && liftRightPos < parseDouble(prop, "arm.limitRange")) {

                this.liftRightPower = (liftRightPos / parseDouble(prop, "arm.limitRange")) * liftPower;

            } else {
                this.liftRightPower = liftPower;
            }

            this.extendPower = extendPower;

            if(clawButton && !this.clawButton) {
                clawOn *= -1;
                this.clawButton = true;
            }
            if(!clawButton && this.clawButton) {
                this.clawButton = false;
            }
        }

        if(clawOn > 0) {
            setServoAngle(claw, parseDouble(prop, "arm.clawOut"));
        } else if(clawOn < 0) {
            setServoAngle(claw, parseDouble(prop, "arm.clawIn"));
            if(liftLeftPower < 0 && liftRightPower < 0) {
                liftLeftPower *= 0.4;
                liftRightPower *= 0.4;
            }
        }
        if(flipOn < 0) {
            adjustPos = parseDouble(prop, "arm.adjustOut") + flipPos;
        } else if(flipOn > 0) {
            adjustPos = parseDouble(prop, "arm.adjustPos") + flipPos;
        }

        if(reset && !this.reset) {
            flipOn = 1;
            flipPos = 0;
            this.reset = true;
        } if(!reset && this.reset) {
            this.reset = false;
        }

        //set everything at the end of the loop.
        adjust.setPosition(adjustPos);
        lift_left.setPower(this.liftLeftPower);
        lift_right.setPower(this.liftRightPower);
        extend.setPower(this.extendPower);
    }

    public void runToPosition(int pos, double power, boolean stopRequested) {
        lift_left.setTargetPosition(pos);
        lift_right.setTargetPosition(pos);
        lift_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift_left.setPower(power);
        lift_right.setPower(power);
        while(lift_left.isBusy() || lift_right.isBusy()) {
            if(stopRequested)
                return;
        }
    }

    public void setServoAngle(Servo servo, double angle) {
        if(angle != 0) {
            servo.setPosition(angle / 180);
        }
    }

    public void setClawPos(double pos) {
        claw.setPosition(pos);
    }

    public double getLiftLeftPos() {
        return lift_left.getCurrentPosition();
    }
    public double getLiftRightPos() {
        return lift_right.getCurrentPosition();
    }

    public void setExtendPower(double v) {
        extend.setPower(v);
    }

    //TODO add more debugging stuff

}