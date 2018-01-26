package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Logger;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Elevator_setPosition extends Command {
	/** Configuration Constants ***********************************************/
	public static final Logger.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	
	/** Instance Variables ****************************************************/
	private Logger log = new Logger(LOG_LEVEL, getName());
	private Elevator.Position position;

    public Elevator_setPosition(Elevator.Position position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Logger.Level.TRACE);
    	
    	Robot.elevator.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	log.add("End", Logger.Level.TRACE);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	log.add("Interrupted", Logger.Level.TRACE);

    }
}