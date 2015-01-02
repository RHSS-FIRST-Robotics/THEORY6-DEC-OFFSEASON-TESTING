/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.utilities;

/**
 *
 * @author Sagar
 */
public class MathLogic {
    
    public static double limitAbs(double in, double limit) {
        if(limit < 0) {
            limit *= -1;
        }
        if(in < -limit) {
            in = -limit;
        }
        if(in > limit) {
            in = limit;
        }
        return in;
    }

    public static double PWMLimit(double in, double lim) {
        return limitAbs(in, lim);
    }

    public static double deadband(double value, double deadband, double center) {
        return (value < (center + deadband) && value > (center - deadband))
                ? center : value;
    }

    public static double degreesToRadians(double degrees){
        return (degrees * Math.PI) / 180.0;
    }
    
    public static double powerOf(double base,int power)
    {
        double hold = base;
        for(int x = 1; x < power; x++)
            base *= hold;
        
        if(power < 0)
            base /= 1;
        else if(power == 0)
            base = 1;
        
        return base;
    }
}
