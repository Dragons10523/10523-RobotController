package org.firstinspires.ftc.teamcode.processors;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

public class Deadwheels {
    private final DcMotor leftYEncoder;
    private final DcMotor rightYEncoder;
    private final DcMotor XEncoder;

    private final double symmetricCircumference;
    private final double asymmetricCircumference;
    private final double inchesPerTick;

    private int leftYPosPrev;
    private int rightYPosPrev;
    private int XPosPrev;

    private long lastUpdateTime;

    public double currentX;
    public double currentY;
    public double currentAngle;

    public double xVelocity;
    public double yVelocity;
    public double angularVelocity;

    public boolean calibrationMode = false;

    public final double[][] calibration = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
    };

    /*
            {1.003473, 0.009575, -0.002154},
            {-0.009476, 1.007915, -0.000867},
            {0.438919, -0.358398, 0.973666}
     */

    // All measurements in inches
    public Deadwheels(DcMotor leftYEncoder, DcMotor rightYEncoder, DcMotor XEncoder, double lateralOffset, double forwardOffset, double inchesPerTick) {
        this.leftYEncoder = leftYEncoder;
        this.rightYEncoder = rightYEncoder;
        this.XEncoder = XEncoder;

        leftYPosPrev = leftYEncoder.getCurrentPosition();
        rightYPosPrev = rightYEncoder.getCurrentPosition();
        XPosPrev = XEncoder.getCurrentPosition();

        this.symmetricCircumference = VecUtils.TAU * lateralOffset;
        this.asymmetricCircumference = VecUtils.TAU * forwardOffset;
        this.inchesPerTick = inchesPerTick;
        lastUpdateTime = -1;
    }

    public void setTransform(VectorF position, double angle) {
        setTransform(position.get(0), position.get(1), angle);
    }

    public void setTransform(double x, double y, double angle) {
        currentX = (x * 24) + 12;
        currentY = (y * 24) + 12;
        currentAngle = angle;
        lastUpdateTime = -1;
    }

    public void wheelLoop() {
        int leftYPos = leftYEncoder.getCurrentPosition();
        int rightYPos = rightYEncoder.getCurrentPosition();
        int XPos = XEncoder.getCurrentPosition();

        // Invert left so directions are the same
        int leftYDelta = leftYPos - leftYPosPrev;
        int rightYDelta = rightYPos - rightYPosPrev;
        int XDelta = XPos - XPosPrev;

        // Positive turning right, negative for left
        double turnDelta = ((((rightYDelta - leftYDelta) / 2d) * inchesPerTick) / symmetricCircumference) * VecUtils.TAU;

        double robotYDelta = (leftYDelta + rightYDelta) * inchesPerTick / 2;
        turnDelta -= robotYDelta * 0.001;

        double robotTurnXDelta = (-turnDelta / VecUtils.TAU) * asymmetricCircumference;
        double robotXDelta = ((XDelta) * inchesPerTick) - robotTurnXDelta;

        if(calibrationMode) {
            currentX += robotXDelta;
            currentY += robotYDelta;
            currentAngle += turnDelta;

            leftYPosPrev = leftYPos;
            rightYPosPrev = rightYPos;
            XPosPrev = XPos;
            return;
        }

        double correctedXDelta = robotXDelta * calibration[0][0] + robotYDelta * calibration[0][1] + turnDelta * calibration[0][2];
        double correctedYDelta = robotXDelta * calibration[1][0] + robotYDelta * calibration[1][1] + turnDelta * calibration[1][2];
        double correctedTurnDelta = robotXDelta * calibration[2][0] + robotYDelta * calibration[2][1] + turnDelta * calibration[2][2];

        double updateAngle = currentAngle + (correctedTurnDelta / 2);
        double xGlobalDelta = Math.cos(-updateAngle) * correctedXDelta + Math.sin(-updateAngle) * correctedYDelta;
        double yGlobalDelta = -Math.sin(-updateAngle) * correctedXDelta + Math.cos(-updateAngle) * correctedYDelta;

        // Calculate velocities
        long currentUpdateTime = System.currentTimeMillis();
        if(lastUpdateTime != -1) {
            double deltaTime = (lastUpdateTime - currentUpdateTime) / 1000f;
            xVelocity = xGlobalDelta / deltaTime;
            yVelocity = yGlobalDelta / deltaTime;
            angularVelocity = correctedTurnDelta / deltaTime;
        }
        lastUpdateTime = currentUpdateTime;

        currentX += xGlobalDelta;
        currentY += yGlobalDelta;
        currentAngle += correctedTurnDelta;

        leftYPosPrev = leftYPos;
        rightYPosPrev = rightYPos;
        XPosPrev = XPos;
    }
}
