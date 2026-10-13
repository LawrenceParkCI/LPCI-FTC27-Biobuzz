package org.firstinspires.ftc.teamcode.tests;


import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;
import java.util.List;

@TeleOp(name = "Console AprilTag Detector 12.0", group = "Concept")
public class ApriltagStan extends LinearOpMode {

    // Core vision components
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // 1. Build and configure the AprilTag processor from scratch
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .build();

        // 2. Build and configure the Vision Portal with a standard hardware webcam
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .build();

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // 3. Retrieve the full list of active detections
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            // 4. Safely loop through and parse detections based on FTC 12.0+ specifications
            for (AprilTagDetection detection : currentDetections) {

                // Ensure it is a single tag and not a structural AprilTagClusterDetection
                if (detection instanceof AprilTagSingleDetection) {
                    AprilTagSingleDetection singleTag = (AprilTagSingleDetection) detection;

                    // Ensure tag has valid metadata/calibration sizes for 3D pose calculations
                    if (singleTag.metadata != null && singleTag.ftcPose != null) {
                        int tagId = singleTag.id;
                        double distance = singleTag.ftcPose.range; // Range is straight-line 3D distance in inches

                        // 5. Print to the Android Studio System Logcat Console
                        Log.d("AprilTagConsole", String.format("Tag ID: %d | Distance (Range): %.2f inches", tagId, distance));
                    }
                }
            }

            // Keep driver station telemetry updated so you know the camera is running
            telemetry.addData("Detections found", currentDetections.size());
            telemetry.update();

            // Small sleep to reduce resource strain and keep log readability human-friendly
            sleep(100);
        }

        // 6. Clean up camera resources when the OpMode closes
        visionPortal.close();
    }
}
