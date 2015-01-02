/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package theory6.utilities;

import theory6.pid.PIDController;

/**
 *
 * @author Mahrus
 */
public class PIDScale {
    public double firstOutput;
    public double output;
    public double max;
    public boolean isEnabled = false;
    
    public double driveScale(PIDController drive, double setpoint, double current)
    {
        output = drive.calcPID(setpoint, current);
        if(!isEnabled)
        {
            firstOutput = output;
            isEnabled = true;
        }
        
        output = output / firstOutput;
        
        if(output > 1.0)
            output = 1.0;
        else if(output < -1.0)
            output = -1.0;
        
        return output;
    }
    
    public double gyroScale(PIDController gyro, double setpoint, double current, double setMax)
    {
        if(!isEnabled)
        {
            max = gyro.calcPID(setMax, current);
            isEnabled = true;
        }
        output = gyro.calcPID(setpoint, current);
        if(output > 0.1 || output < -0.1)
            return output*(1/max);
        else
            return 0.0;
    }
    
    public void disable()
    {
        isEnabled = false;
    }
    
}
