
package org.usfirst.frc.team3042.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3042.lib.I2CRangeSensor;
import org.usfirst.frc.team3042.lib.Logger;
<<<<<<< HEAD
import org.usfirst.frc.team3042.robot.commands.ExampleCommand;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
=======
>>>>>>> branch 'master' of https://github.com/team3042/PowerUp.git
import org.usfirst.frc.team3042.robot.subsystems.Claw;
import org.usfirst.frc.team3042.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;
<<<<<<< HEAD
import org.usfirst.frc.team3042.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team3042.robot.subsystems.HookDeploy;
=======
>>>>>>> branch 'master' of https://github.com/team3042/PowerUp.git
import org.usfirst.frc.team3042.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot { 
	/** Configuration Constants ***********************************************/
	private static final Logger.Level LOG_LEVEL = RobotMap.LOG_ROBOT;
	private static final boolean HAS_DRIVETRAIN = RobotMap.HAS_DRIVETRAIN;
	private static final boolean HAS_GYROSCOPE = RobotMap.HAS_GYROSCOPE;
	private static final boolean HAS_WINCH = RobotMap.HAS_WINCH;
	private static final boolean HAS_CLAW = RobotMap.HAS_CLAW;
	private static final boolean HAS_ELEVATOR = RobotMap.HAS_ELEVATOR;
	private static final boolean HAS_COMPRESSOR = RobotMap.HAS_COMPRESSOR;
	private static final boolean HAS_ARM = RobotMap.HAS_ARM;
	private static final boolean HAS_HOOKDEPLOY = RobotMap.HAS_HOOKDEPLOY;
	
	/** Create Subsystems *****************************************************/
	private Logger log = new Logger(LOG_LEVEL, "Robot");
	public static final Drivetrain 	drivetrain 	= (HAS_DRIVETRAIN) 	? new Drivetrain() : null;
	public static final Winch winch = (HAS_WINCH) ? new Winch() : null;
	public static final Claw claw = (HAS_CLAW) ? new Claw() : null;
	public static final Elevator elevator = (HAS_ELEVATOR) ? new Elevator() : null;
<<<<<<< HEAD
	public static final Arm arm = (HAS_ARM) ? new Arm() : null;
	public static final HookDeploy hookDeploy = (HAS_HOOKDEPLOY) ? new HookDeploy() : null; 
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
=======
>>>>>>> branch 'master' of https://github.com/team3042/PowerUp.git
	public static OI oi;
	public static final Compressor compressor = (HAS_COMPRESSOR) ? new Compressor() : null;
	public I2CRangeSensor range = new I2CRangeSensor(I2C.Port.kOnboard);
	
	private static String gameData = "";
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<Command>();

	
	/** robotInit *************************************************************
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		log.add("Robot Init", Logger.Level.TRACE);
		
		oi = new OI();
		SmartDashboard.putData("Auto Mode", chooser);
	}

	
	/** disabledInit **********************************************************
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		log.add("Disabled Init", Logger.Level.TRACE);
	}

	
	/** disabledPeriodic ******************************************************
	 * Called repeatedly while the robot is is disabled mode.
	 */
	public void disabledPeriodic() {
		searchForGameData();
		
		Scheduler.getInstance().run();
	}

	
	/** autonomousInit ********************************************************
	 * Run once at the start of autonomous mode.
	 */
	public void autonomousInit() {
		log.add("Autonomous Init", Logger.Level.TRACE);
		
		autonomousCommand = chooser.getSelected();

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	
	/** autonomousPeriodic ****************************************************
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		searchForGameData();
		
		Scheduler.getInstance().run();
	}

	
	/** teleopInit ************************************************************
	 * This function is called when first entering teleop mode.
	 */
	public void teleopInit() {
		log.add("Teleop Init", Logger.Level.TRACE);
		
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}


	/** teleopPeriodic ********************************************************
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putNumber("I2C ValueOptical", range.readOptical());
		SmartDashboard.putNumber("I2C ValueUltralsonic", range.readUltrasonic());
	}

	
	/** testPeriodic **********************************************************
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void searchForGameData(){
		log.add("Game Data: " + gameData, Logger.Level.ALL);
		SmartDashboard.putBoolean("Game Data Exists", gameDataPresent());
		if(!gameDataPresent()){
			gameData = DriverStation.getInstance().getGameSpecificMessage();
		}
	}
	
	public boolean gameDataPresent(){
		return gameData.length() == 3;
	}
	
	public String getGameData(){
		return gameData;
	}
}