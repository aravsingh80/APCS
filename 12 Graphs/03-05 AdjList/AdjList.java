// Name: J1-24  
// Date: 5-19-2021
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs3: EdgeList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */

/* Graphs 3: EdgeList 
 */
interface VertexInterface
{
   String toString(); // Don't use commas in the list.  Example: "C [C D]"
   String getName();
   ArrayList<Vertex> getAdjacencies();
   void addAdjacent(Vertex v);
} 

class Vertex implements VertexInterface 
{
   private final String name;
   private ArrayList<Vertex> adjacencies;
  
  /* enter your code here  */
   public Vertex(String s)
   {
      name = s;
      adjacencies = new ArrayList<Vertex>();
   }
   public String toString()
   {
      String s = name + " [";
      for(int x = 0; x < adjacencies.size(); x++)
      {
         if(x == adjacencies.size() - 1)
            s += adjacencies.get(x).getName();
         else
            s += adjacencies.get(x).getName() + " ";
      }
      return s + "]";
   }
   public String getName()
   {
      return name;
   }
   public ArrayList<Vertex> getAdjacencies()
   {
      return adjacencies;
   }
   public void addAdjacent(Vertex v)
   {
      adjacencies.add(v);
   }
}   

interface AdjListInterface 
{ 
   List<Vertex> getVertices();
   Vertex getVertex(int i) ;
   Vertex getVertex(String vertexName);
   Map<String, Integer> getVertexMap();
   void addVertex(String v);
   void addEdge(String source, String target);
   String toString();  //returns all vertices with their edges (omit commas)
}

  
/* Graphs 4: DFS and BFS 
 */
interface DFS_BFS
{
   List<Vertex> depthFirstSearch(String name);
   List<Vertex> depthFirstSearch(Vertex v);
   List<Vertex> breadthFirstSearch(String name);
   List<Vertex> breadthFirstSearch(Vertex v);
   /*  three extra credit methods */
   List<Vertex> depthFirstRecur(String name);
   List<Vertex> depthFirstRecur(Vertex v);
   void depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable);
}

/* Graphs 5: Edgelist with Cities 
 */
interface EdgeListWithCities
{
   void graphFromEdgeListData(String fileName) throws FileNotFoundException;
   int edgeCount();
   int vertexCount(); //count the vertices in the object
   boolean isReachable(String source, String target);
   boolean isConnected();
}


public class AdjList implements AdjListInterface, DFS_BFS, EdgeListWithCities
{
   private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
   private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();
  
 /* enter your code here  */
   public List<Vertex> getVertices()
   {
      return vertices;
   }
   public Vertex getVertex(int i) 
   {
      return vertices.get(i);
   }
   public Vertex getVertex(String vertexName)
   {
      return vertices.get(nameToIndex.get(vertexName));
   }
   public Map<String, Integer> getVertexMap()
   {
      return nameToIndex;
   }
   public void addVertex(String v)
   {
      if(!(nameToIndex.containsKey(v)))
      {
         vertices.add(new Vertex(v));
         nameToIndex.put(v, nameToIndex.size());
      }
   }
   public void addEdge(String source, String target)
   {
      getVertex(source).addAdjacent(getVertex(target));
   }
   public String toString()
   {
      String s = "";
      for(int x = 0; x < vertices.size(); x++)
      {
         s += vertices.get(x).toString() + "\n";
      }
      return s;
   }
   public List<Vertex> depthFirstSearch(String name)
   {
      ArrayList<Vertex> a = new ArrayList<Vertex>();
      Stack<Vertex> temp = new Stack<Vertex>();
      temp.push(getVertex(name));
      a.add(temp.pop());
      ArrayList<Vertex> t = a.get(0).getAdjacencies();
      for(Vertex x : t)
         temp.push(x);
      while(!temp.isEmpty())
      {
         Vertex v = temp.pop();
         if(!a.contains(v))
         {
            a.add(v);
            ArrayList<Vertex> t2 = a.get(a.size() - 1).getAdjacencies();
            for(Vertex x : t2)
               temp.push(x);
         }
      }
      return a;
   }
   public List<Vertex> depthFirstSearch(Vertex v)
   {
      ArrayList<Vertex> a = new ArrayList<Vertex>();
      Stack<Vertex> temp = new Stack<Vertex>();
      temp.push(v);
      a.add(temp.pop());
      ArrayList<Vertex> t = a.get(0).getAdjacencies();
      for(Vertex x : t)
         temp.push(x);
      while(!temp.isEmpty())
      {
         Vertex v2 = temp.pop();
         if(!a.contains(v2))
         {
            a.add(v2);
            ArrayList<Vertex> t2 = a.get(a.size() - 1).getAdjacencies();
            for(Vertex x : t2)
               temp.push(x);
         }
      }
      return a;
   }
   public List<Vertex> breadthFirstSearch(String name)
   {
      ArrayList<Vertex> a = new ArrayList<Vertex>();
      Queue<Vertex> temp = new LinkedList<Vertex>();
      temp.add(getVertex(name));
      a.add(temp.remove());
      ArrayList<Vertex> t = a.get(0).getAdjacencies();
      for(Vertex x : t)
         temp.add(x);
      while(!temp.isEmpty())
      {
         Vertex v = temp.remove();
         if(!a.contains(v))
         {
            a.add(v);
            ArrayList<Vertex> t2 = a.get(a.size() - 1).getAdjacencies();
            for(Vertex x : t2)
               temp.add(x);
         }
      }
      return a;
   }
   public List<Vertex> breadthFirstSearch(Vertex v)
   {
      ArrayList<Vertex> a = new ArrayList<Vertex>();
      Queue<Vertex> temp = new LinkedList<Vertex>();
      temp.add(v);
      a.add(temp.remove());
      ArrayList<Vertex> t = a.get(0).getAdjacencies();
      for(Vertex x : t)
         temp.add(x);
      while(!temp.isEmpty())
      {
         Vertex v2 = temp.remove();
         if(!a.contains(v2))
         {
            a.add(v2);
            ArrayList<Vertex> t2 = a.get(a.size() - 1).getAdjacencies();
            for(Vertex x : t2)
               temp.add(x);
         }
      }
      return a;
   }
   
   public void graphFromEdgeListData(String fileName) throws FileNotFoundException
   {
      Scanner s = new Scanner(new File(fileName));
      while(s.hasNext())
      {
         String[] arr = s.nextLine().split(" ");
         addVertex(arr[0]);
         addVertex(arr[1]);
         addEdge(arr[0], arr[1]);
      }
   }
   
   public int edgeCount()
   {
      int count = 0;
      for(int x = 0; x < vertices.size(); x++)
      {
         count += vertices.get(x).getAdjacencies().size();
      }
      return count;
   }
   
   public int vertexCount()
   {
      return vertices.size();
   }
   
   public boolean isReachable(String source, String target)
   {
      List<Vertex> l = depthFirstSearch(source);
      for(int x = 0; x < l.size(); x++)
      {
         if(l.get(x).getName().equals(target))
            return true;
      }
      return false;
   }
   
   public boolean isConnected()
   {
      for(int x = 0; x < vertices.size(); x++)
      {
         for(int y = 0; y < vertices.size(); y++)
         {
            if(!isReachable(vertices.get(x).getName(), vertices.get(y).getName()))
               return false;
         }
      }
      return true;
   }
   
 /*  three extra credit methods, recursive version  */
   public List<Vertex> depthFirstRecur(String name)
   {
      return null;
   }
   
   public List<Vertex> depthFirstRecur(Vertex v)
   {
      return null;
   }
   
   public void depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable)
   {
      
   }   
}


