package org.firstinspires.ftc.teamcode.autonomous;

import android.util.Size;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class AprilTags {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    // Default constructor: initializes with default webcam name "Webcam 1"
    public AprilTags(HardwareMap hardwareMap) {
        this(hardwareMap, "Webcam 1");
    }

    // Overloaded constructor: allows custom webcam configuration name
    public AprilTags(HardwareMap hardwareMap, String webcamName) {
        // Logitech C270 is pre-calibrated in FTC SDK 12.0 at 640x480
        aprilTag = new AprilTagProcessor.Builder()
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, webcamName))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(aprilTag)
                .build();
    }

    // Returns a list of all detected AprilTag clusters
    public List<AprilTagClusterDetection> getClusterDetections() {
        List<AprilTagClusterDetection> clusters = new ArrayList<>();
        if (aprilTag == null) {
            return clusters;
        }

        for (AprilTagDetection detection : aprilTag.getDetections()) {
            if (detection instanceof AprilTagClusterDetection) {
                clusters.add((AprilTagClusterDetection) detection);
            }
        }
        return clusters;
    }

    // Returns the first visible cluster, or null if none are detected
    public AprilTagClusterDetection getFirstCluster() {
        List<AprilTagClusterDetection> clusters = getClusterDetections();
        return clusters.isEmpty() ? null : clusters.get(0);
    }

    // Returns a specific cluster matching the given metadata name, or null if not found
    public AprilTagClusterDetection getClusterByName(String targetName) {
        if (targetName == null) {
            return null;
        }

        for (AprilTagClusterDetection cluster : getClusterDetections()) {
            if (cluster.metadata != null && targetName.equals(cluster.metadata.name)) {
                return cluster;
            }
        }
        return null;
    }

    // Checks if any cluster is currently visible
    public boolean hasClusterDetection() {
        return !getClusterDetections().isEmpty();
    }

    // Pauses camera streaming to reduce CPU load when vision is not needed
    public void stopStreaming() {
        if (visionPortal != null) {
            visionPortal.stopStreaming();
        }
    }

    // Resumes camera streaming
    public void resumeStreaming() {
        if (visionPortal != null) {
            visionPortal.resumeStreaming();
        }
    }

    // Closes the VisionPortal when the OpMode stops
    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
