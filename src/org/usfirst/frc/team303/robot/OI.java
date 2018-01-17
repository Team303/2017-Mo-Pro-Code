package org.usfirst.frc.team303.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

	
	public static Joystick left = new Joystick(0);
	public static Joystick right = new Joystick(1);
	public static double leftJoy;
	public static double rightJoy;
	
	public static void update(){
		
		leftJoy = left.getY();
		rightJoy = right.getY();
		
	}
	
	
	
	
	
	
}
