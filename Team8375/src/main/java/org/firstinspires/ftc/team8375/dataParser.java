/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375;

import java.util.Properties;

public class dataParser {

    public static boolean parseBool(Properties p, String s) {

    boolean b = Boolean.parseBoolean(p.getProperty(s));
    return b;

    }

    public static int parseInt(Properties p, String s) {
        int i = Integer.parseInt(p.getProperty(s));
        return i;
    }

    public static double parseDouble(Properties p, String s) {
        double d = Double.parseDouble(p.getProperty(s));
        return d;
    }

    public static float parseFLoat(Properties p, String s) {
        float f = Float.parseFloat(p.getProperty(s));
        return f;
    }

}
