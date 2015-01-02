/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//https://www.khanacademy.org/math/precalculus/precalc-matrices
package theory6.utilities;

import java.util.Vector;

/**
 *
 * @author Mahrus Kazi
 */
public class Matrix {
  public Vector vector;
  public double[][] answer;
  public double[][] theMatrix;
  public double[][] inverseMatrix;
  public double largestX = 0;
  public double smallestX = 0;
  public String name;
   
  public Matrix (String point1, String point2, String point3, String point4, String point5, String point6, String name)
  {
    theMatrix = new double[6][6];
    answer = new double[6][1];
    vector = new Vector();
    vector.addElement(point1);
    vector.addElement(point2);
    vector.addElement(point3);
    vector.addElement(point4);
    vector.addElement(point5);
    vector.addElement(point6);
    this.name = name;
  }
 
  public Matrix (String point1, String point2, String point3, String point4, String name)
  {
    theMatrix = new double[4][4];
    answer = new double[4][1];
    vector = new Vector();
    vector.addElement(point1);
    vector.addElement(point2);
    vector.addElement(point3);
    vector.addElement(point4);
    this.name = name;
  }
  
  public String getName()
  {
      return name;
  }
  
  public void changePoints(String point1, String point2, String point3, String point4, String point5, String point6)
  {
        theMatrix = new double[6][6];
        answer = new double[6][1];
        vector = new Vector();
        vector.addElement(point1);
        vector.addElement(point2);
        vector.addElement(point3);
        vector.addElement(point4);
        vector.addElement(point5);
        vector.addElement(point6);
  }
  
