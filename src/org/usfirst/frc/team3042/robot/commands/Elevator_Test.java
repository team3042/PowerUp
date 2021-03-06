package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Gamepad;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * We don't know if it will work
 */
public class Elevator_Test extends Command {

    public Elevator_Test() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = -Robot.oi.gamepad.getRawAxis(Gamepad.LEFT_JOY_Y_AXIS);
    	speed *= 1.0;
    	speed = Math.max(0.10, speed);
    	Robot.elevator.setPower(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
