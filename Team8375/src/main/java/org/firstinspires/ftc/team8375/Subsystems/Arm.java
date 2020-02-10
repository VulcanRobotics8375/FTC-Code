/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import android.content.Context;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team8375.dataParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Arm subsystem
 */

@SuppressWarnings("FieldCanBeLocal")
public class Arm extends Subsystem {
    // variable initialization
    private DcMotor lift;
    private DcMotor pitch;
    private Servo claw;
    private Servo yaw;
    private Servo level;

    private float LiftPos;
    private float pitchPos;
    private double levelPos;
    private double liftPower;
    private double pitchPower;

    //degrees per tick calculation
    private double theta;
    private double ticks;
    private double coefficient;
                                // degrees / 180.0
    private double levelBias = 95;

    private double resetPos;

    private float liftHighLimit;
    private float pitchHighLimit;
    private float lastLiftPos = 0;
    private double yawClockwise;
    private double yawCounterClockwise;

    private boolean clawPressed, yawPressed, bypass, levelUp, levelDown;
    private boolean reset = false;
    private boolean resetIsDone = true;

    private int resetStep = 0;
    private int clawOn = -1;
    private int yawOn = -1;
    private int levelCenter = 0;
    public Arm() {
        //sets constants with properties file, has to be in a method or constructor so it's here now
        theta = dataParser.parseDouble(prop, "arm.theta");
        ticks = dataParser.parseDouble(prop, "arm.pitchHigh");
        coefficient = theta / ticks;
        resetPos = dataParser.parseDouble(prop, "arm.resetPos");
    }

