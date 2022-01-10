package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "CalibrateTeamElement", group = "Calibration")
public class CalibrateTeamElement extends AbstractCalibrate {
    @Override
    public void runOpMode() throws InterruptedException {
        calibrate("tse");
    }
}
