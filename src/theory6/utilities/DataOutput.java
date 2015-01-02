package theory6.utilities;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import com.sun.squawk.microedition.io.FileConnection;

/**
 * https://docs.oracle.com/javase/7/docs/api/java/io/DataOutputStream.html
 * @author Team 1241
 */
public class DataOutput {

    private static OutputStream OS;
    private static PrintStream printStream;
    private static FileConnection kConnection;
    private static int counter = 0;
    /**
     * Creates the log through the class constructor.
     * @param String filePath (the path of the file to be written to)
     */
    public void fileCreate(String filePath) {
        
        try 
        {
            kConnection = (FileConnection) Connector.open("file:///" + filePath, Connector.READ_WRITE);
            
            kConnection.create();
            OS = kConnection.openOutputStream();
            printStream = new PrintStream(OS);
        } 
        
        catch (IOException e) 
        {
            System.out.println("Unable to create file log.");
        }
    }
    public void fileUpdate(String filePath) {
        
        try 
        {
            kConnection = (FileConnection) Connector.open("file:///" + filePath, Connector.READ_WRITE);
            
            OS = kConnection.openOutputStream();
            printStream = new PrintStream(OS);
        } 
        
        catch (IOException e) 
        {
            System.out.println("Unable to create file log.");
        }
    }
    
    public void writeText(String text)                                                     
    {
            printStream.print(text);
            printStream.println();
    }
    
    public void writeStatement(String text, String val)                                                     
    {
            printStream.print(text);
            printStream.print(" = ");
            printStream.print(val);
            printStream.println();
    }
    
    public void writeData(double elapsedTime, double leftEnc, double rightEnc, double gyro)
    {
        if(counter == 0)
        {
            printStream.print("Time");
            printStream.print(",");
            printStream.print("Encoder Distance");
            printStream.print(",");
            printStream.print("Rate");
            printStream.print(",");
            printStream.println("Angle");
            counter++;
        }
        printStream.print(elapsedTime);
        printStream.print(",");
        printStream.print(leftEnc);
        printStream.print(",");
        printStream.print(rightEnc);
        printStream.print(",");
        printStream.println(gyro);
    }  
    
    public void reset()
    {
        counter = 0;
    }
    
}//end class DataOutput
