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

    public void loop() {
        if (aprilTags.getDetectedTags() != null) {
            aprilTags.getDetectedTags();
            telemetry.addData("# of tags detected:", aprilTags.getDetectedTags().size());

            aprilTags.telemetryAprilTag();
        }
    }
}
