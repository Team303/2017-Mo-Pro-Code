package org.usfirst.frc.team303.robot;

import org.usfirst.frc.team303.robot.action.ActionDriveByWaypoints;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Path {
	double timeStep = 0.05;
	double maxVel = 20;
	double maxAccel = 5;
	double maxJerk = 8;
	double wheelBaseWidth = 2.333;
	int ticksPerRev = 2304; 
	double wheelDiameter = 0.3283333333333333;
	//done in feet for now

	double p = 10;
	double i = 0;
	double d = 0;
	double velocityRatio = 1/maxVel;
	double accelGain = 0;
	
	// The first argument is the proportional gain. Usually this will be quite high
		// The second argument is the integral gain. This is unused for motion profiling
		// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
		// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
		//	      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
		// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker

		double l;
		double r;

		public Trajectory forwardLeftTrajectory;
		public Trajectory forwardRightTrajectory;
		Trajectory forwardTrajectory;
		public EncoderFollower testEncLeft;
		public EncoderFollower testEncRight;
		
	
	public Path(Waypoint[] points) {
		try{	
			System.out.println("Generating trajectory...");
			Trajectory.Config testConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, maxVel, maxAccel, maxJerk);
			forwardTrajectory = Pathfinder.generate(points, testConfig);
			TankModifier testModifier = new TankModifier(forwardTrajectory).modify(wheelBaseWidth);
			System.out.println("Trajectory Generation completed");
			
			
			forwardLeftTrajectory = testModifier.getLeftTrajectory();
			forwardRightTrajectory = testModifier.getRightTrajectory();
			
			testEncLeft = new EncoderFollower(forwardLeftTrajectory);
			testEncRight = new EncoderFollower(forwardRightTrajectory);
			testEncLeft.configureEncoder(Robot.drive.getLeftEncoder(), ticksPerRev, wheelDiameter);
			testEncRight.configureEncoder(Robot.drive.getRightEncoder(), ticksPerRev, wheelDiameter);
			testEncLeft.configurePIDVA(p, i, d, velocityRatio, accelGain);
			testEncRight.configurePIDVA(p, i, d, velocityRatio, accelGain);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in Path Construction" + e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
}
