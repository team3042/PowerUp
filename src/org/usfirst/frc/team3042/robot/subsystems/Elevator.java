package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Elevator_HoldPosition;
import org.usfirst.frc.team3042.robot.commands.Elevator_Stop;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	private TalonSRX elevatorTalon = new TalonSRX(RobotMap.CAN_ELEVATOR_TALON);
	private final int BOT_POS = RobotMap.ELEVATOR_BOTTOM_POSITION;
	private final int INT_POS = RobotMap.ELEVATOR_INTAKE_POSITION;
	private final int SWITCH_POS = RobotMap.ELEVATOR_SWITCH_POSITION;
	private final int LOW_SCALE_POS = RobotMap.ELEVATOR_LOW_SCALE_POSITION;
	private final int MID_SCALE_POS = RobotMap.ELEVATOR_MID_SCALE_POSITION;
	private final int HIGH_SCALE_POS = RobotMap.ELEVATOR_HIGH_SCALE_POSITION;
	private final int MAX_POS = RobotMap.ELEVATOR_MAX_POSITION;
	private final int MIN_POS = RobotMap.ELEVATOR_MIN_POSITION;
	private final int MANUAL_SPEED = RobotMap.ELEVATOR_MANUAL_SPEED;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int PIDIDX = RobotMap.PIDIDX;
	private static final int kP = RobotMap.ELEVATOR_KP;
	private static final int kI = RobotMap.ELEVATOR_KI;
	private static final int kD = RobotMap.ELEVATOR_KD;
	private static final int kF = RobotMap.ELEVATOR_KF;
	private static final int I_ZONE = RobotMap.ELEVATOR_I_ZONE;
	private static final int MAGIC_ACCEL = RobotMap.ELEVATOR_MOTION_MAGIC_ACCELERATION;
	private static final int MAGIC_CRUISE = RobotMap.ELEVATOR_MOTION_MAGIC_CRUISE_VELOCITY;
	
	/** Instance Variables ****************************************************/
	private Log log = new Log(LOG_LEVEL, getName());
	private int currentGoalPos = 0;
	private int currentPreset = 0;
	public Position[] positionFromInt = new Position[]{Position.BOTTOM, Position.INTAKE, Position.SWITCH, Position.LOW_SCALE, Position.MID_SCALE, Position.HIGH_SCALE};
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	//setDefaultCommand(new Elevator_HoldPosition());
    	setDefaultCommand(new Elevator_Stop());
    }
    
    public Elevator(){
    	initMotor(elevatorTalon);
    	initMotionMagic(elevatorTalon);
    }
    
    private void initMotor(TalonSRX motor) {
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, I_ZONE, TIMEOUT);
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDIDX, TIMEOUT);
	}
    
    public void initMotionMagic(TalonSRX motor){
		motor.configMotionAcceleration(MAGIC_ACCEL, TIMEOUT);
		motor.configMotionCruiseVelocity(MAGIC_CRUISE, TIMEOUT);
	}
    
    private void setPower(TalonSRX talon, double power){
    	talon.set(ControlMode.PercentOutput, power);
    }
    
	public void stop() {
		setPower(elevatorTalon, 0);
	}
	
	/**
	 * elevatorTalon.set(controlMode.MotionMagic, safetyCheck(position));
	 * @param position
	 */
	public void setTalonPosition(int position) {
		elevatorTalon.set(ControlMode.MotionMagic, safetyCheck(position));
	}
	
	public int safetyCheck(int position) {
		return Math.max(Math.min(MAX_POS, position), MIN_POS);
	}
	
	/**
	 * use this method to set Elevator Position to a Position - BOTTOM, INTAKE, SWITCH, LOW_SCALE, MID_SCALE, HIGH_SCALE;
	 * @param position
	 */
	public void setPosition(Position position) {
		switch (position) {
			case BOTTOM:
				log.add("Bottom", Log.Level.DEBUG);
				setTalonPosition(BOT_POS);
				currentGoalPos = BOT_POS;
                break;
			case INTAKE:
				log.add("Intake", Log.Level.DEBUG);
				setTalonPosition(INT_POS);
				currentGoalPos = INT_POS;
				break;
			case SWITCH:
				log.add("Switch", Log.Level.DEBUG);
				setTalonPosition(SWITCH_POS);
				currentGoalPos = SWITCH_POS;
				break;
			case LOW_SCALE:
				log.add("Low Scale", Log.Level.DEBUG);
				setTalonPosition(LOW_SCALE_POS);
				currentGoalPos = LOW_SCALE_POS;
				break;
			case MID_SCALE:
				log.add("Mid Scale", Log.Level.DEBUG);
				setTalonPosition(MID_SCALE_POS);
				currentGoalPos = MID_SCALE_POS;
				break;
			case HIGH_SCALE:
				log.add("High Scale", Log.Level.DEBUG);
				setTalonPosition(HIGH_SCALE_POS);
				currentGoalPos = HIGH_SCALE_POS;
				break;
			default:
				stop();
				break;
		}
	}
	
	public void manual(int direction){
		if(direction == POVButton.UP){
			elevatorTalon.set(ControlMode.MotionMagic, currentGoalPos += MANUAL_SPEED);
		}
		else if(direction == POVButton.DOWN){
			elevatorTalon.set(ControlMode.MotionMagic, currentGoalPos -= MANUAL_SPEED);
		}
	}
	
	public void cyclePreset(int direction){
		log.add("Cycling the position now", Log.Level.DEBUG);
		if(direction == POVButton.UP){
			currentPreset = Math.min(currentPreset + 1, positionFromInt.length - 1);
			log.add("Preset: " + currentPreset, Log.Level.DEBUG);
			setPosition(positionFromInt[currentPreset]);
		}
		else if(direction == POVButton.DOWN){
			log.add("Previous Preset: " + currentPreset, Log.Level.DEBUG);
			currentPreset = Math.max(currentPreset - 1, 0);
			log.add("Preset: " + currentPreset, Log.Level.DEBUG);
			setPosition(positionFromInt[currentPreset]);
		}
	}
	
	public int getPosition(){
		return elevatorTalon.getSelectedSensorPosition(PIDIDX);
	}
	
	public int getCurrentGoalPos(){
		return currentGoalPos;
	}
	
	public int getCurrentPreset(){
		return currentPreset;
	}
	
	public static enum Position {
		BOTTOM, INTAKE, SWITCH, LOW_SCALE, MID_SCALE, HIGH_SCALE;
	}
}

