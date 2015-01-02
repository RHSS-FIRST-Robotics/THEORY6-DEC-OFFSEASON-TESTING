/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*                                                                            */ 
/* Author: Mahrus Kazi (Team 1241)                                            */
/*----------------------------------------------------------------------------*/

//http://www.spectrum3847.org/frc2012api/edu/wpi/first/wpilibj/command/CommandGroup.html
//http://nwctarobotics.com/javadoc/2013/edu/wpi/first/wpilibj/command/Scheduler.html
//http://wpilib.screenstepslive.com/s/3120/m/7952/l/90252-synchronizing-two-commands

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;
import theory6.commands.CommandBase;
import theory6.commands.TankDrive;
import theory6.pid.PIDController;
import theory6.utilities.PIDScale;
import theory6.utilities.Constants;
import theory6.utilities.CSVLogger;
import theory6.utilities.DataOutput;
import theory6.utilities.Matrix;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    Command autonomousCommand;
    Matrix matrix;
    Matrix matrix2;
    public double[][] function;
    public double[][] function2;
    PIDController pid;

    public void robotInit() {
        CommandBase.init();
        Constants.getInstance("constants.txt");
        matrix = new Matrix(Constants.getString("point1", ""),Constants.getString("point2", ""),Constants.getString("point3", ""),
                Constants.getString("point4", ""),Constants.getString("point5", ""),Constants.getString("point6", ""), "6x6");
        matrix2 = new Matrix(Constants.getString("point1", ""),Constants.getString("point2", ""),Constants.getString("point3", ""),
                Constants.getString("point4", ""), "4x4");
        Constants.load();
        pid = new PIDController(0.025,Constants.getDouble("iGyro"),Constants.getDouble("dGyro"));
    }

    public void autonomousInit() {
       
    }
    
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        Constants.load();
        System.out.println(matrix2.getName());
        System.out.println(matrix.getName());
       
        matrix.changePoints(Constants.getString("point1", ""),Constants.getString("point2", ""),Constants.getString("point3", ""),
                Constants.getString("point4", ""),Constants.getString("point5", ""),Constants.getString("point6", ""));
    
        matrix2.changePoints(Constants.getString("point1", ""),Constants.getString("point2", ""),Constants.getString("point3", ""),
                Constants.getString("point4", ""));
        function = matrix.findFunction();
        function2 = matrix2.findFunction();

        CommandBase.log.fileCreate("output.txt");
        CommandBase.log.writeText("FUNCTION COEFFICIENTS for 5th order function");
        for(int x = 0; x < function.length; x++)
            CommandBase.log.writeText(x + ": " + function[x][0]);
        CommandBase.log.writeText("X Values, Y Values");
        for(int x = 0; x < matrix.findPoints().size(); x++)
            CommandBase.log.writeText(matrix.findPoints().elementAt(x).toString());
        
        CommandBase.log.writeText("");
        
        CommandBase.log.writeText("FUNCTION COEFFICIENTS for cubic function");
        for(int x = 0; x < function2.length; x++)
            CommandBase.log.writeText(x + ": " + function2[x][0]);
        CommandBase.log.writeText("X Values, Y Values");
        for(int x = 0; x < matrix2.findPoints().size(); x++)
            CommandBase.log.writeText(matrix2.findPoints().elementAt(x).toString());
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        Scheduler.getInstance().add(new TankDrive());
       
        updateConstants();
        updateDS();
    }
   
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void updateDS()
    {
        CommandBase.ds.println(DriverStationLCD.Line.kUser1, 1, "Enc. R: " + CommandBase.drive.getRightEncoderDist());
        CommandBase.ds.println(DriverStationLCD.Line.kUser2, 1, "Enc. L: " + CommandBase.drive.getLeftEncoderDist());
        CommandBase.ds.println(DriverStationLCD.Line.kUser3, 1, "Gyro: " + CommandBase.drive.getGyroAngle());
        CommandBase.ds.println(DriverStationLCD.Line.kUser5, 1, "GYR pID " + pid.calcPID(0, CommandBase.drive.getGyroAngle()));
        CommandBase.ds.updateLCD();
    }
    
    public void updateConstants()
    {
        if(CommandBase.oi.getStartButton()){
            Constants.load();
            CommandBase.drive.resetEncoders();
            CommandBase.drive.resetGyro();
        }
    }
}
