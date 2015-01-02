/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.utilities;

/**
 *
 * @author Team 1241
 * Modified by Mahrus Kazi
 */
import theory6.commands.CommandBase;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.LineReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;

public class Constants{
    private static Constants instance;
    public static Hashtable constants;
    private static boolean  usingFile;
    public static String file;
    
    private Constants(){
        usingFile = true;
        this.load();
    }
    
    public static Constants getInstance(String filePath){
        if(instance == null)
        {
            file = filePath;
            instance = new Constants();
        }
        return instance;
    }
    
    
    public static void load(){

        constants = getDefaults();
        System.out.println("loading File");

        if(usingFile){
            InputStream is = null;
            try {
                System.out.println("creating FileConnection");
                FileConnection f = (FileConnection) Connector.open("file:///" + file, Connector.READ_WRITE);
                System.out.println("created FileConnection - Class: ");
                if(f.exists()){
                    System.out.println("FileConnection exists");
                    is = f.openInputStream();
                    LineReader r = new LineReader(new InputStreamReader(is));
                    
                    String line = null;
                    int lineNum = 0;
                    while((line = r.readLine()) != null){
                        lineNum ++;
                        if(line.length() != 0 && line.charAt(0) != '#'){
                            int numSignPos = line.indexOf("#");
                            if(numSignPos > 0){
                                line = line.substring(0, numSignPos);
                            }
                            int equalsSignPos = line.indexOf("=");
                            if(equalsSignPos <= 1){
                                System.out.println("INVALID SETTING LINE: " + line + " (" + lineNum + ")");
                            }
                            else{
                                String key = line.substring(0, equalsSignPos - 1).trim();
                                String value = line.substring(equalsSignPos + 1).trim();
                                
                                if(key.length() > 0 && value.length() > 0){
                                    System.out.println("putting to HashTable: " + key + " = " + value);
                                    constants.put(key, value);
                                }
                            }
                        }

                    }
                }else{
                    f.create();
                    System.out.println("File has been Created");
                }
                f.close();
            } catch (Exception e) {
                System.out.println("failed to open fileConnection");
            }
        }
        
    }
    
    public static void updatePreferences()
    {
        int equalPos = 0;
        int commaPos = 0;
        int counter = 0;
        String[] keyHolder = new String[numOfConstants()];
        Vector vector = new Vector();
        String key;
        String value;
        
        String text = constants.toString();
        vector = CommandBase.prefs.getKeys();
        equalPos = text.indexOf("=", equalPos+1);
        while(equalPos >= 0)
        {
            key = text.substring(commaPos+1, equalPos).trim();
            keyHolder[counter] = key;
            commaPos = text.indexOf(",", commaPos+1);
            if(commaPos > 0)
                value = text.substring(equalPos+1, commaPos).trim();
            else
                value = text.substring(equalPos+1, text.length()-1).trim();

            CommandBase.prefs.putDouble(key, Double.parseDouble(value));

            equalPos = text.indexOf("=", equalPos+1);
            counter++;
        }
        System.out.println("Preferences Updated");
        //System.out.println("SIZE: " + vector.size() + " Key-----> " + keyHolder.length);
        if(vector.size() > keyHolder.length)
        {
            for(int x = 0; x < vector.size(); x++)
            {
                for(int i = 0; i < keyHolder.length; i++)
                {
                    //System.out.println(keyHolder[i] + "<----------->" + vector.elementAt(x).toString());
                    if(keyHolder[i].equals(vector.elementAt(x).toString()))
                        i = keyHolder.length;
                    else
                        if(i == (keyHolder.length-1)){
                            System.out.println("REMOVED FROM PREF-----> " + vector.elementAt(x).toString());
                            CommandBase.prefs.remove(vector.elementAt(x).toString());
                        }
                }
            }
            //System.out.println("----------------------------ENTERED-----------------------------------");
        }
    }
    
