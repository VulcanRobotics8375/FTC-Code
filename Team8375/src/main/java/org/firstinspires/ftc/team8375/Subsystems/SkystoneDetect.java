/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Subsystems;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Range;

@SuppressWarnings("FieldCanBeLocal")
public class SkystoneDetect {
    private ColorSensor colorSensor;

    private double error;
    private double scorer;
    private int scale = 255;
    private static final double scorerGain = 1.5;
    private static final double saturationGain = 10;
    private double threshold;
    private float[] hsv = {0F, 0F, 0F};

    //stone color in RGB
    private static final float[] stoneHSV = {320, 0.6f, 210};
    private int[] sensorInput = {0, 0, 0};

    public SkystoneDetect(ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
    }

    public boolean detect() {

        Color.RGBToHSV(
                (getSensorInput(0) * scale),
                (getSensorInput(1) * scale),
                (getSensorInput(2) * scale),
                hsv
        );

        updateSensorInput();

        for(int i = 0; i < 3; i++) {
            if(i == 1) {
                error = (int) stoneHSV[i] - hsv[i] * saturationGain;
            } else {
                error = (int) stoneHSV[i] - hsv[i] * scorerGain;
            }

            scorer += Range.clip(error, -10, 10);
        }

        return Math.abs(scorer) > (30 - threshold);


    }

    private void updateSensorInput() {
        sensorInput[0] = colorSensor.red();
        sensorInput[1] = colorSensor.blue();
        sensorInput[2] = colorSensor.green();
    }

    public void setScorerThreshold(double threshold) {
        this.threshold = threshold;
    }

    public int getSensorInput(int i) {
        return sensorInput[i];
    }

    public float getHSV(int i) {
        return hsv[i];
    }

    public void resetScore() {
        scorer = 0;
    }

    public double getScorer() {
        return scorer;
    }

}
