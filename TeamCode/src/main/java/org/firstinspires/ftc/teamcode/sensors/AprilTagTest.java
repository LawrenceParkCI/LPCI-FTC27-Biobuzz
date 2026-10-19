package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection; // New import for SDK 12.0+
import java.util.List;

@Autonomous(name = "AprilTagTest", group = "Autonomous")
public class AprilTagTest extends LinearOpMode {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // 1. Initialize the AprilTag Processor using the Builder pattern
        aprilTagProcessor = new AprilTagProcessor.Builder()
                // Configure the SDK to output all translations in CM instead of default Inches
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .build();

        // 2. Initialize the VisionPortal and link the webcam
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Camera")) // Match your robot configuration name
                .addProcessor(aprilTagProcessor)
                .build();

        telemetry.addData("Status", "Initialized. Camera Streaming...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Processing AprilTags...\n");

            // 3. Retrieve the multi-type detection list
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            if (currentDetections.isEmpty()) {
                telemetry.addLine("No AprilTags detected in view.");
            } else {
                for (AprilTagDetection detection : currentDetections) {

                    // 4. CRITICAL SDK 12.0+ PATTERN: Check if it is a Single Tag Detection
                    if (detection instanceof AprilTagSingleDetection) {
                        // Cast the base detection to access specific single-tag properties safely
                        AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;

                        // Check metadata explicitly to prevent NullPointerExceptions on unlisted/custom tags
                        if (singleDet.metadata != null && singleDet.ftcPose != null) {
                            telemetry.addLine(String.format("==== Single Tag ID %d ====", singleDet.id));

                            // Distance values are automatically scaled to centimeters via the builder configurations
                            telemetry.addData("Straight-line Distance (Range)", "%.2f cm", singleDet.ftcPose.range);
                            telemetry.addData("Forward Distance (Y)", "%.2f cm", singleDet.ftcPose.y);
                            telemetry.addData("Horizontal Offset (X)", "%.2f cm", singleDet.ftcPose.x);
                            telemetry.addData("Vertical Offset (Z)", "%.2f cm", singleDet.ftcPose.z);
                            telemetry.addData("Bearing (Angle to center)", "%.2f deg", singleDet.ftcPose.bearing);
                        } else {
                            telemetry.addLine(String.format("Detected Tag ID %d (No Metadata / Pose Data available)", singleDet.id));
                        }

                    } else {
                        // This handles AprilTagClusterDetection instances if your camera encounters a multi-tag macro asset
                        telemetry.addLine("Detected a Cluster Tag backdrop; ignoring single-tag math.");
                    }
                }
            }

            telemetry.update();
            sleep(20); // Minor cooling loop for telemetry throughput
        }

        // 5. Clean up vision resources
        visionPortal.close();
    }
}
