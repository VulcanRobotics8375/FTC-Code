/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Autonomous;

import android.graphics.Point;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.disnodeteam.dogecv.detectors.skystone.StoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team8375.SkystoneDetect;
import org.firstinspires.ftc.team8375.dataParser;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@TeleOp(name="vision test", group="test")
public class visionTest extends LinearOpMode {

    OpenCvCamera webcam;
    OpenCvCamera phoneCam;
    private SkystoneDetect detector;
    private int stonePos;
    private Properties prop;

    @Override
    public void runOpMode() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("config.properties");
            if(input != null) {
                telemetry.addLine("inputstream loaded");
                prop = new Properties();
                prop.load(input);
                telemetry.update();
            } else {

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        detector = new SkystoneDetect();
        phoneCam.setPipeline(detector);

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPSIDE_DOWN);
        waitForStart();



        while(opModeIsActive()) {
            telemetry.addData("Thread", Thread.currentThread());
            telemetry.addData("rect", detector.foundRectangle());
            telemetry.addData("x", detector.foundRectangle().x);
            telemetry.addData("point", detector.getScreenPosition());
            telemetry.addData("pos", stonePos);
            if(detector.foundRectangle().x < dataParser.parseInt(prop, "detector.pos1")) {
                stonePos = 1;
            } else if(detector.foundRectangle().x < dataParser.parseInt(prop, "detector.pos2")) {
                stonePos = 2;
            } else if(detector.foundRectangle().x > dataParser.parseInt(prop, "detector.pos2")) {
                stonePos = 3;
            }
            telemetry.update();


        }


    }
}

