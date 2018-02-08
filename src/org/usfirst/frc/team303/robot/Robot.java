/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------*/
/* 	██████╗  ██████╗ ██████╗  */
/* 	╚════██╗██╔═████╗╚════██╗ */
/* 	 █████╔╝██║██╔██║ █████╔╝ */
/* 	 ╚═══██╗████╔╝██║ ╚═══██╗ */
/* 	██████╔╝╚██████╔╝██████╔╝ */
/* 	╚═════╝  ╚═════╝ ╚═════╝  */
/*----------------------------*/  
                    

package org.usfirst.frc.team303.robot;

import java.nio.file.Paths;

import org.usfirst.frc.team303.robot.action.ActionWait;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private CanCan can;
	public static Drivebase drive;
	public static NavX navX;
	public static Autonomous auto;
	
	static boolean autoRunOnce = false;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		try{
			drive = new Drivebase();
			navX = new NavX();
			auto = new Autonomous();
			
			drive.zeroEncoders();
			m_chooser.addDefault("Default Auto", kDefaultAuto);
			m_chooser.addObject("My Auto", kCustomAuto);
			SmartDashboard.putData("Auto choices", m_chooser);
			
			//can = new CanCan();
			
			NetworkTable pathfinderInputTable = NetworkTable.getTable("pathfinderInput");
			
			pathfinderInputTable.putNumber("timeStep", Path.timeStep);
			pathfinderInputTable.putNumber("maxVel", Path.maxVel);
			pathfinderInputTable.putNumber("maxAccel", Path.maxAccel);
			pathfinderInputTable.putNumber("maxJerk", Path.maxJerk);

			pathfinderInputTable.putString("waypoints", Path.serializeWaypointArray2d(new Waypoint[][] {
				{//this is waypoints[0], and will output to trajectories[0]
					new Waypoint(0, 0, 0),
					new Waypoint(7, 4, Pathfinder.d2r(30)), //this point is waypoints[0, 1]
					new Waypoint(13, 3.5, 0),
					new Waypoint(18, 4, 0)
				}, {
					new Waypoint(0, 0, 0), //this point is waypoints[1, 0]
					new Waypoint(7, 6, Pathfinder.d2r(30)),
					new Waypoint(12, 1, 0),
					new Waypoint(18, 4, 0)
				}, {//this is waypoints[2] and will output to trajectories[2]
					new Waypoint(0, 0, 0),
					new Waypoint(4, -4, Pathfinder.d2r(330)),
					new Waypoint(8, -6, 0),
					new Waypoint(12, -4, Pathfinder.d2r(90)),
					new Waypoint(10, 2, Pathfinder.d2r(180))
				}
			}));

			NetworkTable.flush();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("error in Robot Init" + e.getMessage());
		}
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		drive.zeroEncoders();
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		auto.assembleDriveForwardFour();
			
		auto.arr.add(new ActionWait(9999999.0));
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/*switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}*/
		
		
		if(!autoRunOnce){
			
			
			
			
		}
		auto.run();
		
		autoRunOnce = true;
				
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		OI.update();
		//can.setLEDs(0, 0, 150);
		Robot.drive.drive(OI.leftJoy, OI.rightJoy);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