    @Override
    public void create() {
        lift = hwMap.dcMotor.get("lift");
        pitch = hwMap.dcMotor.get("pitch");
        claw = hwMap.get(Servo.class, "claw");
        yaw = hwMap.get(Servo.class, "yaw");
        level = hwMap.get(Servo.class, "level");
        level.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Arm.run is not independent, relies on a loop such as an OpMode.
     * @param liftPower Input for the power of the lift motor, ususally a joystick
     * @param pitchPower Input for the pitch motor
     * @param clawButton boolean for opening and closing the claw -- system doesn't rely on this value being true or false, it has a state switch for every true false cycle of the boolean.
     * @param yawButton boolean for the flip servo toggle, same true false cycle state as the clawButton param
     * @param bypass bypass the pitch zero position
     * @param reset reset button-- resets all lift components to starting position
     * @param levelUp button for toggling the level give upward
     * @param levelDown button for toggling the level give downward
     * @param flipGive joystick input for extra flip movement
     */
    public void run(double liftPower, double pitchPower, boolean clawButton, boolean yawButton, boolean bypass, boolean reset, boolean levelUp, boolean levelDown, double flipGive) {

        //limits
        LiftPos = lift.getCurrentPosition();
        pitchPos = pitch.getCurrentPosition();
        liftHighLimit = dataParser.parseFLoat(prop, "arm.liftHigh") - dataParser.parseFLoat(prop, "arm.limitRange");
        pitchHighLimit = dataParser.parseFLoat(prop, "arm.pitchHigh") - dataParser.parseFLoat(prop, "arm.limitRange");

        //lower bound
        if(reset) {
            this.reset = true;
            resetIsDone = false;
            resetStep = 0;
            clawOn = -1;
        }
        if (this.reset && !reset) {
            if(!resetIsDone) {
                levelBias = dataParser.parseDouble(prop, "arm.levelBias");

                if(!pitch.isBusy() && resetStep > 1) {
                    if (pitchPos == 0) {
                        pitch.setPower(0);
                        pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        pitch.setTargetPosition(0);
                        pitch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        pitch.setPower(dataParser.parseDouble(prop, "arm.pitchResetPower"));
                    }
                }
                if(yaw.getPosition() > 0 && resetStep < 1) {
                    setServoAngle(yaw, 130);
                }

                if(!lift.isBusy() && resetStep == 0) {
                    if(LiftPos >= resetPos) {
                        lift.setPower(0);
                        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        resetStep++;
                    }
                    else if (LiftPos <= resetPos + 5 && LiftPos >= resetPos - 5) {

                        lift.setPower(0);
                        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        resetStep++;

                    } else {
                        lift.setTargetPosition((int) resetPos);
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.setPower(dataParser.parseDouble(prop, "arm.liftResetPower"));
                    }
                }

                if (resetStep == 1) {
                    if(yaw.getPosition() != Double.parseDouble(prop.getProperty("arm.yawRetracted"))) {
                        setServoAngle(yaw, dataParser.parseDouble(prop, "arm.yawRetracted"));
                    } else {
                        resetStep++;
                        yawOn = -1;
                    }
                }

                if (!lift.isBusy() && resetStep == 2) {
                    if (Math.abs(LiftPos) > 5) {
                        lift.setTargetPosition(0);
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.setPower(-0.5);
                    } else {
                        lift.setPower(0);
                        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        resetStep++;
                    }
                }

                if(resetStep == 3 && Math.abs(pitchPos) < 5 && Math.abs(LiftPos) < 6) {
                    resetIsDone = true;
                }
            }

            if(resetIsDone && this.reset) {
                pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setPower(0);
                pitch.setPower(0);
                this.reset = false;
            }
        }

        if(pitchPower != 0 || liftPower != 0 || clawButton || yawButton) {
            if(!resetIsDone) {
                pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setPower(0);
                pitch.setPower(0);
                resetIsDone = true;
                this.reset = false;
            }
        }

        if(resetIsDone) {

            if(liftPower < 0 && yawOn > 0) {
                if(LiftPos <= dataParser.parseDouble(prop, "arm.limitRange") + dataParser.parseDouble(prop, "arm.liftLow")) {

                    this.liftPower = (dataParser.parseDouble(prop, "arm.liftLow") - pitchPos) / dataParser.parseDouble(prop, "arm.limitRange");

                } else {
                    this.liftPower = liftPower;
                }
            }
            else if (liftPower < 0 && LiftPos <= dataParser.parseDouble(prop, "arm.limitRange")) {
                //gradual slow down
                this.liftPower = (-(LiftPos / dataParser.parseDouble(prop, "arm.limitRange"))) / 1.0;
                lastLiftPos = LiftPos;
            }

            //upper bound
            else if (liftPower > 0 && LiftPos >= liftHighLimit) {

                //takes the distance from the upper limit and divides it by the limit range for a gradual slow down of the motor.
                this.liftPower = ((dataParser.parseDouble(prop, "arm.liftHigh") - LiftPos) / (dataParser.parseDouble(prop, "arm.limitRange"))) / 1.0;
                lastLiftPos = LiftPos;
            } else {
                this.liftPower = liftPower;
            }

            //pitch limits
            if (!bypass && !this.bypass) {
                if (pitchPower < 0 && pitchPos <= dataParser.parseDouble(prop, "arm.limitRange")) {
                    this.pitchPower = (-(pitchPos / (dataParser.parseDouble(prop, "arm.limitRange") / Math.abs(pitchPower)))) / 1.0;

                } else if (pitchPower > 0 && pitchPos >= pitchHighLimit) {
                    this.pitchPower = (dataParser.parseDouble(prop, "arm.pitchHigh") - pitchPos) / (dataParser.parseDouble(prop, "arm.limitRange") / pitchPower) / 1.0;
                } else {
                    this.pitchPower = pitchPower;
                }
            } else if (bypass) {
                this.pitchPower = pitchPower;
                this.bypass = true;
            }
            if (this.bypass && !bypass) {
                pitch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                this.bypass = false;
            }

            //claw button
            if (clawButton && !clawPressed) {
                clawOn *= -1;
                clawPressed = true;
            }
            if (clawPressed && !clawButton) {
                clawPressed = false;
            }

            if (clawOn < 0) {
                setServoAngle(claw, Double.parseDouble(prop.getProperty("arm.clawOut")));
            } else if (clawOn > 0) {
                setServoAngle(claw, Double.parseDouble(prop.getProperty("arm.clawIn")));
            }

            //set powers

            if (yawButton && !yawPressed) {
                yawOn *= -1;
                yawPressed = true;
            }
            if (yawPressed && !yawButton) {
                yawPressed = false;
            }

            if (yawOn < 0) {
                setServoAngle(yaw, dataParser.parseDouble(prop, "arm.yawRetracted"));
            } else if (yawOn > 0) {
                setServoAngle(yaw, dataParser.parseDouble(prop, "arm.yawDeployed") + (flipGive * 18));

            }

            lift.setPower(this.liftPower);
            pitch.setPower(this.pitchPower);
        }

        if(levelUp && !this.levelUp) {
            if(levelCenter == 0) {
                if (yawOn > 0) {
                    levelCenter = -1;
                } else if (yawOn < 0) {
                    levelCenter = 1;
                }
            } else if(Math.abs(levelCenter) == 1) {
                levelCenter = 0;
            }
            this.levelUp = true;
        } if(!levelUp && this.levelUp) {
            this.levelUp = false;
        }

        if(levelDown && !this.levelDown) {
            if(levelCenter == 0) {
                if (yawOn > 0) {
                    levelCenter = 1;
                } else if (yawOn < 0) {
                    levelCenter = -1;
                }
            } else if(Math.abs(levelCenter) == 1) {
                levelCenter = 0;
            }
            this.levelDown = true;
        } if(!levelDown && this.levelDown) {
            this.levelDown = false;
        }

        if(levelCenter == 0) {
            levelBias = dataParser.parseDouble(prop, "arm.levelBias");
        } else if(levelCenter == 1) {
            levelBias = dataParser.parseDouble(prop, "arm.levelBias") - dataParser.parseDouble(prop, "arm.levelGive");
        } else if(levelCenter == -1) {
            levelBias = dataParser.parseDouble(prop, "arm.levelBias") + dataParser.parseDouble(prop, "arm.levelGive");
        }



        //leveler
        levelPos = (double) pitchPos * coefficient;

        if (pitchPos != 0) {
            setServoAngle(level, levelPos + levelBias);
        } else {
            setServoAngle(level, levelBias);
        }
    }

    public void motorTest(double liftPower, double pitchPower) {

        LiftPos = lift.getCurrentPosition();
        pitchPos = pitch.getCurrentPosition();

        lift.setPower(liftPower);
        pitch.setPower(pitchPower);

    }

    public void ArmMotorInit(int position) {

        //motor initialization
//        lift.setDirection(DcMotor.Direction.REVERSE);
        //there's a lot but its all important
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pitch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pitch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        if(position > 0) {
//            lift.setTargetPosition(-position);
//            lift.setPower(0.05);
//            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        } else if(position < 0) {
//            lift.setTargetPosition(position);
//            lift.setPower(-0.05);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
//
//        if(!lift.isBusy()){
//            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }

    }


    private void setServoAngle(Servo servo, double angle) {

        if(angle != 0) {
            servo.setPosition(angle / 180.0);
        } else {
            servo.setPosition(angle);
        }

    }

    @Override
    public void stop() {
        lift.setPower(0);
        pitch.setPower(0);
    }

    //Testing stuff

    public double getLiftPower() {
        return liftPower;
    }
    public float getLiftPos() {
        return LiftPos;
    }
    public float getPitchPos() {
        return pitchPos;
    }
    public double getClawPos() {
        return claw.getPosition();
    }
    public double getLevelPos() {
        return level.getPosition();
    }

    public int getResetStep() {
        return resetStep;
    }

    public boolean isResetDone() {
        return resetIsDone;
    }
}