package org.firstinspires.ftc.teamcode.Camera;


import android.annotation.SuppressLint;
import android.util.Size;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;


public class AprilTagPipeline {
    AprilTagProcessor aprilTag;
    VisionPortal visionPortal;
    WebcamName webcamName;
    Telemetry telemetry;
    public AprilTagPipeline(WebcamName webCamName, Telemetry telemetry){
        this.webcamName = webCamName;
        this.telemetry = telemetry;
        initAprilTag();
    }
    public void updateAprilTagPipeline(){
        telemetryAprilTag();
        telemetry.update();
        //TODO: DO THE STUFF
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void initAprilTag(){
        aprilTag = new AprilTagProcessor.Builder().setDrawAxes(true).setDrawCubeProjection(true).build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        telemetry.addData("webcamName", webcamName);
        telemetry.update();
        builder.setCamera(webcamName);

        //builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTag);

        visionPortal = builder.build();
    }
    @SuppressLint("DefaultLocale")
    private void telemetryAprilTag(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        for(AprilTagDetection detection : currentDetections){
            if(detection.metadata != null){
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            }
            else{
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f  (pixels)", detection.center.x, detection.center.y));
            }
        }
        //XYZ === X (RIGHT), Y (Forward), Z (Up) dist.
        //PRY Pitch, Roll & Yaw (XYZ Rotation)
        //RBE = Range, Bearing & Elevation
    }
    public ArrayList<AprilTagDetection> getDetections(){
        return aprilTag.getDetections();
    }
    public AprilTagPoseFtc getClosestAprilTagLocation() {
        ArrayList<AprilTagDetection> detections = aprilTag.getDetections();
        if (detections.isEmpty()) {
            return null;
        }
        double closestTagRange = 100;
        int closestTagID = -1;
        for (AprilTagDetection detection : detections) {
            double tagRange = detection.ftcPose.range;
            if (tagRange < closestTagRange) {
                closestTagRange = tagRange;
                closestTagID = detection.id;
            }
        }
        if(closestTagID == -1){
            return null;
        }
        else{
            return detections.get(closestTagID).ftcPose;
        }
    }
    public int getClosestAprilTagID(){
        ArrayList<AprilTagDetection> detections = aprilTag.getDetections();
        if (detections.isEmpty()) {
            return -1;
        }
        double closestTagRange = 100;
        int closestTagID = -1;
        for (AprilTagDetection detection : detections) {
            double tagRange = detection.ftcPose.range;
            if (tagRange < closestTagRange) {
                closestTagRange = tagRange;
                closestTagID = detection.id;
            }
        }
        return closestTagID;
    }
    public double[] getTagLocationRelativeToOrigin(int id){
        return null;
    }
}
