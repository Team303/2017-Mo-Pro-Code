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
		if(firstRun) Robot.drive.zeroEncoders(); 
		
		l = path.testEncLeft.calculate(Robot.drive.getLeftEncoder());
		r = path.testEncRight.calculate(Robot.drive.getRightEncoder());
		double theta = Robot.navX.getYaw();
		double desiredHeading = Pathfinder.r2d(path.testEncLeft.getHeading());
		double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading-theta);
		double turn = -0.15*angleDifference;

		Robot.drive.drive(l+turn, r-turn);
		//SmartDashboard.putNumber("Segment");
		SmartDashboard.putNumber("L", Robot.drive.getLeftEncoder());
		SmartDashboard.putNumber("R", Robot.drive.getRightEncoder());
	
		firstRun = false;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}