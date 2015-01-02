/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package theory6.commands.AutonCommands;

import theory6.commands.CommandBase;

/**
 *
 * @author Controls
 */
public class WaitCommand extends CommandBase{
    private double wait;
    public WaitCommand(double wait)
    {
        this.wait = wait;
    }
    
    protected void initialize()
    {
    }
    
    protected void execute()
    {
        while(!isFinished())
            setTimeout(wait);
    }
    
    protected boolean isFinished()
    {
        return isTimedOut();
    }
    
    protected void end()
    {
        
    }
    
    protected void interrupted()
    {
        
    }
    
}
