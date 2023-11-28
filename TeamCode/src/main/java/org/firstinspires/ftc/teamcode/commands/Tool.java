package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Mushu;
import org.firstinspires.ftc.teamcode.Subsystems.InExtakeSub;
import org.firstinspires.ftc.teamcode.Subsystems.ToolSubsystem;

public class Tool extends CommandBase {
    private final ToolSubsystem toolSubsystem;
    GamepadEx toolGamepad;

    Mushu mushu;

    public Tool(GamepadEx toolGamepad, ToolSubsystem subsystem, Mushu mushu){
        toolSubsystem = subsystem;
        this.toolGamepad = toolGamepad;
        this.mushu = mushu;
    }

    @Override
    public void initialize(){
        //TODO: need to add tool at the end of the intake

    }
    @Override
    public void execute(){
        toolSubsystem.manualIntake(toolGamepad.getRightY());
        toolSubsystem.manualExtake(toolGamepad.getLeftY());
    }

    public static class InExtake extends CommandBase{
        GamepadEx gamepad;
        double intakePower;
        double extakePower;

        InExtakeSub sub;
        public InExtake(GamepadEx gamepad, InExtakeSub sub){
            this.gamepad = gamepad;
            this.sub = sub;
        }
        @Override
        public void execute(){
           intakePower = gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER); // INTAKE IS LEFT TRIGGER
           extakePower = gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER); // INTAKE IS RIGHT TRIGGER

            sub.runIN(intakePower);
            sub.runEX(extakePower);
        }
    }
}
