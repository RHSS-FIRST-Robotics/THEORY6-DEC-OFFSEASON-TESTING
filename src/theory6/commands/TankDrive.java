/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.commands;
/**
 *
 * @author Mahrus
 */
public class TankDrive extends CommandBase {
    
    public TankDrive()
    {
        requires(drive);
        requires(oi);
    }
    
    protected void initialize()
    {
    }
    
    protected void execute()
    {
        drive.runLeftDrive(-oi.getLeftY());
        drive.runRightDrive(oi.getRightY());
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
