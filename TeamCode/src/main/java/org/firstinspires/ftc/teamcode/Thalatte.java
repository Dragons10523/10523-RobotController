package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Thalatte {
    public DcMotor frontLeft, frontRight, backLeft, backRight, shooterBack, shooterFront, intake, vwomp;
    public Servo localizationFrontLeft, localizationFrontRight, localizationBackLeft, localizationBackRight, vwompClampLeft, vwompClampRight;
    public BNO055IMU imu;

    public HardwareMap hwmap;

    public Thalatte(HardwareMap hwmap){
        this.hwmap = hwmap;

//        frontLeft = hwmap.get(DcMotor.class,"frontLeft");
//        frontRight = hwmap.get(DcMotor.class,"frontRight");
//        backLeft = hwmap.get(DcMotor.class,"backLeft");
//        backRight = hwmap.get(DcMotor.class,"backRight");
//
//        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
//        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
//
//        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooterBack = hwmap.get(DcMotor.class, "shooterBack");
        shooterFront = hwmap.get(DcMotor.class, "shooterFront");

//        intake = hwmap.get(DcMotor.class, "intake");
//        vwomp = hwmap.get(DcMotor.class, "vwomp");
//
//        localizationFrontLeft = hwmap.get(Servo.class, "localizationFrontLeft");
//        localizationFrontRight = hwmap.get(Servo.class, "localizationFrontRight");
//        localizationBackLeft = hwmap.get(Servo.class, "localizationBackLeft");
//        localizationBackRight = hwmap.get(Servo.class, "localizationBackRight");
//
//        vwompClampLeft = hwmap.get(Servo.class, "vwompClampLeft");
//        vwompClampRight = hwmap.get(Servo.class, "vwompClampRight");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwmap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
}