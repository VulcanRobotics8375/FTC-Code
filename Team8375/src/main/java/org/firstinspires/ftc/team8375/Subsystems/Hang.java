package org.firstinspires.ftc.team8375.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hang {
    private HardwareMap hardwareMap;
    private DcMotor motor;

    public Hang(int targetPos) {
        init(targetPos);
    }

    public void init(int targetPos) {
        motor = hardwareMap.dcMotor.get("hang");
        motor.setTargetPosition(targetPos);
    }

    public void start(double power, boolean isTrue){
        if(isTrue) {
            motor.setPower(power);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(!motor.isBusy()){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }
    }

}
