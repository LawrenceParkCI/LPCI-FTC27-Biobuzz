package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit; // Required for centimeters
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;
@Disabled

@TeleOp(name = "CameraTestStanley", group = "TeleOp")
public class CameraTestStanley extends LinearOpMode {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // 1. Initialize the AprilTag Processor and change output units to CM
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES) // <--- Switched to centimeters here
                .build();

        // 2. Initialize the Vision Portal using your webcam
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .addProcessor(aprilTagProcessor)
                .build();

        telemetry.addLine("Camera Initialized in CM. Press Play to start reading tags.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            telemetry.addData("# Total Objects Spotted", currentDetections.size());

            for (AprilTagDetection detection : currentDetections) {

                if (detection instanceof AprilTagSingleDetection) {
                    AprilTagSingleDetection singleTag = (AprilTagSingleDetection) detection;
                    int tagId = singleTag.id; // The ID is always safe to read!

                    // CRITICAL PROTECTION: Check if metadata and pose data actually exist
                    if (singleTag.metadata != null && singleTag.ftcPose != null) {

                        // This block is now completely safe from NullPointerExceptions!
                        telemetry.addLine(String.format("\n=== Found Single Tag ID: %d ===", tagId));
                        telemetry.addData("Tag Name", singleTag.metadata.name);
                        telemetry.addData("Distance X (cm)", singleTag.ftcPose.x);
                        telemetry.addData("Distance Y (cm)", singleTag.ftcPose.y);
                        telemetry.addData("Yaw Angle (degrees)", singleTag.ftcPose.yaw);

                        // Put your actual driving/alignment logic here
                        if (tagId == 11) {
                            // Safe to drive based on singleTag.ftcPose.x
                        }

                    } else {
                        // The camera saw the tag, but cannot calculate distances
                        telemetry.addLine(String.format("\nSpotted Tag ID %d, but missing pose/metadata size data!", tagId));
                    }

                } else {
                    telemetry.addLine("\nSkipped a Cluster/Group Detection object.");
                }
            }


            telemetry.update();
            sleep(20);
        }

        visionPortal.close();
    }
}
