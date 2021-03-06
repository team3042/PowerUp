package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.robot.commands.WaitForGameData;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Right_ChooseScaleThenSwitchSide extends CommandGroup {

    public Right_ChooseScaleThenSwitchSide() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new WaitForGameData());
    	addSequential(new Right_LeftOrRightScaleThenSwitch());

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
