/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;

public class CoreGamepad {

    public static HashMap<String, Object> toHashMap(Gamepad gamepad) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", gamepad.id);
        map.put("lx", gamepad.left_stick_x);
        map.put("ly", gamepad.left_stick_y);
        map.put("rx", gamepad.right_stick_x);
        map.put("ry", gamepad.right_stick_y);
        map.put("lt", gamepad.left_trigger);
        map.put("rt", gamepad.right_trigger);
        map.put("a", gamepad.a);
        map.put("b", gamepad.b);
        map.put("x", gamepad.x);
        map.put("y", gamepad.y);
        map.put("left_bumper", gamepad.left_bumper);
        map.put("right_bumper", gamepad.right_bumper);
        map.put("right_stick_button", gamepad.right_stick_button);
        map.put("left_stick_button", gamepad.left_stick_button);

        return map;
    }
}
