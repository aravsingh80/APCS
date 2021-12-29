// Name: J1-24
// Date: 5-28-2021
 
import java.util.*;
import java.io.*;

public class TeamBuilder
{
   public static void main(String[] args) 
   {
      String[] path = {"010", "000", "110"};	   
      //String[] path = {"0010", "1000", "1100", "1000"};
      //String[] path = {"01000", "00100", "00010", "00001", "10000"};
      //String[] path = {"0110000", "1000100", "0000001", "0010000", "0110000", "1000010", "0001000"};
      
      int[] ret = specialLocations(path);
      System.out.println("{" + ret[0] + ", " + ret[1] + "}");
   }

   public static int[] specialLocations(String[] paths)
   {
      int[] toRet = new int[2];
      int[][] p = new int[paths.length][paths.length];
      for(int x = 0; x < p.length; x++)
      {
         String s = paths[x];
         for(int y = 0; y < p.length; y++)
         {
            p[x][y] = Integer.parseInt("" + s.charAt(y));
         }
      }
      for (int k = 0; k < p.length; k++)
      {
         for (int i = 0; i < p.length; i++)
         {
            for (int j = 0; j < p.length; j++)
            {
               if(p[i][k] == 1 && p[k][j] == 1) 
                  p[i][j] = 1;
            }
         }
      }
      toRet[0] = 0;
      toRet[1] = 0;
      for(int x = 0; x < p.length; x++)
      {
         int count = 0;
         int count2 = 0;
         for(int y = 0; y < p.length; y++)
         {
            if(x == y)
            {
               count++;
               count2++;
            }
            else
            {
               if(p[x][y] == 1)
                  count++;
               if(p[y][x] == 1)
                  count2++;
            }
         }
         if(count == p.length)
            toRet[0]++;
         if(count2 == p.length)
            toRet[1]++;
      }
      return toRet;
   }
}