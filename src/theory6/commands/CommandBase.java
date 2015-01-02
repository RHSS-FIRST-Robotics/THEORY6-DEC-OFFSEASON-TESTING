package theory6.commands;

import theory6.subsystems.DriveTrain;
import theory6.main.OI;
import theory6.utilities.CSVLogger;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import theory6.utilities.DataOutput;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    // Create a single static instance of all of your subsystems

    public static DriveTrain drive;
    public static DriverStationLCD ds;
    public static OI oi;
    public static DataOutput log;
    public static Preferences prefs;
    
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.

        // Show what command your subsystem is running on the SmartDashboard
        drive = DriveTrain.getInstance();
        oi = OI.getInstance();
        ds = DriverStationLCD.getInstance();
        prefs = Preferences.getInstance();
        log = new DataOutput();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
