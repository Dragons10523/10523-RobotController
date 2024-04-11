package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Terminator Mode")
public class Drive extends OpMode {
    Warbotron warbotron;

    // Seconds to max speed
    public final double SNEAK_ACCEL = 0.125;

    @Override
    public void init() {
        warbotron = new Warbotron(hardwareMap);
    }

    double leftPower = 0;
    double rightPower = 0;

    ElapsedTime deltaTime = new ElapsedTime();

    @Override
    public void loop() {
        double leftTargetPower = -gamepad1.left_stick_y;
        double rightTargetPower = -gamepad1.right_stick_y;

        if(gamepad1.a){
            warbotron.spatula.setPosition(1);
            warbotron.spatula.setPosition(-1);
        }

        if(gamepad1.right_bumper) {
            leftPower += (leftTargetPower - leftPower) * deltaTime.seconds() / SNEAK_ACCEL;
            rightPower += (rightTargetPower - rightPower) * deltaTime.seconds() / SNEAK_ACCEL;
        } else {
            leftPower = leftTargetPower;
            rightPower = rightTargetPower;
        }


        deltaTime.reset();

        warbotron.frontLeft.setPower(leftPower);
        warbotron.frontRight.setPower(rightPower);
        warbotron.backLeft.setPower(leftPower);
        warbotron.backRight.setPower(rightPower);

        warbotron.flyWheel.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

        telemetry.addData("Left", leftPower);
        telemetry.addData("Right", rightPower);
    }
}
