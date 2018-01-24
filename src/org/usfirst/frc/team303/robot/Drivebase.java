package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivebase {

	
	
	
	
	
	SpeedControllerGroup right;
	SpeedControllerGroup left;
	
	DifferentialDrive drivebase;
	private WPI_TalonSRX FR = new WPI_TalonSRX(RobotMap.FRONT_RIGHT);
	private WPI_TalonSRX BR = new WPI_TalonSRX(RobotMap.REAR_RIGHT);
	private WPI_TalonSRX FL = new WPI_TalonSRX(RobotMap.FROMT_LEFT);
	private WPI_TalonSRX BL = new WPI_TalonSRX(RobotMap.REAR_LEFT);
	public Drivebase(){
		
		
		
		FR.setInverted(RobotMap.FRONT_RIGHT_INV);
		BR.setInverted(RobotMap.REAR_RIGHT_INV);
		FL.setInverted(RobotMap.FRONT_LEFT_INV);
		BL.setInverted(RobotMap.REAR_RIGHT_INV);
		
		
		FL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		BR.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
		
		right = new SpeedControllerGroup(FR, BR);
		left = new SpeedControllerGroup(FL, BL);
		
		drivebase = new DifferentialDrive(left, right);
		
		
		
	}
	
	public void drive(double l, double r){
		
		drivebase.tankDrive(l, r);
		
	}
	
	public int getLeftEncoder(){
		
		return FL.getSelectedSensorPosition(0);
		
		
	}
	
	public int getRightEncoder(){
		return BR.getSelectedSensorPosition(0);
	}
	
	public void zeroEncoders(){
		
		FL.setSelectedSensorPosition(0, 0, 0);
		BR.setSelectedSensorPosition(0, 0, 0);
	}
	
	
}
