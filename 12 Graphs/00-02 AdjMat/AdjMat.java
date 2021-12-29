// Name: J1-24
// Date: 4-20-2021
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graph0 AdjMat_0_Driver,
 *              Graph1 WarshallDriver,
 *          and Graph2 FloydDriver
 */

interface AdjacencyMatrix
{
   void addEdge(int source, int target);
   void removeEdge(int source, int target);
   boolean isEdge(int from, int to);
   String toString();   //returns the grid as a String
   int edgeCount();
   List<Integer> getNeighbors(int source);
   List<String> getReachables(String from);  //Warshall extension
}

interface Warshall      //User-friendly functionality
{
   boolean isEdge(String from, String to);
   Map<String, Integer> getVertices();     
   void readNames(String fileName) throws FileNotFoundException;
   void readGrid(String fileName) throws FileNotFoundException;
   void displayVertices();
   void allPairsReachability();  // Warshall's Algorithm
}

interface Floyd
{
   int getCost(int from, int to);
   int getCost(String from, String to);
   void allPairsWeighted(); 
}

public class AdjMat implements AdjacencyMatrix, Warshall//,Floyd 
{
   private int[][] grid = null;   //adjacency matrix representation
   private Map<String, Integer> vertices = null;   // name maps to index (for Warshall & Floyd)
   /*for Warshall's Extension*/  ArrayList<String> nameList = null;  //reverses the map, index-->name
	  
   /*  enter your code here  */  
   public AdjMat(int size)
   {
      grid = new int[size][size];
      vertices = new TreeMap<String, Integer>();
      nameList = new ArrayList<String>();
      for(int x = 0; x < size; x++)
      {
         for(int y = 0; y < size; y++)
            grid[x][y] = 0;
      }
   }
   public void addEdge(int source, int target)
   {
      grid[source][target] = 1;
   }
   public void removeEdge(int source, int target)
   {
      grid[source][target] = 0;
   }
   public boolean isEdge(int from, int to)
   {
      int size = grid.length;
      if(from < 0 || from >= size || to < 0 || to >= size)
         return false;
      else if(grid[from][to] == 0)
         return false;
      else
         return true;
   }
   public boolean isEdge(String from2, String to2)
   {
      int from = vertices.get(from2);
      int to = vertices.get(to2);
      int size = grid.length;
      if(from < 0 || from >= size || to < 0 || to >= size)
         return false;
      else if(grid[from][to] == 0)
         return false;
      else
         return true;
   }
   public Map<String, Integer> getVertices()  
   {
      return vertices;
   }
   public void readNames(String fileName) throws FileNotFoundException
   {
      Scanner x = new Scanner(new File(fileName));
      String s = "" + x.next();
      int count = 0;
      while(x.hasNext())
      {
         s = x.next();
         vertices.put(s, count);
         count++;
      }
   }
   public void readGrid(String fileName) throws FileNotFoundException
   {
      Scanner x = new Scanner(new File(fileName));
      String s = "" + x.nextLine();
      int count = 0;
      while(x.hasNextLine())
      {
         s = x.nextLine();
         String[] s2 = s.split(" ");
         int count2 = 0;
         for(String s3 : s2)
         {
            grid[count][count2] = Integer.parseInt(s3);
            count2++;
         }
         count++;
      }
   }
   public void displayVertices()
   {
      for(String s : vertices.keySet())
      {
         System.out.println(vertices.get(s) + "-" + s);
      }
      System.out.println();
   }
   public void allPairsReachability()
   {
      int size = grid.length;
      for(int v = 0; v < size; v++)
      {
         for(int i = 0; i < size; i++)
         {
            for(int j = 0; j < size; j++)
            {
               if(grid[i][v] == 1 && grid[v][j] == 1)
                  grid[i][j] = 1;
            }
         }
      }
   }
   public String toString()
   {
      String s = "";
      int size = grid.length;
      for(int x = 0; x < size; x++)
      {
         for(int y = 0; y < size; y++)
         {
            s += "" + grid[x][y] + " ";
         }
         if(x != size - 1)
            s += "\n";
      }
      return s;
   }
   public int edgeCount()
   {
      int count = 0;
      int size = grid.length;
      for(int x = 0; x < size; x++)
      {
         for(int y = 0; y < size; y++)
         {
            if(grid[x][y] != 0 && grid[x][y] != 9999)
               count++;
         }
      }
      return count;
   }
   public List<Integer> getNeighbors(int source)
   {
      int size = grid.length;
      List<Integer> l = new ArrayList<Integer>();
      for(int x = 0; x < size; x++)
      {
         if(grid[source][x] == 1)
            l.add(x);
      }
      return l;
   }
   public List<String> getReachables(String from)
   {
      nameList.clear();
      int y = vertices.get(from);
      for(int x = 0; x < grid.length; x++)
      {
         if(grid[y][x] == 1)
         {
            int count = 0;
            for(String s : vertices.keySet())
            {
               if(count == x)
                  nameList.add(s);
               count++;
            }
         }
      }
      return nameList;
   }
   public int getCost(int from, int to)
   {
      return grid[from][to];
   }
   public int getCost(String from, String to)
   {
      int from2 = vertices.get(from);
      int to2 = vertices.get(to);
      return grid[from2][to2];
   }
   public void allPairsWeighted()
   {
      int size = grid.length;
      for(int v = 0; v < size; v++)
      {
         for(int i = 0; i < size; i++)
         {
            for(int j = 0; j < size; j++)
            {
               if(grid[i][v] + grid[v][j] < grid[i][j])
                  grid[i][j] = grid[i][v] + grid[v][j];
            }
         }
      }
   }
}
