/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Range;

@SuppressWarnings("FieldCanBeLocal")
public class SkystoneDetect extends Subsystem {
    private ColorSensor colorSensor;

    private double error;
    private double scorer;
    private int scale = 255;
    private static final double scorerGain = 1;
    private static final double saturationGain = 10;
    private double threshold = 20;
    private float[] hsv = {0F, 0F, 0F};

    //stone color in RGB
    private float[] stoneHSV = {300, 0.36f, 39};
    private int[] sensorInput = {0, 0, 0};

    public SkystoneDetect() {}

    @Override
    public void create() {
        colorSensor = hwMap.get(ColorSensor.class, "stone_detector");
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

    public void setStoneHSV(float h, float s, float v) {
        stoneHSV[0] = h;
        stoneHSV[1] = s;
        stoneHSV[2] = v;
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
