package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.ADIS16448_IMU;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Winch_Stop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {

	private static final int CAN_WINCH_MOTOR_FRONT_LEFT = RobotMap.CAN_WINCH_MOTOR_FRONT_LEFT;
	private static final int CAN_WINCH_MOTOR_FRONT_RIGHT = RobotMap.CAN_WINCH_MOTOR_FRONT_RIGHT;
	private static final int CAN_WINCH_MOTOR_REAR_LEFT = RobotMap.CAN_WINCH_MOTOR_REAR_LEFT;
	private static final int CAN_WINCH_MOTOR_REAR_RIGHT = RobotMap.CAN_WINCH_MOTOR_REAR_RIGHT;

	private TalonSRX winchMotorFrontLeft = new TalonSRX(CAN_WINCH_MOTOR_FRONT_LEFT);
	private TalonSRX winchMotorFrontRight = new TalonSRX(CAN_WINCH_MOTOR_FRONT_RIGHT);
	private TalonSRX winchMotorRearLeft = new TalonSRX(CAN_WINCH_MOTOR_FRONT_LEFT);
	private TalonSRX winchMotorRearRight = new TalonSRX(CAN_WINCH_MOTOR_FRONT_RIGHT);
	
	ADIS16448_IMU gyro = new ADIS16448_IMU();
	
	private Timer time = new Timer();
	private double oldTime = time.get();
	
	private double climbPower = RobotMap.WINCH_VERTICAL_BASE_SPEED;
	
	private double kPFL = RobotMap.KP_WINCH_FRONT_LEFT;
	private double kIFL = RobotMap.KI_WINCH_FRONT_LEFT;
	private double kDFL = RobotMap.KD_WINCH_FRONT_LEFT;

	private double kPFR = RobotMap.KP_WINCH_FRONT_RIGHT;
	private double kIFR = RobotMap.KI_WINCH_FRONT_RIGHT;
	private double kDFR = RobotMap.KD_WINCH_FRONT_RIGHT;

	private double kPRL = RobotMap.KP_WINCH_REAR_LEFT; 
	private double kIRL = RobotMap.KI_WINCH_REAR_LEFT; 
	private double kDRL = RobotMap.KD_WINCH_REAR_LEFT;

	private double kPRR = RobotMap.KP_WINCH_REAR_RIGHT;
	private double kIRR = RobotMap.KI_WINCH_REAR_RIGHT;
	private double kDRR = RobotMap.KD_WINCH_REAR_RIGHT;
	
	private double oldErrorLeftRight = 0;
	private double accumErrorLeftRight = 0;
	private double oldErrorFrontBack = 0;
	private double accumErrorFrontBack = 0;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Winch_Stop());
    }
    
    public void setPower(TalonSRX motor, double winchPower) {
		winchPower = safetyCheck(winchPower);
    
				
		motor.set(ControlMode.PercentOutput, winchPower);		
	}
    
    public void climb(){
    	
    }
    
    private void gyroPIDCorrection(double powerFL, double powerFR, double powerRL, double powerRR) {
    	double time = this.time.get();
    	double deltaTime = time - oldTime;
    	
    	//Left to Right.
    	double errorLeftRight = 0 - gyro.getAngleX();
    	double deltaError = errorLeftRight - oldErrorLeftRight;
    	
    	//Front to Back.
    	double errorFrontBack = 0 - gyro.getAngleY();
    	double deltaErrorFrontBack = errorFrontBack - oldErrorFrontBack;
    	
    	//Prepare for next time through
    	oldTime = time;
    	accumErrorLeftRight += errorLeftRight;
    	accumErrorFrontBack += errorFrontBack;
    	oldErrorLeftRight = errorLeftRight;
    	oldErrorFrontBack = errorFrontBack;
    }
    
    private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
    
	public void stop() {
		setPower(winchMotorFrontLeft, 0.0);
		setPower(winchMotorFrontRight, 0.0);
		setPower(winchMotorRearLeft, 0.0);
		setPower(winchMotorRearRight, 0.0);
	}
}

