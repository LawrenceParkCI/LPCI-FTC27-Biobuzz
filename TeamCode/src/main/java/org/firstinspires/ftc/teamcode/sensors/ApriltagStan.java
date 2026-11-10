package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

@Autonomous(name = "ApriltagStan", group = "Autonomous")
public class ApriltagStan extends LinearOpMode {

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;

    @Override
    public void runOpMode() {
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES) // Changed to CM
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .build();

        waitForStart();

        while (opModeIsActive()) {
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            for (AprilTagDetection detection : currentDetections) {
                if (detection instanceof AprilTagClusterDetection) {
                    AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;

                    if (clusterDetection.ftcPose != null) {
                        // Displays Cluster Name, Distance, and Heading/Bearing Angle
                        telemetry.addData("Cluster Target", clusterDetection.metadata.name);
                        telemetry.addData("Distance", String.format("%.2f cm", clusterDetection.ftcPose.range));
                        telemetry.addData("Angle (Bearing)", String.format("%.2f deg", clusterDetection.ftcPose.bearing));
                    }
                }
            }

            telemetry.update();
            sleep(20);
        }
        visionPortal.close();
    }
}
