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
	static double maxVel = 11;
	static double maxAccel = 12;
	static double maxJerk = 100;
	double wheelBaseWidth = 2.333;
	int ticksPerRev = 2304; 
	double wheelDiameter = 0.3283333333333333;
	//done in feet for now
	//watch out for a table right about there
	
	double p = 1;
	double i = 0;
	double d = 0.08;
	double velocityRatio = 1/maxVel;
	double accelGain = 0.06;
	
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
			TankModifier testModifier = new TankModifier(forwardTrajectory).modify(wheelBaseWidth);
			
			forwardLeftTrajectory = testModifier.getLeftTrajectory();
			forwardRightTrajectory = testModifier.getRightTrajectory();
			
			testEncLeft = new EncoderFollower(forwardLeftTrajectory);
			testEncRight = new EncoderFollower(forwardRightTrajectory);
			testEncLeft.configureEncoder(Robot.drive.getLeftEncoder(), ticksPerRev, wheelDiameter);
			testEncRight.configureEncoder(Robot.drive.getRightEncoder(), ticksPerRev, wheelDiameter);
			testEncLeft.configurePIDVA(p, i, d, velocityRatio, accelGain);
			testEncRight.configurePIDVA(p, i, d, velocityRatio, accelGain);	
	}
	
/**
 * Method used to turn the serialized Trajectory Array string back in to a Trajectory Array object.
 * @param serialized Trajectory Array
 * @return Array of Trajectory objects
 */
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
	
/**
 * Method used to turn the 2-D Waypoints Array in Robot.java into a string that can be passed to the Remote Path Generator through the NetworkTables input table.
 * @param 2-D Array of Waypoints
 * @return Serialized 2-D Waypoint Array 
 */
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