package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

public class Kai {
    // Also used for deadwheels. Use back motors for both y axis, front left for the x axis
    public final DcMotor frontLeft, frontRight, backLeft, backRight;

    public final DcMotor horizontalLift;
    public final DcMotorEx turntable;
    public final DcMotor armLiftA, armLiftB;
    public final Servo claw, clawFlup, clawTwist;

    public final DistanceSensor clawSensor;

    public final BNO055IMU imu;

    public OpenCvWebcam frontCamera;

    public Deadwheels deadwheels;
    public HardwareMap hwmap;

    public Kai(HardwareMap hwmap) {
        this.hwmap = hwmap;

        // Map electronics
        frontLeft = hwmap.get(DcMotor.class, "frontLeft");
        frontRight = hwmap.get(DcMotor.class, "frontRight");
        backLeft = hwmap.get(DcMotor.class, "backLeft");
        backRight = hwmap.get(DcMotor.class, "backRight");

        horizontalLift = hwmap.get(DcMotor.class, "horizontalLift");
        turntable = hwmap.get(DcMotorEx.class, "turntable");
        armLiftA = hwmap.get(DcMotor.class, "armLiftA");
        armLiftB = hwmap.get(DcMotor.class, "armLiftB");

        claw = hwmap.get(Servo.class, "claw");
        clawFlup = hwmap.get(Servo.class, "clawFlup");
        clawTwist = hwmap.get(Servo.class, "clawTwist");

        clawSensor = hwmap.get(DistanceSensor.class, "clawSensor");

        // Set up IMU parameters
        BNO055IMU.Parameters parameters             = new BNO055IMU.Parameters();
        parameters.angleUnit                        = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit                        = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile              = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled                   = false;
        parameters.loggingTag                       = "IMU";

        imu = hwmap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        WebcamName frontWebcamName = hwmap.get(WebcamName.class, "Front Camera");
        frontCamera = OpenCvCameraFactory.getInstance().createWebcam(frontWebcamName);

        // Set motor directions
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turntable.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armLiftA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armLiftB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set up encoders
        horizontalLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turntable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLiftA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLiftB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        horizontalLift.setTargetPosition(0);
        turntable.setTargetPosition(0);
        armLiftA.setTargetPosition(0);
        armLiftB.setTargetPosition(0);

        horizontalLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turntable.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLiftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLiftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        horizontalLift.setPower(1);
        turntable.setPower(1);
        armLiftA.setPower(1);
        armLiftB.setPower(1);

        deadwheels = new Deadwheels(backLeft, backRight, frontLeft, 7, 0, Math.PI/4096);
    }

    public double getHeading(){
        return deadwheels.currentAngle;
    }
}
