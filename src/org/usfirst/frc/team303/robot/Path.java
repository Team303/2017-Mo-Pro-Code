package org.usfirst.frc.team303.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Path {

boolean firstRun;
	
	double timeStep = 0.05;
	double maxVel = 2;
	double maxAccel = 7;
	double maxJerk = 5;
	double wheelBaseWidth = 2.333;
	int ticksPerRev = 2304; 
	double wheelDiameter = 0.3283333333333333;
	//done in feet for now

	double p = 0.001;
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
		Waypoint[] testPoints = new Waypoint[]{
				new Waypoint(0,0, Pathfinder.d2r(0)),
				new Waypoint(2,0,Pathfinder.d2r(0)),
				
				new Waypoint(6,0,Pathfinder.d2r(0)),
				//new Waypoint(10,4,Pathfinder.d2r(90)),
				//new Waypoint(10,10,Pathfinder.d2r(90))
				//new Waypoint(4,2,Pathfinder.d2r(90))
		};
	
	public Path() {
		try{	
			//System.loadLibrary("Pathfinder-Java");
			firstRun = true; 
			
			Trajectory.Config testConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, maxVel, maxAccel, maxJerk);
			forwardTrajectory = Pathfinder.generate(testPoints, testConfig);
			TankModifier testModifier = new TankModifier(forwardTrajectory).modify(wheelBaseWidth);
			
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
			System.out.println("error in Path COnstructor" + e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
}
