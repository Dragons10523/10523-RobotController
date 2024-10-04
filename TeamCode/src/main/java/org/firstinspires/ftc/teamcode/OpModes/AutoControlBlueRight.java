package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Control;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.Susbsystem.AutoUtils;

import java.util.function.BooleanSupplier;

@Autonomous(name = "Auto_Blue_Right")
public class AutoControlBlueRight extends OpMode {
    public RobotClass robot;
    private final Control.FieldSide fieldSide = Control.FieldSide.BLUE_RIGHT;
    private static boolean stop = false;
    public static BooleanSupplier isStopRequested = () -> stop;
    AutoUtils autoUtils;

    @Override
    public void init() {
        robot = new RobotClass(hardwareMap);
        autoUtils = new AutoUtils(robot, telemetry);
        robot.setDirection();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void start() {
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoTurn(0);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoDrive(0,20);
//        autoUtils.AutoDrive(0,-20);
//        autoUtils.AutoTurn(0);
    }

    @Override
    public void loop() {
        //aprilTagPipeline.updateAprilTagPipeline();
    }

    @Override
    public void stop() {
        if (robot == null) return; // ensures that stop() is not called before initialization
        stop = true;
        robot.driveMotors.get(RobotClass.MOTORS.FRONT_LEFT).setPower(0.0);
        robot.driveMotors.get(RobotClass.MOTORS.FRONT_RIGHT).setPower(0.0);
        robot.driveMotors.get(RobotClass.MOTORS.BACK_LEFT).setPower(0.0);
        robot.driveMotors.get(RobotClass.MOTORS.BACK_RIGHT).setPower(0.0);
        requestOpModeStop();
    }


}