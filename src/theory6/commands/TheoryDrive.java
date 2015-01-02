/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.commands;

import theory6.commands.CommandBase;

/**
 *
 * @author Mahrus
 */
public class TheoryDrive extends CommandBase{
    
    public TheoryDrive()
    {
        requires(drive);
        requires(oi);
    }
    
    protected void initialize()
    {
    }
    
    protected void execute()
    {
        double left = oi.getLeftX();
        double right = oi.getRightY();
        
        if(left >= 0)
        {
            drive.runLeftDrive(-right + Math.abs(left));
            drive.runRightDrive(right + Math.abs(left));
        }
        if(left <= 0)
        {
            drive.runLeftDrive(-right - Math.abs(left));
            drive.runRightDrive(right - Math.abs(left));
        }
    }
    
    protected boolean isFinished()
    {
        return false;
    }
    
    protected void end()
    {
        
    }
    
    protected void interrupted()
    {
        
    }
    
}
