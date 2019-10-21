package org.firstinspires.ftc.team8375.Autonomous;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.disnodeteam.dogecv.DogeCV;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.openftc.easyopencv.OpenCvCamera;

@TeleOp(name="vision test", group="test")
public class visionTest extends OpMode {

    private OpenCvCamera phoneCam;
    private SkystoneDetector detector;
    private Camera camera;
    Parameters params;

    @Override
    public void init(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        phoneCam.openCameraDevice();
        detector = new SkystoneDetector();
        phoneCam.setPipeline(detector);

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);


        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                telemetry.addData("error", e.getMessage());
                telemetry.update();
            }



        }

    }

    @Override
    public void start() {
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
}
