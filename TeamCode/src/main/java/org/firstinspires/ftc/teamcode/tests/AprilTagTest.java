package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.autonomous.AprilTags;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

@TeleOp
public class AprilTagTest extends OpMode {
    AprilTags aprilTags = new AprilTags();

    Camera cam = new Camera();

    @Override
    public void init() {
        aprilTags.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        AprilTagDetection detection = aprilTags.getTagBySpecificId(1);

        if (detection instanceof AprilTagSingleDetection) {
            AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;

            // 2. Ensure pose data exists before accessing coordinates
            if (singleDet.ftcPose != null) {
                double distanceCm = singleDet.ftcPose.range;

                telemetry.addData("Distance to Tag", "%.2f cm", distanceCm);
            }
        }

    }
}
