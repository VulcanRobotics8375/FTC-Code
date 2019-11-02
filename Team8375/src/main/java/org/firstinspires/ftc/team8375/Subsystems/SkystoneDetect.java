/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Range;

@SuppressWarnings("FieldCanBeLocal")
public class SkystoneDetect {
    private ColorSensor colorSensor;

    private double error;
    private double scorer;
    private static final double scorerGain = 1;
    private double threshold;

    //stone color in RGB
    private static final int[] stoneRGB = {95, 75, 45};
    private int[] sensorInput = {0, 0, 0};

    public SkystoneDetect(ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
    }

    public boolean detect() {
        updateSensorInput();

        for(int i = 0; i < 3; i++) {
            error = stoneRGB[i] - sensorInput[i] * scorerGain;
            scorer += Range.clip(error, -10, 10);
        }

        if(scorer > (30 - threshold)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateSensorInput() {
        sensorInput[0] = colorSensor.red();
        sensorInput[1] = colorSensor.blue();
        sensorInput[2] = colorSensor.green();
    }

    public void setScorerThreshold(double threshold) {
        this.threshold = threshold;
    }
}
