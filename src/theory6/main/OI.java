
package theory6.main;

import theory6.utilities.ToggleBoolean;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import theory6.commands.AutonCommands.SCurve;

//http://nwctarobotics.com/javadoc/2013/edu/wpi/first/wpilibj/buttons/Button.html

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends Subsystem{
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    static OI inst = null;
    
    Joystick drivePad;
    ToggleBoolean toggle;
    InternalButton button = new InternalButton();;
    
    boolean bol = false;
    
    public OI()
    {
        drivePad = new Joystick (GamepadConstants.DRIVE_USB_PORT);
        toggle = new ToggleBoolean();
        SmartDashboard.putData("S Curve", button);
        button.whenPressed(new SCurve());
    }
    
    protected void initDefaultCommand()
    {
        
    }
    
    public static OI getInstance() {
        if(inst == null) 
            inst = new OI();
        
        return inst;
    }
    
    public double getRightY ()
    {
        double joy = drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y);
        if(Math.abs(joy) < 0.05)
            return 0.0;
        else
            return joy;
    }
    
    public double getLeftY ()
    {
        double joy = drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y);
        if(Math.abs(joy) < 0.05)
            return 0.0;
        else
            return joy;
    }
    
    public double getRightX()
    {
        double joy = drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_X);
        if(Math.abs(joy) < 0.05)
            return 0.0;
        else
            return joy;
    }
    
    public double getLeftX()
    {
        double joy = drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_X);
        if(Math.abs(joy) < 0.05)
            return 0.000;
        else
            return joy;
    }
    
    public boolean toggleRightBumper()
    {
        button.toggleWhenPressed(null);
        return toggle.get();
    }
    
    public double getRightTrigger()
    {
        return drivePad.getRawAxis(GamepadConstants.RIGHT_TRIGGER);
    }
    
    public boolean getLeftBumper()
    {
        return drivePad.getRawButton(GamepadConstants.LEFT_BUMPER);
    }
    
    public boolean getStartButton()
    {
        return drivePad.getRawButton(GamepadConstants.START_BUTTON);
    }
    
    public boolean getAButton()
    {
        return drivePad.getRawButton(GamepadConstants.A_BUTTON);
    }
    
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

