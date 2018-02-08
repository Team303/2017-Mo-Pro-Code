package org.usfirst.frc.team303.robot.action;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory;

import org.usfirst.frc.team303.robot.Path;
import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActionDriveByTrajectory implements Action {
	public Path path;
	boolean firstRun = true;
	double l;
	double r;

	public ActionDriveByTrajectory(Trajectory trajectory) {
		path = new Path(trajectory);
	}



	@Override
	public void run() {
		if(firstRun) { 
			Robot.drive.zeroEncoders(); 
			firstRun = false;
		}
		l = path.testEncLeft.calculate(Robot.drive.getLeftEncoder());
		r = path.testEncRight.calculate(Robot.drive.getRightEncoder());
		double theta = Robot.navX.getYaw();
		double desiredHeading = Pathfinder.r2d(path.testEncLeft.getHeading());
		double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading-theta);
		double turn = -0.08*angleDifference;
		Robot.drive.drive(l+turn, r-turn);
		updateSmartDashboard();
	}

	@Override
	public boolean isFinished() {
		return path.testEncLeft.isFinished() && path.testEncRight.isFinished();
	}

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("L", Robot.drive.getLeftEncoder());
		SmartDashboard.putNumber("R", Robot.drive.getRightEncoder());
		if(!path.testEncLeft.isFinished() && !path.testEncRight.isFinished()) {
			SmartDashboard.putNumber("L Heading", path.testEncLeft.getHeading());
			SmartDashboard.putNumber("R Heading", path.testEncRight.getHeading());
			SmartDashboard.putNumber("Lx", path.testEncLeft.getSegment().x);
			SmartDashboard.putNumber("Rx", path.testEncRight.getSegment().x);
			SmartDashboard.putNumber("Ly", path.testEncLeft.getSegment().y);
			SmartDashboard.putNumber("Ry", path.testEncRight.getSegment().y);
			SmartDashboard.putBoolean("Heading = navX?", Math.abs(Pathfinder.r2d(path.testEncLeft.getHeading()) - Robot.navX.getYaw()) <= 2.0);
		}
	}	
}
