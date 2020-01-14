/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375;

import android.util.Log;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.DogeCVDetector;
import com.disnodeteam.dogecv.filters.CbColorFilter;
import com.disnodeteam.dogecv.filters.DogeCVColorFilter;
import com.disnodeteam.dogecv.filters.GrayscaleFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * modified version of the built in SkystoneDetector from DogeCV 2020.4-alpha
 */
public class SkystoneDetect extends DogeCVDetector {
    public DogeCV.AreaScoringMethod areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Setting to decide to use MaxAreaScorer or PerfectAreaScorer

    private int pos1, pos2;

    //Create the default filters and scorers
    public DogeCVColorFilter blackFilter = new GrayscaleFilter(0, 25);
    public DogeCVColorFilter yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 70); //Default Yellow blackFilter

    public RatioScorer ratioScorer = new RatioScorer(1.9, 3); // Used to find the long face of the stone
    public MaxAreaScorer maxAreaScorer = new MaxAreaScorer( 7);                    // Used to find largest objects
    public PerfectAreaScorer perfectAreaScorer = new PerfectAreaScorer(5000,0.05); // Used to find objects near a tuned area value

    // Results of the detector
    private Point screenPosition = new Point(); // Screen position of the stone
    private Rect foundRect = new Rect(); // Found rect
    private Rect foundYellow = new Rect(); // Found yellow

    private Mat rawImage = new Mat();
    private Mat workingMat = new Mat();
    private Mat displayMat = new Mat();
    private Mat blackMask = new Mat();
    private Mat yellowMask = new Mat();
    private Mat hierarchy  = new Mat();

    public Point getScreenPosition() {
        return screenPosition;
    }

    public Rect foundRectangle() {
        return foundRect;
    }


    public SkystoneDetect() {
        detectorName = "Vulcan Detector";
    }

    @Override
    public Mat process(Mat input) {

        input.copyTo(rawImage);
        input.copyTo(workingMat);
        input.copyTo(displayMat);
        input.copyTo(blackMask);

         Imgproc.GaussianBlur(workingMat,workingMat,new Size(5,5),0);
        yellowFilter.process(workingMat.clone(), yellowMask);

        List<MatOfPoint> contoursYellow = new ArrayList<>();

        Imgproc.findContours(yellowMask, contoursYellow, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursYellow,-1,new Scalar(255,255,255),2);

        // Current result
        Rect bestRect = foundRect;
        double bestDifference = Double.MAX_VALUE; // MAX_VALUE since less difference = better
        Rect bestYellow = foundYellow;
        double bestYellowDiff = Double.MAX_VALUE;

        // Loop through the contours and score them, searching for the best result
        for(MatOfPoint cont : contoursYellow){
            double score = calculateScore(cont); // Get the difference score using the scoring API

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(cont);
            Imgproc.rectangle(displayMat, rect.tl(), rect.br(), new Scalar(0,0,255),2); // Draw rect

            // If the result is better then the previously tracked one, set this rect as the new best
            if(score < bestYellowDiff){
                bestYellowDiff = score;
                bestYellow = rect;
            }
        }

        Imgproc.rectangle(blackMask, bestRect.tl(), bestRect.br(), new Scalar(255,255,255), 1, Imgproc.LINE_4, 0);
        blackFilter.process(workingMat.clone(), blackMask);
        List<MatOfPoint> contoursBlack = new ArrayList<>();

        Imgproc.findContours(blackMask, contoursBlack, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursBlack,-1,new Scalar(40,40,40),2);

        for(MatOfPoint cont : contoursBlack){
            double score = calculateScore(cont); // Get the difference score using the scoring API

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(cont);
            Imgproc.rectangle(displayMat, rect.tl(), rect.br(), new Scalar(0,0,255),2); // Draw rect

            // If the result is better then the previously tracked one, set this rect as the new best
            if(score < bestDifference){
                bestDifference = score;
                bestRect = rect;
            }
        }
        if(bestRect != null) {
            // Show chosen result
            Imgproc.rectangle(displayMat, bestRect.tl(), bestRect.br(), bgrColor.BLUE,4);
            Imgproc.putText(displayMat, "Chosen", bestRect.tl(),0,1, bgrColor.WHITE);
            Imgproc.rectangle(displayMat, bestYellow.tl(), bestYellow.br(), new Scalar(0, 0, 255), 4);
            Imgproc.putText(displayMat, "Yellow", bestYellow.tl(),0,1, bgrColor.WHITE);

            screenPosition = new Point(bestRect.x, bestRect.y);
            foundRect = bestRect;
            found = true;
        }
        else {
            found = false;
        }

        Imgproc.line(displayMat, new Point(pos1, 0), new Point(pos1, 240), new Scalar(30, 255, 0), 2);
        Imgproc.line(displayMat, new Point(pos2, 0), new Point(pos2, 240), new Scalar(40, 255, 0), 2);
        switch (stageToRenderToViewport) {
            case THRESHOLD: {
                Imgproc.cvtColor(blackMask, blackMask, Imgproc.COLOR_GRAY2BGR);

                return blackMask;
            }
            case RAW_IMAGE: {
                return rawImage;
            }

            case CONTOURS: {
                return yellowMask;
            }

            default: {
                return displayMat;
            }
        }
    }

    public Integer getRelativePos() {
        int relativePos = 1;
        if(foundRect != null) {
            if(foundRect.x < pos1) {
                relativePos = 1;
            } else if(foundRect.x < pos2) {
                relativePos = 2;
            } else if(foundRect.x > pos2) {
                relativePos = 3;
            }
        } else
            return null;

        return relativePos;
    }

    public void setTargetPositions(int pos1, int pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    public void useDefaults() {
        addScorer(ratioScorer);

        // Add diffrent scorers depending on the selected mode
        if(areaScoringMethod == DogeCV.AreaScoringMethod.MAX_AREA){
            addScorer(maxAreaScorer);
        }

        if (areaScoringMethod == DogeCV.AreaScoringMethod.PERFECT_AREA){
            addScorer(perfectAreaScorer);
        }

        pos1 = 70;
        pos2 = 145;
    }
}