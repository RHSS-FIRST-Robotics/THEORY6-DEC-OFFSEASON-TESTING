/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package theory6.commands.AutonCommands;

import theory6.commands.CommandBase;

/**
 *
 * @author Mahrus
 */
public class DriveCommand extends CommandBase{
    private double distance;
    private double speed;
    private double angle;
    private double timeout;
    
    public DriveCommand(double setPoint, double setSpeed, double setAngle, double setTimeout)
    {
        distance = setPoint;
        speed = setSpeed;
        angle = setAngle;
        timeout = setTimeout;
        requires(drive);
    }
    
    protected void initialize()
    {
    }
    
    protected void execute()
    {
        while(!isFinished())
        {
            drive.driveStraight(distance, speed, angle);
            setTimeout(timeout);
        }
    }
    
    protected boolean isFinished()
    {
        boolean done;
        if (speed >= 0 && angle == 0) {
            done = (drive.getLeftEncoderDist() > distance && drive.getRightEncoderDist() > distance || isTimedOut());
            return done;
        } 
        else if(speed < 0 && angle == 0) {
            done = (drive.getLeftEncoderDist() < distance && drive.getRightEncoderDist() < distance || isTimedOut());
            return done;
        }
        else{
            done = ((drive.getLeftEncoderDist() + drive.getRightEncoderDist())/2 > distance || isTimedOut());
            return done;
        }
    }
    
    protected void end()
    {
        drive.runLeftDrive(0);
        drive.runRightDrive(0);
        drive.reset();
    }
    
    protected void interrupted()
    {
        
    }
    
    protected boolean thresHold(double input){
        if((distance - 1) < input && (distance + 1) > input)
            return true;
        else 
            return false;
    }
    
}
