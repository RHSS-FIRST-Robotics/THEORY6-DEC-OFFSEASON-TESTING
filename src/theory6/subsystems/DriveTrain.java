/*----------------------------------------------------------------------------/
Author: Mahrus Kazi
Date: 28/11/2014

This class is used to control and recieve feedback from components on the 
drive train. 
Ex. Control the drive motors or recieve information from the gyroscope
/----------------------------------------------------------------------------*/
package theory6.subsystems;

import edu.wpi.first.wpilibj.Encoder;       // Used to get values from the encoder
import theory6.utilities.TheoryGyro;        // Used to get values from the gyro
import theory6.main.ElectricalConstants;    // Contains constants that won't change electrically Ex. Wire Ports 
import theory6.utilities.JoystickScaler;    // Used to control the sensitivity of the joysticks
import edu.wpi.first.wpilibj.Talon;         // Used to control the talon speed controllers
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import theory6.commands.CommandBase;
import theory6.pid.PIDController;
import theory6.utilities.MathLogic;
import theory6.utilities.Constants;


public class DriveTrain extends Subsystem {
    int counter = 0;
    static DriveTrain inst = null;
   
    //Makes an object for the left/right encoders and gives it a name
    Encoder leftDriveEncoder;               
    Encoder rightDriveEncoder;              
    
    //Makes an object for the gyroscope and gives it a name
    TheoryGyro driveGyro;
    
    //Makes an object for all the talons that control the right side of the drive
    Talon rightDriveFront;
    Talon rightDriveMiddle;
    Talon rightDriveBack;
    
    //Makes an object for all the talons that control the left side of the drive
    Talon leftDriveFront;
    Talon leftDriveMiddle;
    Talon leftDriveBack;
    
    //Makes an object for the analog sticks on the game pad to control the sensitivity of the joystick
    JoystickScaler leftAnalogScaler = new JoystickScaler();
    JoystickScaler rightAnalogScaler = new JoystickScaler();
    
    //Initialize drive PID controllers
    PIDController drivePID;
    PIDController anglePID;
    
    Timer timer;
    
    /*------------------------------------------------------------------------/
    Once called this will initialize all the objects created
    /------------------------------------------------------------------------*/
    public DriveTrain ()
    {
        Constants.getInstance("constants.txt");
        
        driveGyro = new TheoryGyro(ElectricalConstants.DRIVE_GYRO);

        leftDriveEncoder = new Encoder(ElectricalConstants.LEFT_ENCODER_A, 
                                       ElectricalConstants.LEFT_ENCODER_B, 
                                       ElectricalConstants.leftDriveTrainEncoderReverse, 
                                       Encoder.EncodingType.k4X);
        leftDriveEncoder.setDistancePerPulse(ElectricalConstants.driveEncoderDistPerTick);  
        leftDriveEncoder.start();
        rightDriveEncoder = new Encoder(ElectricalConstants.RIGHT_ENCODER_A,
                                        ElectricalConstants.RIGHT_ENCODER_B, 
                                        ElectricalConstants.rightDriveTrainEncoderReverse, 
                                        Encoder.EncodingType.k4X);        
        rightDriveEncoder.setDistancePerPulse(ElectricalConstants.driveEncoderDistPerTick); 
        rightDriveEncoder.start();


        rightDriveFront = new Talon(ElectricalConstants.RIGHT_DRIVE_FRONT);
        rightDriveMiddle = new Talon(ElectricalConstants.RIGHT_DRIVE_MIDDLE);
        rightDriveBack = new Talon(ElectricalConstants.RIGHT_DRIVE_BACK);
        
        leftDriveFront = new Talon(ElectricalConstants.LEFT_DRIVE_FRONT);
        leftDriveMiddle = new Talon(ElectricalConstants.LEFT_DRIVE_MIDDLE);
        leftDriveBack = new Talon(ElectricalConstants.LEFT_DRIVE_BACK);
        
        timer = new Timer();
        drivePID = new PIDController(Constants.getDouble("pDrive"), Constants.getDouble("iDrive"), Constants.getDouble("dDrive"));
        anglePID = new PIDController(Constants.getDouble("pGyro"), Constants.getDouble("iGyro"), Constants.getDouble("dGyro"));
        System.out.println(Constants.getDouble("pDrive") + " "+ Constants.getDouble("iDrive")+ " "+ Constants.getDouble("dDrive"));
        System.out.println(Constants.getDouble("pGyro") + " "+ Constants.getDouble("iGyro")+ " "+ Constants.getDouble("dGyro"));
    }
    
