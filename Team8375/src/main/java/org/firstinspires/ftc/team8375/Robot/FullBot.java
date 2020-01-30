/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.firstinspires.ftc.team8375.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team8375.Subsystems.*;

public class FullBot extends Robot {
    //list of subsystems
    public Arm arm = new Arm();
    public AutoArm autoArm = new AutoArm();
    public Drivetrain drivetrain = new Drivetrain();
    public Foundation foundation = new Foundation();
    public Intake intake = new Intake();
    public SkystoneDetect SkystoneDetect = new SkystoneDetect();

    public FullBot(HardwareMap hwMap) {
        //add all the subsystems to the subsystems List<>
        subsystems.add(arm);
        subsystems.add(autoArm);
        subsystems.add(drivetrain);
        subsystems.add(foundation);
        subsystems.add(intake);
        subsystems.add(SkystoneDetect);
        
        //run the subsystem foreach loop to initialize all subsystems
        createAll(hwMap, subsystems);

    }

}
