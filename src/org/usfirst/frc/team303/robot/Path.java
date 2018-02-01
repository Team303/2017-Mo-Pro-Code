package org.usfirst.frc.team303.robot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import org.usfirst.frc.team303.robot.action.ActionDriveByTrajectory;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Path {
	
	static double timeStep = 0.02;
	static double maxVel = 10;
	static double maxAccel = 10;
	static double maxJerk = 40;
	double wheelBaseWidth = 2.333;
	int ticksPerRev = 2304; 
	double wheelDiameter = 0.3283333333333333;
	//done in feet for now

	double p = 0.5;
	double i = 0;
	double d = 0.01;
	double velocityRatio = 1/maxVel;
	double accelGain = 0.05;
	
	// The first argument is the proportional gain. Usually this will be quite high
		// The second argument is the integral gain. This is unused for motion profiling
		// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
		// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
		//	      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
		// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
	

		double l;
		double r;
		public Trajectory[] trajectoryArray;
		public Trajectory forwardLeftTrajectory;
		public Trajectory forwardRightTrajectory;
		public EncoderFollower testEncLeft;
		public EncoderFollower testEncRight;
		
	
	public Path(Trajectory forwardTrajectory) {
		try{	
			/*System.out.println("Generating trajectory...");
			Trajectory.Config testConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, maxVel, maxAccel, maxJerk);
			forwardTrajectory = Pathfinder.generate(points, testConfig);
			TankModifier testModifier = new TankModifier(forwardTrajectory).modify(wheelBaseWidth);
			System.out.println("Trajectory Generation completed");*/
			
			TankModifier testModifier = new TankModifier(forwardTrajectory).modify(wheelBaseWidth);
			
			forwardLeftTrajectory = testModifier.getLeftTrajectory();
			forwardRightTrajectory = testModifier.getRightTrajectory();
			
			testEncLeft = new EncoderFollower(forwardLeftTrajectory);
			testEncRight = new EncoderFollower(forwardRightTrajectory);
			testEncLeft.configureEncoder(Robot.drive.getLeftEncoder(), ticksPerRev, wheelDiameter);
			testEncRight.configureEncoder(Robot.drive.getRightEncoder(), ticksPerRev, wheelDiameter);
			testEncLeft.configurePIDVA(p, i, d, velocityRatio, accelGain);
			testEncRight.configurePIDVA(p, i, d, velocityRatio, accelGain);
		
			/*for(int i = 0; i < forwardTrajectory.segments.length; i++) {
				System.out.println("Segment " + i + ") x: " + forwardTrajectory.segments[i].x + " y: " + forwardTrajectory.segments[i].y + " heading: " + Pathfinder.r2d(forwardTrajectory.segments[i].heading));
			}*/
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in Path Construction" + e.getMessage());
		}
	}
	
	public static Trajectory[] deserializeTrajectoryArray(String serializedTrajectoryArray) {
		Trajectory[] trajectories = null; 
		try {
			byte[] b = Base64.getDecoder().decode(serializedTrajectoryArray.getBytes()); 
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream si = new ObjectInputStream(bi);
			trajectories = (Trajectory[]) si.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trajectories;
	}

	public static String serializeWaypointArray2d(Waypoint[][] waypoints2d) {
		String serializedWaypoints = "";
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(waypoints2d);
			so.flush();
			serializedWaypoints = new String(Base64.getEncoder().encode(bo.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serializedWaypoints;
	}

}