  public void changePoints(String point1, String point2, String point3, String point4)
  {
        theMatrix = new double[4][4];
        answer = new double[4][1];
        vector = new Vector();
        vector.addElement(point1);
        vector.addElement(point2);
        vector.addElement(point3);
        vector.addElement(point4);
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
        compareX(x);
        yPoint = point.substring(commaPos+1).trim();
        y = Double.parseDouble(yPoint);
        if(vector.size() == 6)
        {
            answer[i][0] = y;
            theMatrix[i][0] = MathLogic.powerOf(x, 5);
            theMatrix[i][1] = MathLogic.powerOf(x, 4);
            theMatrix[i][2] = MathLogic.powerOf(x, 3);
            theMatrix[i][3] = MathLogic.powerOf(x, 2);
            theMatrix[i][4] = MathLogic.powerOf(x, 1);
            theMatrix[i][5] = MathLogic.powerOf(x, 0);
        }
        else if(vector.size() == 4)
        {
            answer[i][0] = y;
            theMatrix[i][0] = MathLogic.powerOf(x, 3);
            theMatrix[i][1] = MathLogic.powerOf(x, 2);
            theMatrix[i][2] = MathLogic.powerOf(x, 1);
            theMatrix[i][3] = MathLogic.powerOf(x, 0);
        }
    }  
    System.out.println("POINTS ADDED");
  }
  
  public double[][] findFunction()
  {   
      putPoints();
      inverseMatrix = adjugate(theMatrix);
      inverseMatrix = scalerDivide(determinant(), inverseMatrix);
      answer = vectorMultiplication(inverseMatrix, answer);
      System.out.println();
      for(int x = 0; x < answer.length; x++)
          System.out.println(x + ": " + answer[x][0]);
      System.out.println("MADE FUNCTION");
      return answer;
  }
  
  public Vector findPoints()
  {
      Vector points = new Vector();
      String value;
      for(int x = 0; x < largestX+1; x++){
          value = (x + ", " + function(x));
          points.addElement(value);
      }
      return points;
  }
  
  public double function(double x)
  {
      double y = 0;
      
      if(answer.length == 6)
          y = (answer[0][0]*MathLogic.powerOf(x, 5) + answer[1][0]*MathLogic.powerOf(x, 4) 
             + answer[2][0]*MathLogic.powerOf(x, 3) + answer[3][0]*MathLogic.powerOf(x, 2) + answer[4][0]*x);
      else if(answer.length == 4)
          y = (answer[0][0]*MathLogic.powerOf(x, 3) + answer[1][0]*MathLogic.powerOf(x, 2) + answer[2][0]*x);
      
      return y;
  }
  
  public void compareX(double value)
  {
      if(value > largestX)
          largestX = value;
      else if(value < smallestX)
          smallestX = value;
  }
  
  public double determinant()
  {
      double value = 0;
      if(theMatrix.length == 6)
          value = sixbysix(theMatrix);
      else if(theMatrix.length == 4)
          value = fourbyfour(theMatrix);
      
      return value;
  }
  
  public double[][] cofactorMatrix(double[][] matrix)
  {
    double[][] subMatrix = new double[2][2];
    double[][] matrixMajor = new double[matrix.length][matrix.length];
    int counter = 1;
    
    for(int m = 0, z = 0; m < matrix.length; m++, z++){
      for(int p = 0, y = 0; p < matrix.length; p++, y++)
      {
        for(int row = 0, o = 0; row < subMatrix.length+1; row++, o++)
          for(int column = 0, n = 0; column < subMatrix.length + counter; column++, n++)
          {
            if(m == row && subMatrix.length != row)
              row++; 
            if(p == column)
              column++; 
            if(o != 2)
              subMatrix[o][n] = matrix[row][column];
          }
        counter = 0;
        matrixMajor[z][y] = (subMatrix[0][0]*subMatrix[1][1])-(subMatrix[0][1]*subMatrix[1][0]);
      }
      counter = 1;
    }
    for(int row = 0; row < matrixMajor.length; row++)
      for(int column = 0; column < matrixMajor[0].length; column++)
      {
        if(column%2 != 0 && row%2 == 0)
          matrixMajor[row][column] = -matrixMajor[row][column];
        else if(column%2 == 0 && row%2 != 0)
          matrixMajor[row][column] = -matrixMajor[row][column];
      }
    return transpose(matrixMajor);
  }
  
  public double sixbysix(double[][] matrix)
  {
    double[][] fivebyfive = new double[5][5];
    double[][] fourByFour = new double[4][4];
    double[][] threeByThree = new double[3][3];
    double[][] twoByTwo = new double[2][2];
    double determinant;
    
    if(matrix[0][0] == 0){
      matrix = swap(matrix);
      determinant = -1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    }
    else  
      determinant = 1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    
    for(int row = 0; row < fivebyfive.length; row++)
      for(int column = 0; column < fivebyfive[0].length; column++)
        fivebyfive[row][column] = (matrix[0][0]*matrix[row+1][column+1])-(matrix[0][column+1]*matrix[row+1][0]);
    
    if(fivebyfive[0][0] == 0){
      fivebyfive = swap(fivebyfive);
      determinant = -determinant*(1/(MathLogic.powerOf(fivebyfive[0][0], fivebyfive.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(fivebyfive[0][0], fivebyfive.length - 2)));
    
    for(int row = 0; row < fourByFour.length; row++)
      for(int column = 0; column < fourByFour[0].length; column++)
        fourByFour[row][column] = (fivebyfive[0][0]*fivebyfive[row+1][column+1])-(fivebyfive[0][column+1]*fivebyfive[row+1][0]);
    
    if(fourByFour[0][0] == 0){
      fourByFour = swap(fourByFour);
      determinant = -determinant*(1/(MathLogic.powerOf(fourByFour[0][0], fourByFour.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(fourByFour[0][0], fourByFour.length - 2)));
    
    for(int row = 0; row < threeByThree.length; row++)
      for(int column = 0; column < threeByThree[0].length; column++)
        threeByThree[row][column] = (fourByFour[0][0]*fourByFour[row+1][column+1])-(fourByFour[0][column+1]*fourByFour[row+1][0]);
    
    if(threeByThree[0][0] == 0){
      threeByThree = swap(threeByThree);
      determinant = -determinant*(1/(MathLogic.powerOf(threeByThree[0][0], threeByThree.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(threeByThree[0][0], threeByThree.length - 2)));
    
    for(int row = 0; row < twoByTwo.length; row++)
      for(int column = 0; column < twoByTwo[0].length; column++)
        twoByTwo[row][column] = (threeByThree[0][0]*threeByThree[row+1][column+1])-(threeByThree[0][column+1]*threeByThree[row+1][0]);
    
    if(twoByTwo[0][0] == 0){
      twoByTwo = swap(twoByTwo);
      determinant = -determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    
    if(determinant != 0 && (twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]) != 0)
      determinant = determinant*(twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]);
    else
      determinant = 0;
    return determinant;
  }
  
  public double fivebyfive(double[][] matrix)
  {
    double[][] fourByFour = new double[4][4];
    double[][] threeByThree = new double[3][3];
    double[][] twoByTwo = new double[2][2];
    double determinant = 1;
    
    if(matrix[0][0] == 0){
      matrix = swap(matrix);
      determinant = -1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    }
    else  
      determinant = 1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    
    for(int row = 0; row < fourByFour.length; row++)
      for(int column = 0; column < fourByFour[0].length; column++)
        fourByFour[row][column] = (matrix[0][0]*matrix[row+1][column+1])-(matrix[0][column+1]*matrix[row+1][0]);
    
    if(fourByFour[0][0] == 0){
      fourByFour = swap(fourByFour);
      determinant = -determinant*(1/(MathLogic.powerOf(fourByFour[0][0], fourByFour.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(fourByFour[0][0], fourByFour.length - 2)));
    
    for(int row = 0; row < threeByThree.length; row++)
      for(int column = 0; column < threeByThree[0].length; column++)
        threeByThree[row][column] = (fourByFour[0][0]*fourByFour[row+1][column+1])-(fourByFour[0][column+1]*fourByFour[row+1][0]);
    
    if(threeByThree[0][0] == 0){
      threeByThree = swap(threeByThree);
      determinant = -determinant*(1/(MathLogic.powerOf(threeByThree[0][0], threeByThree.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(threeByThree[0][0], threeByThree.length - 2)));
    
    for(int row = 0; row < twoByTwo.length; row++)
      for(int column = 0; column < twoByTwo[0].length; column++)
        twoByTwo[row][column] = (threeByThree[0][0]*threeByThree[row+1][column+1])-(threeByThree[0][column+1]*threeByThree[row+1][0]);
    
    if(twoByTwo[0][0] == 0){
      twoByTwo = swap(twoByTwo);
      determinant = -determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    
    if(determinant != 0 && (twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]) != 0)
      determinant = determinant*(twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]);
    else
      determinant = 0;

    return determinant;
  }
  
  public double fourbyfour(double[][] matrix)
  {
    double[][] threeByThree = new double[3][3];
    double[][] twoByTwo = new double[2][2];
    double determinant;
    
    if(matrix[0][0] == 0){
      matrix = swap(matrix);
      determinant = -1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    }
    else  
      determinant = 1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    
    for(int row = 0; row < threeByThree.length; row++)
      for(int column = 0; column < threeByThree[0].length; column++)
        threeByThree[row][column] = (matrix[0][0]*matrix[row+1][column+1])-(matrix[0][column+1]*matrix[row+1][0]);
 
    if(threeByThree[0][0] == 0){
      threeByThree = swap(threeByThree);
      determinant = -determinant*(1/(MathLogic.powerOf(threeByThree[0][0], threeByThree.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(threeByThree[0][0], threeByThree.length - 2)));
    
    for(int row = 0; row < twoByTwo.length; row++)
      for(int column = 0; column < twoByTwo[0].length; column++)
        twoByTwo[row][column] = (threeByThree[0][0]*threeByThree[row+1][column+1])-(threeByThree[0][column+1]*threeByThree[row+1][0]);
    
    if(twoByTwo[0][0] == 0){
      twoByTwo = swap(twoByTwo);
      determinant = -determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    
    determinant = determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    
    if(determinant != 0 && (twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]) != 0)
      determinant = determinant*(twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]);
    else
      determinant = 0;
    
    return determinant;
  }
  
  public double threebythree(double[][] matrix)
  {
    double[][] twoByTwo = new double[2][2];
    double determinant;
    
    if(matrix[0][0] == 0){
      matrix = swap(matrix);
      determinant = -1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    }
    else  
      determinant = 1/(MathLogic.powerOf(matrix[0][0], matrix.length - 2));
    
    for(int row = 0; row < twoByTwo.length; row++)
      for(int column = 0; column < twoByTwo[0].length; column++)
        twoByTwo[row][column] = (matrix[0][0]*matrix[row+1][column+1])-(matrix[0][column+1]*matrix[row+1][0]);
    
    if(twoByTwo[0][0] == 0){
      twoByTwo = swap(twoByTwo);
      determinant = -determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    }
    else  
      determinant = determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    
    determinant = determinant*(1/(MathLogic.powerOf(twoByTwo[0][0], twoByTwo.length - 2)));
    
    if(determinant != 0 && (twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]) != 0)
      determinant = determinant*(twoByTwo[0][0]*twoByTwo[1][1] - twoByTwo[0][1]*twoByTwo[1][0]);
    else
      determinant = 0;
    
    return determinant;
  }
  
  public double[][] scalerDivide(double scaler, double[][] matrix)
  {
    double[][] ans = new double[matrix.length][matrix[0].length];
    
    for(int row = 0; row < matrix.length; row++){
      for(int column = 0; column < matrix[0].length; column++)
      {
        if(matrix[row][column] != 0)
          ans[row][column] = matrix[row][column]/scaler;
        else
          ans[row][column] = matrix[row][column];
      }
    }
    return ans;
  }
  
  public double[][] adjugate(double[][] matrix)
  {
    double[][] subMatrix = new double[matrix.length-1][matrix[0].length-1];
    double[][] coFactor = new double[matrix.length][matrix[0].length];
    int counter = 1;
    
    for(int m = 0, z = 0; m < matrix.length; m++, z++){
      for(int p = 0, y = 0; p < matrix.length; p++, y++)
      {
        for(int row = 0, o = 0; row < subMatrix.length+1; row++, o++)
        {
          for(int column = 0, n = 0; column < subMatrix.length + counter; column++, n++)
          {
            if(m == row)
              row++; 
            if(p == column)
              column++;
            if(o != matrix.length-1){
              subMatrix[o][n] = matrix[row][column];
            }
          }
        }
        if(vector.size() == 6)
            coFactor[z][y] = fivebyfive(subMatrix);
        else if(vector.size() == 4)
            coFactor[z][y] = threebythree(subMatrix);
        counter = 0;
      }
      counter = 1;
    }
    coFactor = changeSigns(coFactor);
    coFactor = transpose(coFactor);
    
    return coFactor;
  }
  
  public double[][] transpose(double[][] matrix)
  {
    double[][] adj = new double[matrix.length][matrix[0].length];
    for(int row = 0; row < adj.length; row++)
    {
      for(int column = 0; column < adj[0].length; column++)
      {
        adj[row][column] = matrix[column][row];
      }
    }
    return adj;
  }
  
  public double[][] changeSigns(double[][] matrix)
  {
    for(int row = 0; row < matrix.length; row++)
      for(int column = 0; column < matrix[0].length; column++)
      {
        if(column%2 != 0 && row%2 == 0)
          matrix[row][column] = -matrix[row][column];
        else if(column%2 == 0 && row%2 != 0)
          matrix[row][column] = -matrix[row][column];
      }
    return matrix;
  }
  
  public double[][] swap(double[][] matrix)
  {
    double[][] ans = new double[matrix.length][matrix[0].length];
    int zero = 0;
    int nonZero = 0;
    boolean checked = false;
    boolean checked1 = false;
    
    for(int column = 0; column < ans[0].length; column++)
    {
      if(matrix[0][column] == 0 && !checked1){
        zero = column;
        checked1 = true;
      }
      else if(matrix[0][column] != 0 && !checked){
        nonZero = column;
        checked = true;
      }
    }
    
    for(int x = 0; x < ans[0].length; x++){
      ans[x][zero] = matrix[x][nonZero];
      ans[x][nonZero] = matrix[x][zero];
    }
    
    for(int row = 0; row < ans.length; row++)
      for(int column = 0; column < ans[0].length; column++)
        if(column != nonZero && column != zero)
          ans[row][column] = matrix[row][column];

    return ans;
  }
  
  public double[][] vectorMultiplication(double[][] matrix, double[][] vector)
  {
    double[][] vec = new double[vector.length][vector[0].length];
    double ans = 0;
    double multiple;

    for(int row = 0; row < matrix.length; row++){
      for(int column = 0; column < matrix.length; column++){
        multiple = matrix[row][column]*vector[column][0];
        ans += multiple;
      }
      vec[row][0] = ans;
      ans = 0;
    }
    return vec;
  }
}