/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375;

import org.opencv.core.Scalar;

/**
 * because why not
 */
public interface bgrColor {
    Scalar GREEN = new Scalar(0, 255, 0);
    Scalar RED = new Scalar(0, 0, 255);
    Scalar BLUE = new Scalar(255, 0, 0);
    Scalar VULCAN = new Scalar(12, 21, 140);
    Scalar PINK = new Scalar(255, 0, 255);
    Scalar PURPLE = new Scalar(255, 51, 51);
    Scalar CYAN = new Scalar(255, 255, 0);
    Scalar TURQUOISE = new Scalar(208, 224, 64);
    Scalar TEAL = new Scalar(128, 128, 0);
    Scalar YELLOW = new Scalar(0, 255, 255);
    Scalar FOREST_GREEN = new Scalar(0, 102, 51);
    Scalar ORANGE = new Scalar(0, 128, 255);

    Scalar WHITE = new Scalar(255, 255, 255);
    Scalar BLACK = new Scalar(0, 0, 0);
    Scalar GRAY = new Scalar(96, 96, 96);

    Scalar PASTEL_RED = new Scalar(204, 204, 255);
    Scalar PASTEL_YELLOW = new Scalar(204, 255, 255);
    Scalar PASTEL_GREEN = new Scalar(204, 255, 204);
    Scalar PASTEL_BLUE = new Scalar(255, 255, 204);
    Scalar PASTEL_PURPLE = new Scalar(255, 204, 204);
    Scalar PASTEL_PINK = new Scalar(229, 204, 255);

}