    public static void updateConstants()
    {
        Vector vector = new Vector();
        String[] keyHolder = nameOfConstants();
        String value;
        int equalPos = 0;
        int commaPos = 0;
        String key;
        String val;
        
        vector = CommandBase.prefs.getKeys();
        for(int x = 0; x < vector.size(); x++)
        {
            constants.remove(vector.elementAt(x)); 
            value = CommandBase.prefs.getString(vector.elementAt(x).toString(), "1");
            constants.put(vector.elementAt(x), value);
        }
        if(keyHolder.length > vector.size())
        {
            for(int x = 0; x < keyHolder.length; x++)
            {
                for(int i = 0; i < vector.size(); i++)
                {
                    if(keyHolder[x].equals(vector.elementAt(i).toString()))
                        i = vector.size();
                    else
                        if(i == (vector.size() - 1)){
                            System.out.println("CONSTANT REMOVED: " + keyHolder[x]);
                            constants.remove(keyHolder[x]);
                        }
                }
            }
        }
       
        CommandBase.log.fileCreate("constants.txt");
        String text = constants.toString();
        equalPos = text.indexOf("=", equalPos+1);

        while(equalPos >= 0)
        {
            key = text.substring(commaPos+1, equalPos).trim();

            commaPos = text.indexOf(",", commaPos+1);
            if(commaPos > 0)
                val = text.substring(equalPos+1, commaPos).trim();
            else
                val = text.substring(equalPos+1, text.length()-1).trim();

            CommandBase.log.writeStatement(key, val);
            equalPos = text.indexOf("=", equalPos+1);
        }
        System.out.println("Constants Updated");
    }
    
    public static int numOfConstants()
    {
        int equalPos = 0;
        int commaPos = 0;
        int num = 0;
        String text = constants.toString();
        equalPos = text.indexOf("=", equalPos+1);

        while(equalPos >= 0)
        {
            commaPos = text.indexOf(",", commaPos+1);
            equalPos = text.indexOf("=", equalPos+1);
            num++;
        }
        return num;
    }
    
    public static String[] nameOfConstants()
    {
        int equalPos = 0;
        int commaPos = 0;
        int num = 0;
        String[] keyHolder = new String[numOfConstants()];
        String key;
        String text = constants.toString();
        equalPos = text.indexOf("=", equalPos+1);
        while(equalPos >= 0)
        {
            key = text.substring(commaPos+1, equalPos).trim();
            keyHolder[num] = key;
            commaPos = text.indexOf(",", commaPos+1);
            equalPos = text.indexOf("=", equalPos+1);
            num++;
        }
        return keyHolder;
    }
    
    private static Hashtable getDefaults(){
        System.out.println("putting defaults");
        Hashtable defaults = new Hashtable();
        System.out.println("defaults put");
        return defaults;
    }
    
    public static void replace(String constName, String val)
    {
        int equalPos = 0;
        int commaPos = 0;
        String key;
        String value;
        
        constants.remove(constName);
        constants.put(constName, val);
        System.out.println("Updated Constant");
        CommandBase.log.fileCreate("constants.txt");
        String text = constants.toString();
        equalPos = text.indexOf("=", equalPos+1);

        while(equalPos >= 0)
        {
            key = text.substring(commaPos+1, equalPos).trim();

            commaPos = text.indexOf(",", commaPos+1);
            if(commaPos > 0)
                value = text.substring(equalPos+1, commaPos).trim();
            else
                value = text.substring(equalPos+1, text.length()-1).trim();

            CommandBase.log.writeStatement(key, value);
            equalPos = text.indexOf("=", equalPos+1);
        }
        
    }
    
    
    public static String getString(String constName, String def) {
        Object val = constants.get(constName);
        if (val == null){
            System.out.println("Failed to return constant: " + constName);
            return def;
        }else{
            return (String) val;
        }
    }
    
    public static double getDouble (String constName) {
        try {
            double val = Double.parseDouble(getString(constName, ""));
            return val;
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int getInteger(String constName) {
        try {
            int val = Integer.parseInt(getString(constName, ""));
            return val;
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static long getLong(String constName) {
        try {
            long val = Long.parseLong(getString(constName,""));
            return val;
        } catch(NumberFormatException e){
            return 0;
        }
    }
    
    public static boolean getBoolean (String constName) {
        try {
            boolean val = getString(constName, "").toLowerCase().equals("true");
            if(getString(constName, "").toLowerCase().equals("false")){
                return val;
            }else{
                return false;
            }
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
