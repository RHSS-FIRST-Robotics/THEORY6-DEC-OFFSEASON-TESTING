/*
 * Author: Mahrus Kazi
 * 
 * Reference Material:
 * https://www.youtube.com/watch?v=ct_uGOSPtok
 * https://www.desmos.com/calculator/cahqdxeshd
 */

import java.util.Vector;
public class BezierCurves {
  public Vector vector = new Vector();
  public static double[] xValues = new double[4];
  public static double[] yValues = new double[4];
  
  public BezierCurves (String startPoint, String controlPoint1, String controlPoint2, String endPoint)
  {
    vector.addElement(startPoint);
    vector.addElement(controlPoint1);
    vector.addElement(controlPoint2);
    vector.addElement(endPoint);
    putPoints();
  }
  
  public Vector findPoints()
  {
    Vector points = new Vector();
    
    for(double x = 0; x <= 1.05; x++)
      points.addElement(useFunctionX(xValues[0], xValues[1], xValues[2], xValues[3], x) + ", " + useFunctionY(yValues[0], yValues[1], yValues[2], yValues[3], x));
    
    return points;
  }
  
  public double useFunctionX(double zeroX, double oneX, double twoX, double threeX, double time)
  {
    double num = MathLogic.powerOf(1 - time, 3)*zeroX;
    double num2 = 3*MathLogic.powerOf(1 - time, 2)*time*oneX;
    double num3 = 3*(1 - time)*MathLogic.powerOf(time, 2)*twoX;
    double num4 = MathLogic.powerOf(time, 3)*threeX;
    
    return num + num2 + num3 + num4;
  }
  
  public double useFunctionY(double zeroY, double oneY, double twoY, double threeY, double time)
  {
    double num = MathLogic.powerOf(1 - time, 3)*zeroY;
    double num2 = 3*MathLogic.powerOf(1 - time, 2)*time*oneY;
    double num3 = 3*(1 - time)*MathLogic.powerOf(time, 2)*twoY;
    double num4 = MathLogic.powerOf(time, 3)*threeY;
    
    return num + num2 + num3 + num4;
  }
  
  public void putPoints()
  {
    String point;
    String xPoint;
    String yPoint;
    double x;
    double y;
    int commaPos;
    for(int i = 0; i < vector.size(); i++){
        point = vector.elementAt(i).toString();
        commaPos = point.indexOf(",");
        xPoint = point.substring(0, commaPos).trim();
        x = Double.parseDouble(xPoint);
        yPoint = point.substring(commaPos+1).trim();
        y = Double.parseDouble(yPoint);        
        xValues[i] = x;
        yValues[i] = y;
    }  
  }
  
}
