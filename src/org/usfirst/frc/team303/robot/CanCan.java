package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.CANifier;

public class CanCan {

	private CANifier cancan;
	
	
	public CanCan(){
		
		cancan = new CANifier(2);
		
}
	public void setLEDs (double A, double B, double C){
		 
		cancan.setLEDOutput(A, CANifier.LEDChannel.LEDChannelA);
		cancan.setLEDOutput(B, CANifier.LEDChannel.LEDChannelB);
		cancan.setLEDOutput(C, CANifier.LEDChannel.LEDChannelC);
		
		
	}
	
	
}
