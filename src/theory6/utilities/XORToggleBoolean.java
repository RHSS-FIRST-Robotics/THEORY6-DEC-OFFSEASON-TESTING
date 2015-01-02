/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package theory6.utilities;

/**
 *
 * @author Mahrus
 */
public class XORToggleBoolean {
    
    boolean state = false;
    
    public void set(boolean input)
    {
        if(input == true ^ state == true)
            state = true;
        else if(input == true ^ state == false)
            state = false;
    }
     
    
    
    public boolean get()
    {
        return state;
    }
    
}
