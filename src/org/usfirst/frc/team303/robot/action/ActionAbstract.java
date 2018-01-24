package org.usfirst.frc.team303.robot.action;

public abstract class ActionAbstract {
	
	static final double pixelPerDegreeConstant = 0.146875;
	static final double offsetConstant = 20;
	
	public static double getCameraDegreeOffset() {
		
		return (0.1);
		
	}
	
	public static double[] driveStraightAngle(double powSetpoint, double angleDifference, double tuningConstant) {                                                                                                                      //memes
		return new double[] {(powSetpoint + (angleDifference*tuningConstant)), (powSetpoint - (angleDifference*tuningConstant))};
	}
}
