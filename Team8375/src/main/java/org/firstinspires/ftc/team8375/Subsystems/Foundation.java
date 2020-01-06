/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Subsystems;

import android.content.Context;

import com.qualcomm.robotcore.hardware.Servo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class Foundation {
    private Context context;
    private Servo foundationMove;
    private Servo capStone;
    int capStonePos = 1;
    private boolean button;
    private Properties prop;

    public Foundation(Servo foundationMove, Servo capStone) {
        this.foundationMove = foundationMove;
        this.capStone = capStone;

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("config.properties");
            if(input != null) {
                prop = new Properties();
                prop.load(input);
            } else {

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void deployCapstone(boolean button) {
        if(button && !this.button) {
            capStonePos *= -1;
            this.button = true;
        }
        if(this.button && !button) {
            this.button = false;
        }
        if(capStonePos > 0) {
            capStone.setPosition(Double.parseDouble(prop.getProperty("foundation.capStoneIn")));
        } else {
            capStone.setPosition(Double.parseDouble(prop.getProperty("foundation.capStoneOut")));
        }
    }

    public void setFoundationMoveAngle(double angle) {
        foundationMove.setPosition(angle/180.0);
    }

}
