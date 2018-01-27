package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Logger;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Elevator_Stop;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final Logger.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	private TalonSRX elevatorTalon = new TalonSRX(RobotMap.CAN_ELEVATOR_TALON);
	private final int BOT_POS = RobotMap.ELEVATOR_BOTTOM_POSITION;
	private final int INT_POS = RobotMap.ELEVATOR_INTAKE_POSITION;
	private final int SWITCH_POS = RobotMap.ELEVATOR_SWITCH_POSITION;
	private final int LOW_SCALE_POS = RobotMap.ELEVATOR_LOW_SCALE_POSITION;
	private final int HIGH_SCALE_POS = RobotMap.ELEVATOR_HIGH_SCALE_POSITION;
	private final int MANUAL_SPEED = RobotMap.ELEVATOR_MANUAL_SPEED;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int kP = RobotMap.ELEVATOR_KP;
	private static final int kI = RobotMap.ELEVATOR_KI;
	private static final int kD = RobotMap.ELEVATOR_KD;
	private static final int kF = RobotMap.ELEVATOR_KF;
	private static final int I_ZONE = RobotMap.ELEVATOR_I_ZONE;
	
	/** Instance Variables ****************************************************/
	private Logger log = new Logger(LOG_LEVEL, getName());
	private int currentPos = 0;
	private int currentPreset = 0;
	Position[] positionFromInt = new Position[]{Position.BOTTOM, Position.INTAKE, Position.SWITCH, Position.LOW_SCALE, Position.MID_SCALE, Position.HIGH_SCALE};
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Elevator_Stop());
    }
    
    public Elevator(){
    	initMotor(elevatorTalon);
    }
    
    private void initMotor(TalonSRX motor) {
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, I_ZONE, TIMEOUT);
	}
    
    private void setPower(TalonSRX talon, double power){
    	talon.set(ControlMode.PercentOutput, power);
    }
    
	public void stop() {
		setPower(elevatorTalon, 0);
	}
	
	public void setPosition(Position position) {
		switch (position) {
			case BOTTOM:
				elevatorTalon.set(ControlMode.Position, BOT_POS);
				currentPos = BOT_POS;
				currentPreset = 0;
                break;
			case INTAKE:
				elevatorTalon.set(ControlMode.Position, INT_POS);
				currentPos = INT_POS;
				currentPreset = 1;
				break;
			case SWITCH:
				elevatorTalon.set(ControlMode.Position, SWITCH_POS);
				currentPos = SWITCH_POS;
				currentPreset = 2;
				break;
			case LOW_SCALE:
				elevatorTalon.set(ControlMode.Position, LOW_SCALE_POS);
				currentPos = LOW_SCALE_POS;
				currentPreset = 3;
				break;
			case HIGH_SCALE:
				elevatorTalon.set(ControlMode.Position, HIGH_SCALE_POS);
				currentPos = HIGH_SCALE_POS;
				currentPreset = 4;
				break;
			default:
				stop();
				break;
		}
	}
	
	public void manual(int direction){
		if(direction == POVButton.UP){
			elevatorTalon.set(ControlMode.Position, currentPos += MANUAL_SPEED);
		}
		else if(direction == POVButton.DOWN){
			elevatorTalon.set(ControlMode.Position, currentPos -= MANUAL_SPEED);
		}
	}
	
	public void cyclePreset(int direction){
		if(direction == POVButton.UP){
			currentPreset = (currentPreset + 1) % positionFromInt.length; // modulus to keep the value in the range of 0-4
			setPosition(positionFromInt[currentPreset]);
		}
		else if(direction == POVButton.DOWN){
			currentPreset = (currentPreset - 1) % positionFromInt.length; // modulus to keep the value in the range of 0-4
			setPosition(positionFromInt[currentPreset]);
		}
	}
	
	public int getPosition(){
		return currentPos;
	}
	
	public int getCurrentPreset(){
		return currentPreset;
	}
	
	public static enum Position {
		BOTTOM, INTAKE, SWITCH, LOW_SCALE, MID_SCALE, HIGH_SCALE;
	}
}

