package org.usfirst.frc.team303.robot.action;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActionDriveByWaypoints implements Action {
	
	boolean firstRun = true;
	double l;
	double r;
	@Override
	public void run() {
		if(firstRun) {
			Robot.drive.zeroEncoders();
		} 
		l = Robot.path.testEncLeft.calculate(Robot.drive.getLeftEncoder());
			r = Robot.path.testEncRight.calculate(Robot.drive.getRightEncoder());
			double theta = Robot.navX.getYaw();
			double desiredHeading = Pathfinder.r2d(Robot.path.testEncLeft.getHeading());
			double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading-theta);
			double turn = -0.02*angleDifference;

			Robot.drive.drive(l+turn, r-turn);
			//SmartDashboard.putNumber("NavX", Robot.navX.getYaw());
			SmartDashboard.putNumber("L", Robot.drive.getLeftEncoder());
			SmartDashboard.putNumber("R", Robot.drive.getRightEncoder());
			firstRun = false;
	}

	@Override
	public boolean isFinished() {
		return Robot.path.testEncRight.getSegment().equals(Robot.path.forwardRightTrajectory.get(Robot.path.forwardRightTrajectory.length() - 1)) && Robot.path.testEncLeft.getSegment().equals(Robot.path.forwardLeftTrajectory.get(Robot.path.forwardLeftTrajectory.length() - 1));
	}

}
