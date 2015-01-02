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
public class ResetSensorsCommand extends CommandBase{
    
    public ResetSensorsCommand()
    {
        requires(drive);
    }
    
    protected void initialize()
    {
    }
    
    protected void execute()
    {
        drive.resetEncoders();
        drive.resetGyro();
    }
    
    protected boolean isFinished()
    {
        return true;
    }
    
    protected void end()
    {
        
    }
    
    protected void interrupted()
    {
        
    }
}
