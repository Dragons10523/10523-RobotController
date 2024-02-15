package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.Mushu;
import org.firstinspires.ftc.teamcode.Subsystems.DroneSub;
import org.firstinspires.ftc.teamcode.Subsystems.InExtakeSub;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystems;
import org.firstinspires.ftc.teamcode.Subsystems.ToolSubsystem;
import org.firstinspires.ftc.teamcode.commands.InExtakeCommands.ExtakeSpin;
import org.firstinspires.ftc.teamcode.commands.InExtakeCommands.FlipServo;
import org.firstinspires.ftc.teamcode.commands.InExtakeCommands.RetractServo;

public class ButtonCall extends CommandBase {
    Mushu mushu;
    Button Dpad_DOWN_Tool, A;
    Button Dpad_UP_Tool, Y;
    Button Left_Bumper;
    Button Right_Bumper;
    Button BACK;
    Button X, Y_Driver;
    Button B;

    InExtakeSub m_InExtakeSub;
    ToolSubsystem m_toolSub;
    MecanumDriveSubsystems m_driveSub;
    DroneSub droneSub;
    public ButtonCall(Mushu mushu, InExtakeSub sub, ToolSubsystem toolSub, MecanumDriveSubsystems m_driveSub, DroneSub droneSub){
        this.mushu = mushu;
        m_InExtakeSub = sub;
        m_toolSub = toolSub;
        this.m_driveSub = m_driveSub;
        this.droneSub = droneSub;

    }
    public void initialize(){
        Dpad_DOWN_Tool = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.DPAD_DOWN
        );
        Dpad_UP_Tool = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.DPAD_UP
        );
        Left_Bumper = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.LEFT_BUMPER
        );
        Right_Bumper = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.RIGHT_BUMPER
        );
        BACK = new GamepadButton(
                mushu.driverGamepad, GamepadKeys.Button.BACK
        );
        Y = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.Y
        );
        A = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.A
        );
        X = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.X
        );
        B = new GamepadButton(
                mushu.toolGamepad, GamepadKeys.Button.B
        );
        Y_Driver = new GamepadButton(
                mushu.driverGamepad, GamepadKeys.Button.Y
        );

//        mushu.hangMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        mushu.intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE); disabled for power issues



        Dpad_UP_Tool.whenPressed(new FlipServo(m_InExtakeSub));
        Dpad_DOWN_Tool.whenPressed(new RetractServo(m_InExtakeSub));

        Right_Bumper.whileHeld(new ExtakeSpin(m_InExtakeSub, .75)).whenReleased(new ExtakeSpin(m_InExtakeSub, 0));
        Left_Bumper.whileHeld(new ExtakeSpin(m_InExtakeSub, -.75)).whenReleased(new ExtakeSpin(m_InExtakeSub, 0));

        BACK.whenPressed(new InstantCommand(m_driveSub::resetIMU));


        Y.whileHeld(new HangCommand(m_toolSub, -1)).whenReleased(new HangCommand(m_toolSub, 0));
        A.whileHeld(new HangCommand(m_toolSub, 1)).whenReleased(new HangCommand(m_toolSub, 0));
        B.whenPressed(new InstantCommand(droneSub::flipDrone));

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
