package org.firstinspires.ftc.team8375.Subsystems;


import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team8375.dataParser;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("FieldCanBeLocal")
public class Arm extends Subsystem {
    private Servo flip, claw;
    private DcMotor lift_left, lift_right;
    private CRServo extend;
    private TouchSensor extend_reset;
    private ElapsedTime flipTime = new ElapsedTime();
    private double currentFlipPos, lastFlipPos, liftLeftPos, liftRightPos, extendPower, liftPower, liftPos, liftHighLimit;
    private boolean flipPosSet, reset, resetIsDone, clawButton;
    private int flipOn, clawOn;
    public Arm() {}

    @Override
    public void create() {
        flip = hwMap.get(Servo.class, "flip");
        claw = hwMap.get(Servo.class, "claw");
        extend = hwMap.get(CRServo.class, "extend");
        lift_left = hwMap.dcMotor.get("lift_left");
        lift_right = hwMap.dcMotor.get("lift_right");

        lift_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_left.setDirection(DcMotorSimple.Direction.FORWARD);
        lift_right.setDirection(DcMotorSimple.Direction.REVERSE);
        liftHighLimit = dataParser.parseDouble(prop, "arm.liftHigh") - dataParser.parseDouble(prop, "arm.limitRange");
        clawOn = 1;
        flipOn = 1;
    }
    @Override
    public void stop() {
        lift_left.setPower(0);
        lift_right.setPower(0);
        extend.setPower(0);
    }

    public void run(double liftPower, double extendPower, double flipPower, boolean clawButton, boolean reset) {
        liftLeftPos = lift_left.getCurrentPosition();
        liftRightPos = lift_right.getCurrentPosition();
        liftPos = (liftLeftPos + liftRightPos) / 2.0;

        //reset code here
        if(reset) {
            this.reset = true;
            resetIsDone = false;
        }
        if(this.reset && !reset) {
            if(!resetIsDone) {
                clawOn = 1;

                if(!extend_reset.isPressed()) {
                    this.extendPower = -1;
                } else {
                    this.extendPower = 0;
                }

                if(!lift_left.isBusy()) {
                    if(liftLeftPos == 0) {
                        lift_left.setPower(0);
                        lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        lift_left.setTargetPosition(0);
                        lift_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift_left.setPower(dataParser.parseDouble(prop, "arm.liftResetPower"));
                    }
                }

                if(!lift_right.isBusy()) {
                    if (liftRightPos == 0) {
                        lift_right.setPower(0);
                        lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        lift_right.setTargetPosition(0);
                        lift_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift_right.setPower(dataParser.parseDouble(prop, "arm.liftResetPower"));
                    }
                }

                if(liftRightPos == 0 && liftLeftPos == 0 && extend_reset.isPressed()) {
                    resetIsDone = true;
                }
            }

            if(resetIsDone && this.reset) {
                lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_right.setPower(0);
                lift_left.setPower(0);
                this.reset = false;
            }
        }

        if(liftPower != 0 || extendPower != 0 || clawButton || flipPower != 0) {
            if(!resetIsDone) {
                lift_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_left.setPower(0);
                lift_right.setPower(0);
                resetIsDone = true;
                this.reset = false;
            }
        }

        //most code goes here
        if(resetIsDone) {

            //flip accel/movement code
            if (flipPower == 0) {
                if (!flipPosSet) {
                    lastFlipPos = flip.getPosition();
                    flipPosSet = true;
                }
                flipTime.reset();
            } else if (flipPower > 0) {
                flipPosSet = false;
                if (flipTime.now(TimeUnit.MILLISECONDS) < dataParser.parseDouble(prop, "arm.flipTime")) {
                    flip.setPosition(lastFlipPos + (1 / (flipTime.now(TimeUnit.MILLISECONDS) / 1000.0)));
                }
            } else if (flipPower < 0) {
                flipPosSet = false;
                if (flipTime.now(TimeUnit.MILLISECONDS) < dataParser.parseDouble(prop, "arm.flipTime")) {
                    flip.setPosition(lastFlipPos - (1 / (flipTime.now(TimeUnit.MILLISECONDS) / 1000.0)));
                }
            }

            if (liftPower > 0 && liftPos >= liftHighLimit) {
                this.liftPower = ((dataParser.parseDouble(prop, "arm.liftHigh") - liftPos) / dataParser.parseDouble(prop, "arm.limitRange")) / liftPower;

            } else if (liftPower < 0 && liftPos < dataParser.parseDouble(prop, "arm.limitRange")) {

                this.liftPower = (liftPos / dataParser.parseDouble(prop, "arm.limitRange")) / liftPower;

            } else {
                this.liftPower = liftPower;
            }
            this.extendPower = extendPower;
        }

        if(clawButton && !this.clawButton) {
            clawOn *= -1;
            this.clawButton = true;
        }
        if(!clawButton && this.clawButton) {
            this.clawButton = false;
        }

        if(clawOn > 0) {
            claw.setPosition(dataParser.parseDouble(prop, "arm.clawOut"));
        } else if(clawOn < 0) {
            claw.setPosition(dataParser.parseDouble(prop, "arm.clawIn"));
        }

        lift_left.setPower(this.liftPower);
        lift_right.setPower(this.liftPower);
        extend.setPower(this.extendPower);
    }

}