    protected void initDefaultCommand()
    {
        
    }
    
    /*------------------------------------------------------------------------/
    This function will make sure the Drivetrain class is only created once 
    /------------------------------------------------------------------------*/
    
    public static DriveTrain getInstance() {
        if(inst == null) 
            inst = new DriveTrain();
        
        return inst;
    }
    
    /*------------------------------------------------------------------------/
    This function can individually control the left side of the drive, using a 
    value from -1 to 1. 
    /------------------------------------------------------------------------*/
    
    public void runLeftDrive (double pwmVal)
    {
     leftDriveFront.set(pwmVal);
     leftDriveMiddle.set(pwmVal);
     leftDriveBack.set(pwmVal);
    }
    
    /*------------------------------------------------------------------------/
    This function can individually control the right side of the drive, using a 
    value from -1 to 1. 
    /------------------------------------------------------------------------*/
    
    public void runRightDrive (double pwmVal)
    { 
     rightDriveFront.set(pwmVal);
     rightDriveMiddle.set(pwmVal);
     rightDriveBack.set(pwmVal);
    }
    
    public void driveStraight(double setPoint, double speed, double setAngle)
    {
        if(counter == 0)
        {
            timer.start();
            CommandBase.log.fileCreate("output.txt");
            drivePID.changePIDGains(Constants.getDouble("pDrive"), Constants.getDouble("iDrive"), Constants.getDouble("dDrive"));
        }
        
        double leftOutput = drivePID.calcPID(setPoint, getLeftEncoderDist());
        double rightOutput = drivePID.calcPID(setPoint, getRightEncoderDist());
        double output = (rightOutput + leftOutput)/2;
        double angle = anglePID.calcPID(setAngle, getGyroAngle());
        
        if(setAngle < 0){
            leftOutput = MathLogic.PWMLimit(output, speed) + MathLogic.PWMLimit(angle, speed);
            rightOutput = -MathLogic.PWMLimit(output, speed);
        }
        else if(setAngle > 0){
            leftOutput = MathLogic.PWMLimit(output, speed);
            rightOutput = -MathLogic.PWMLimit(output, speed) + MathLogic.PWMLimit(angle, speed);
        }
        else{
            leftOutput = MathLogic.PWMLimit(output, speed) + MathLogic.PWMLimit(angle, speed);
            rightOutput = -MathLogic.PWMLimit(output, speed) + MathLogic.PWMLimit(angle, speed);
        }
        
        runLeftDrive(leftOutput);
        runRightDrive(rightOutput);
        
        if(counter == 26)
        {
            CommandBase.log.writeData(timer.get(), rightOutput, -MathLogic.PWMLimit(output, speed), MathLogic.PWMLimit(angle, speed));
            counter = 1;
        }
        counter++;
    }
    
    public void reset()
    {
        timer.reset();
        resetEncoders();
        resetGyro();
        counter = 0;
        CommandBase.log.reset();
    }
    
    /************************ENCODER FUNCTIONS************************/

    public void stopEncoders(){
        leftDriveEncoder.stop();
        rightDriveEncoder.stop();
    }
    
    public void startEncoders(){
        leftDriveEncoder.start();
        rightDriveEncoder.start();
    }

    public double getLeftEncoderDist(){
        return leftDriveEncoder.getDistance();
    }

    public double getRightEncoderDist(){
        return rightDriveEncoder.getDistance();
    }

    public double getLeftEncoderRaw(){
        return leftDriveEncoder.getRaw();
    }

    public double getRightEncoderRaw(){
        return rightDriveEncoder.getRaw();
    }

    public double getLeftEncoderRate(){
        return leftDriveEncoder.getRate();
    }

    public double getRightEncoderRate(){
        return rightDriveEncoder.getRate(); 
    }

    public void resetEncoders() {
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
    }
        
    /************************GYRO FUNCTIONS************************/
    
    public double getGyroAngle(){
        double angle = (driveGyro.getAngle() / 180)*180.0;
        if(angle > 360 || angle < -360){
            resetGyro();
            angle = 0.0;
        }
        return angle;
    }
    
    public void resetGyro(){
        driveGyro.reset();
    }
    
    public void recalibrateGyro(){
        driveGyro.initGyro();
    }
    
}


