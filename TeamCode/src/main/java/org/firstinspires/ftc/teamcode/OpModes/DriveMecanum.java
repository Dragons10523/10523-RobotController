package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Control;
import org.firstinspires.ftc.teamcode.RobotClass;

@TeleOp
public class DriveMecanum extends Control {

    @Override
    public void loop() {
        super.loop();
        double leftY = gamepad1.left_stick_y;// < .05 ? 0 : gamepad1.left_stick_y;
        double leftX = gamepad1.left_stick_x;// < .05 ? 0 : gamepad1.left_stick_x;
        double turn = gamepad1.left_trigger - gamepad1.right_trigger;
        boolean A_2 = gamepad2.a;
        boolean B_2 = gamepad2.b;
        double intakePower = gamepad2.left_trigger - gamepad2.right_trigger;

        double liftPower = gamepad2.dpad_down ? -1 : 0;
        liftPower += gamepad2.dpad_up ? 1 : 0;
        double armPower = gamepad2.left_bumper ? -1 : 0;
        armPower += gamepad2.right_bumper ? 1 : 0;
        SparkFunOTOS.Pose2D pose2D = robot.opticalSensor.getPosition();

        telemetry.addData("driveMode", driveMode);
//        telemetry.addData("liftMode", liftMode);
//        telemetry.addData("liftPos", liftState);
//        telemetry.addData("LeftLiftEncoder", robot.Motors.get(RobotClass.MOTORS.LIFT_LEFT).getCurrentPosition());
//        telemetry.addData("RightLiftEncoder", robot.Motors.get(RobotClass.MOTORS.LIFT_RIGHT).getCurrentPosition());
//        telemetry.addData("armEncoder", robot.Motors.get(RobotClass.MOTORS.ARM_FLIP).getCurrentPosition());
//        telemetry.addData("armState", armState);
        telemetry.addLine(String.format("XYH %.3f %.3f %.3f", pose2D.x, pose2D.y, pose2D.h));
        //up EncoderPos:
        //down Encoder Pos:
        mecanumDrive(leftY, leftX, turn);
        resetIMU(gamepad1.back);
//        liftPower(liftPower);
        switchDriveMode(gamepad1.start);
//        switchLiftMode(gamepad2.start);
//        intakePower(intakePower);
//        manualArmFlip(armPower);
//        flipArm(A_2);
//        flipBucket(B_2);
    }
}