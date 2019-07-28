/*
 * Copyright (c) 2019. Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375;


import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.imgcodecs.Imgcodecs;

import java.lang.reflect.Array;
import java.util.Vector;

public class OpenCVClass extends OpenCVPipeline {
    private Mat hsv = new Mat();
    private Mat threshold = new Mat();
    private Mat rgb = new Mat();
    private Mat output = new Mat();
    private Mat buffer = new Mat();
    private Bitmap bitmap;
    // private MatOfPoint output = new MatOfPoint();
    private List<MatOfPoint> contours = new ArrayList<>();
    private List<Moments> mu;
    int x;
    int y;

    public synchronized List<MatOfPoint> getContours() {
        return contours;
    }

    public synchronized Mat getThreshold(){
        return threshold;
    }

    public synchronized Mat getRgb() {
        return rgb;
    }

    @Override
    public Mat processFrame(Mat rgba, Mat gray) {
        //this is where image processing goes

        Imgproc.blur(rgba, hsv, new Size(60, 60));
        Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_RGB2HSV, 3);
        Core.inRange(hsv, new Scalar(0, 140, 206), new Scalar(180, 255, 255), threshold);
        //Rect rectCrop = new Rect(100, 50, 200, 500);


        output = rgba;


        findContours(threshold, false, contours);
        findContourPos(contours, false);
        Imgproc.circle(output, new Point(x, y), 100, new Scalar(0, 0, 255), 10);

        //Imgproc.cvtColor(threshold, output, Imgproc.COLOR_HSV2RGB, 3);
        //Imgproc.circle(output, new Point(230, 160), 100, new Scalar(0, 0, 255), 10);

// comment this rotate out when done pls
        // Core.rotate(rgba, rgba, Core.ROTATE_180);
        //rgb = rgba.submat(rectCrop);

//        MatOfInt outputArray = new MatOfInt(CvType.CV_32S);
//        rgba.convertTo(outputArray, CvType.CV_32S);
//        array = new int[(int)(outputArray.total()*outputArray.channels())];
//        rgba.get(0, 0, array);

        //Imgproc.cvtColor(output, buffer, Imgproc.COLOR_RGB2GRAY, 0);



        return output;

    }



    private void findContours(Mat input, boolean externalOnly,
                              List<MatOfPoint> contours) {
        Mat hierarchy = new Mat();
        contours.clear();
        int mode;
        if (externalOnly) {
            mode = Imgproc.RETR_EXTERNAL;
        }
        else {
            mode = Imgproc.RETR_LIST;
        }
        int method = Imgproc.CHAIN_APPROX_SIMPLE;
        Imgproc.findContours(input, contours, hierarchy, mode, method);
    }

    private void findContourPos(List<MatOfPoint> contours, boolean telemetryOn) {
        mu = new ArrayList<>(contours.size());
        for(int i = 0; i < contours.size(); i++){
            mu.add(i, Imgproc.moments(contours.get(i), false));
            Moments p = mu.get(i);
            x = (int) (p.get_m10() / p.get_m00());
            y = (int) (p.get_m01() / p.get_m00());
            // Imgproc.circle(mat, new Point(x, y), 4, new Scalar(49,0,255));

        }
        contours.clear();

    }

}


