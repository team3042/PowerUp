package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_SetPosition;import org.usfirst.frc.team3042.robot.commands.Arm_WaitForNearPosition;
import org.usfirst.frc.team3042.robot.commands.Auto_RunWhenDistanceLeft;
import org.usfirst.frc.team3042.robot.commands.Claw_Clamp;
import org.usfirst.frc.team3042.robot.commands.Claw_Intake;
import org.usfirst.frc.team3042.robot.commands.Claw_IntakeAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseTimed;
import org.usfirst.frc.team3042.robot.commands.Claw_StopAuton;
import org.usfirst.frc.team3042.robot.commands.Claw_Unclamp;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.paths.LeftScaleToCube;
import org.usfirst.frc.team3042.robot.paths.RightScaleToCube;
import org.usfirst.frc.team3042.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Two Cube.
 */
public class Right_RightScaleRightSwitch extends CommandGroup {

    public Right_RightScaleRightSwitch() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new Right_RightScale());
    	addParallel(new Auto_RunWhenDistanceLeft(new Claw_ReleaseTimed(0.5), 10.0));
    	Path driveToSwitch = new Path();
    	driveToSwitch.addStraight(20, 32);
    	addSequential(new DrivetrainAuton_Drive(driveToSwitch));
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
