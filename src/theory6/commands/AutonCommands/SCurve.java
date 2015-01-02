/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package theory6.commands.AutonCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Controls
 */
public class SCurve extends CommandGroup{
    
    public SCurve()
    {
        addSequential(new ResetSensorsCommand());
        addSequential(new DriveCommand(36, 1, 0, 5));
        addSequential(new DriveCommand(36, 1, 45, 5));
        addSequential(new DriveCommand(36, 1, -45, 5));
        addSequential(new DriveCommand(36, 1, 0, 5));
    }
}
