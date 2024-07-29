package org.firstinspires.infoedu.opmodes;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
@TeleOp(name="AprilTagDetection")
public class AprilTagDetection extends LinearOpMode {

    @Override
    public void runOpMode() {

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.RADIANS)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640,480))
                .build();

        waitForStart();

        while(opModeIsActive()) {

            if(tagProcessor.getDetections().size() > 0) {
                org.firstinspires.ftc.vision.apriltag.AprilTagDetection tag = tagProcessor.getDetections().get(0);

                telemetry.addData("X", tag.ftcPose.x);
                telemetry.addData("Y", tag.ftcPose.y);
                telemetry.addData("heading", tag.ftcPose.bearing);
                telemetry.addData("heading2", tag.ftcPose.yaw);
            }

            telemetry.update();
        }
        visionPortal.close();
    }
}
