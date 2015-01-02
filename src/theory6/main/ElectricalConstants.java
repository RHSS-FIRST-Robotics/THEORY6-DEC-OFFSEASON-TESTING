/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.main;

/**
 *
 * @author Sagar
 */
public class ElectricalConstants {
    
    //**************************************************************************
    //****************************** PWMs **************************************
    //**************************************************************************        

    public static final int RIGHT_DRIVE_FRONT                               = 4; 
    public static final int RIGHT_DRIVE_MIDDLE                              = 5;
    public static final int RIGHT_DRIVE_BACK                                = 6;
    
    public static final int LEFT_DRIVE_FRONT                                = 1;
    public static final int LEFT_DRIVE_MIDDLE                               = 2;
    public static final int LEFT_DRIVE_BACK                                 = 3;
    
    //**************************************************************************
    //***************************Analog Sensors*********************************
    //**************************************************************************

    public static final int DRIVE_GYRO                                      = 1;
    
    //**************************************************************************
    //*************************** ENCODERS *************************************
    //**************************************************************************
    
    public static final int LEFT_ENCODER_A                                  = 3;
    public static final int LEFT_ENCODER_B                                  = 4;
    
    public static final int RIGHT_ENCODER_A                                 = 1;
    public static final int RIGHT_ENCODER_B                                 = 2;
    
    //**************************************************************************
    //*************************** Digital Sensors ******************************
    //**************************************************************************
  


    //*************************** ENCODER CONSTANTS ****************************
    public static final int driveWheelRadius = 2;//wheel radius in inches
    public static final int pulsePerRotation = 128; //encoder pulse per rotation
    public static final double gearRatio = 1/1; //ratio between wheel and encoder
    public static final double driveEncoderPulsePerRot = pulsePerRotation*gearRatio; //pulse per rotation * gear ratio
    public static final double driveEncoderDistPerTick =(Math.PI*2*driveWheelRadius)/driveEncoderPulsePerRot;
    public static final boolean rightDriveTrainEncoderReverse = true; 
    public static final boolean leftDriveTrainEncoderReverse = false; 

   //***************************************************************************
   //*************************** Pneumatics ************************************
   //***************************************************************************


   //***************************************************************************
   //*************************** Relays ****************************************
   //***************************************************************************


}
