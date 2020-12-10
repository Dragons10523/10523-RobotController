package org.firstinspires.ftc.teamcode;

import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Collection;

@TeleOp(name = "Test")
public class Test extends Localization {
    @Override
    public void runOpMode() throws InterruptedException {
        alize();
        waitForStart();
        startLocalization();
        while(opModeIsActive()) {
            telemetry.addData("Mouse X",     thalatte.mouse.getMouseX());
            telemetry.addData("Mouse Y",     thalatte.mouse.getMouseY());
            telemetry.addData("Field Theta", thalatte.mouse.theta      );
            telemetry.addData("Field X",     thalatte.mouse.x          );
            telemetry.addData("Field Y",     thalatte.mouse.y          );
            telemetry.update();
        }
    }
}
