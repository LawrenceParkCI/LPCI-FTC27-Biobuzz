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
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection; // New for SDK 12.0

import java.util.List;

@Autonomous(name = "AprilTagTest", group = "Autonomous")
public class AprilTagTest extends LinearOpMode {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // 1. Initialize the AprilTag processor and explicitly set output units to Centimeters
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        // 2. Initialize the VisionPortal with your webcam
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1")) // Change to matches your hardware config
                .addProcessor(aprilTagProcessor)
                .build();

        telemetry.addLine("Hardware Initialized. Camera streaming...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // 3. Retrieve generic detections list from the processor
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            telemetry.addData("# Tags Detected", currentDetections.size());

            // 4. Iterate through detections and apply SDK 12.0 subclass checks
            for (AprilTagDetection detection : currentDetections) {

                // Safe check for Single AprilTags (non-clusters)
                if (detection instanceof AprilTagSingleDetection) {
                    AprilTagSingleDetection singleTag = (AprilTagSingleDetection) detection;

                    // Ensure the SDK successfully resolved the 3D position
                    if (singleTag.ftcPose != null) {

                        // 'range' gives the exact straight-line distance from camera lens to the tag center
                        double absoluteDistanceCm = singleTag.ftcPose.range;

                        // 'y' gives the forward distance perpendicular to the camera sensor surface
                        double forwardDistanceCm = singleTag.ftcPose.y;

                        telemetry.addLine(String.format("\n==== (ID %d) Single Tag Found ====", singleTag.id));
                        telemetry.addData("Absolute Distance (Range)", "%.2f cm", absoluteDistanceCm);
                        telemetry.addData("Forward Distance (Y)", "%.2f cm", forwardDistanceCm);
                        telemetry.addData("Horizontal Offset (X)", "%.2f cm", singleTag.ftcPose.x);
                    } else {
                        // Triggers if the tag ID isn't registered in the active Library (no size metadata)
                        telemetry.addLine(String.format("\n==== (ID %d) Found but lacks positional math", singleTag.id));
                    }
                }
            }

            telemetry.update();
            sleep(20); // Small rest to lower CPU strain
        }

        // 5. Clean up resource when OpMode stops
        visionPortal.close();
    }
}
