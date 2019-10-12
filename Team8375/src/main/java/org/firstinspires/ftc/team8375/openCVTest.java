package org.firstinspires.ftc.team8375;

import com.disnodeteam.dogecv.OpenCVPipeline;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class openCVTest extends OpenCVPipeline {

    public Mat processFrame(Mat rgba, Mat gray) {

        Imgproc.bilateralFilter(rgba, gray, 0, 100, 3);

        return gray;
    }

}
