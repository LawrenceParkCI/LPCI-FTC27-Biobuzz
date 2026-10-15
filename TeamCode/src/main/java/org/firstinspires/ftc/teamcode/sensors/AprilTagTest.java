package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection; // New for SDK 12.0

import java.util.List;

@Autonomous (name = "AprilTagTest", group = "Vision")
public class AprilTagTest extends LinearOpMode {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // 1. Initialize the AprilTag Processor
        aprilTagProcessor = new AprilTagProcessor.Builder()
                // You can add custom configuration here if needed
                .build();

        // 2. Initialize the Vision Portal with your webcam
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1")) // Ensure matches your robot config
                .addProcessor(aprilTagProcessor)
                .build();

        telemetry.addData("Status", "Initialized. Camera starting up...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // 3. Get the list of all active AprilTag detections
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            telemetry.addData("# Tags Detected", currentDetections.size());

            // 4. Loop through the detections
            for (AprilTagDetection detection : currentDetections) {

                // SKD 12.0 Check: Ensure it is a standard Single Tag (not a cluster)
                if (detection instanceof AprilTagSingleDetection) {

                    // Cast the generic detection into the SDK 12.0 single detection object
                    AprilTagSingleDetection singleTag = (AprilTagSingleDetection) detection;

                    // Ensure the tag is recognized in the active TagLibrary to get pose data
                    if (singleTag.metadata != null && singleTag.ftcPose != null) {

                        int tagId = singleTag.id;                        // Get Tag ID
                        String tagName = singleTag.metadata.name;        // Get Tag name from metadata

                        // Distance coordinates relative to the camera center:
                        double distanceAway = singleTag.ftcPose.range;   // Straight-line distance (inches)
                        double lateralOffset = singleTag.ftcPose.x;      // Distance left/right from center line
                        double forwardDist = singleTag.ftcPose.y;        // Straight ahead distance perpendicular to camera

                        // Output to Driver Station Telemetry
                        telemetry.addLine(String.format("\n==== (ID %d) %s ====", tagId, tagName));
                        telemetry.addData("Straight-line Distance (Range)", "%.2f inches", distanceAway);
                        telemetry.addData("Forward Component (Y)", "%.2f inches", forwardDist);
                        telemetry.addData("Side Component (X)", "%.2f inches", lateralOffset);
                    } else {
                        // Tag detected, but size/metadata unknown for calculations
                        telemetry.addLine(String.format("\nUnknown Tag ID %d (No Pose Data)", singleTag.id));
                    }
                }
            }

            telemetry.update();
            sleep(20); // Small pause to ease CPU usage
        }

        // Clean up and close camera stream when OpMode stops
        visionPortal.close();
    }
}
