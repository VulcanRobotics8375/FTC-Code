/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.vulcanrobotics.ftcrobotcore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class MotionLogger {

    private HashMap<Integer, HashMap<String, Object>> gamepadArray = new HashMap<>();

    public MotionLogger() {}

    public void saveGamepadState(int step, Gamepad gamepad){
        gamepadArray.put(step, CoreGamepad.toHashMap(gamepad));
    }

    public void copyGamepadArrayToJSON(String fileName){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(fileName).getAbsoluteFile(), gamepadArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path has to be the FULL PATH of the file, i think
     */
    public void loadGamepadArrayFromJSON(String path) {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();
        File file = new File(path);
        if ( file.exists() ) {
            //byte[] buffer = new byte[(int) new File(filePath).length()];
            FileInputStream fis = null;
            try {
                //f = new BufferedInputStream(new FileInputStream(filePath));
                //f.read(buffer);

                fis = new FileInputStream(file);
                char current;
                while (fis.available() > 0) {
                    current = (char) fis.read();
                    result.append(current);
                }
                gamepadArray = mapper.readValue(result.toString(), new TypeReference<HashMap<Integer, HashMap<String, Object>>>(){});

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IOException ignored) {
                    }
            }
        }
    }

    public void getValue(int n, String key) {
        Objects.requireNonNull(gamepadArray.get(n)).get(key);
    }

}
