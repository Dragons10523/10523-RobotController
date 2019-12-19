package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
@TeleOp(name = "Limit Switch")
public class limitSwitch extends OpMode {
    DigitalChannel limitSwitch;
    @Override
    public void init() {
        limitSwitch = hardwareMap.get(DigitalChannel.class, "bzzt");
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void loop() {
        telemetry.addData("Limit",limitSwitch.getState());
        telemetry.update();
    }
}
