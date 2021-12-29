// Name: J1-24
// Date: 5-27-2021
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs6: Dijkstra
 *              Graphs7: Dijkstra with Cities
 */

class Edge 
{
   public final wVertex target;  //if it's public, you don't need accessor methods
   public final double weight;   //if it's public, you don't need accessor methods
  
   public Edge(wVertex argTarget, double argWeight) 
   {
      target = argTarget;
      weight = argWeight;
   }
}

interface wVertexInterface 
{
   String getName();
   double getMinDistance();
   void setMinDistance(double m);
   wVertex getPrevious();   //for Dijkstra 7
   void setPrevious(wVertex v);  //for Dijkstra 7
   ArrayList<Edge> getAdjacencies();
   void addEdge(wVertex v, double weight);   
   int compareTo(wVertex other);
}

class wVertex implements Comparable<wVertex>, wVertexInterface
{
   private final String name;
   private ArrayList<Edge> adjacencies;
   private double minDistance = Double.POSITIVE_INFINITY;
   private wVertex previous;  //for building the actual path in Dijkstra 7
   
   /*  enter your code for this class here   */ 
   public wVertex(String argName)
   {
      name = argName;
      adjacencies = new ArrayList<Edge>();
      previous = null;
   }
   public String getName()
   {
      return name;
   }
   public double getMinDistance()
   {
      return minDistance;
   }
   public void setMinDistance(double m)
   {
      minDistance = m;
   }
   public ArrayList<Edge> getAdjacencies()
   {
      return adjacencies;
   }
   public void addEdge(wVertex v, double weight) 
   {
      adjacencies.add(new Edge(v, weight));
   }
   public int compareTo(wVertex other) 
   {
      return (int)(minDistance - other.minDistance);
   }
   public wVertex getPrevious()
   {
      return previous;
   }
   public void setPrevious(wVertex v)
   {
      previous = v;
   }
}

interface AdjListWeightedInterface 
{
   List<wVertex> getVertices();
   Map<String, Integer> getNameToIndex();
   wVertex getVertex(int i);   
   wVertex getVertex(String vertexName);
   void addVertex(String v);
   void addEdge(String source, String target, double weight);
   void minimumWeightPath(String vertexName);   //Dijkstra's
   void minimumWeightPathWithSet(String vertexName); //Extension
}

/* Interface for Graphs 7:  Dijkstra with Cities 
 */

interface AdjListWeightedInterfaceWithCities 
{       
   List<String> getShortestPathTo(wVertex v);
   AdjListWeighted graphFromEdgeListData(File vertexNames, File edgeListData) throws FileNotFoundException ;
   List<String> getShortestPathToWithSet(wVertex v);  //Extension
}
 

public class AdjListWeighted implements AdjListWeightedInterface, AdjListWeightedInterfaceWithCities
{
   private List<wVertex> vertices = new ArrayList<wVertex>();
   private Map<String, Integer> nameToIndex = new HashMap<String, Integer>();
   //the constructor is a no-arg constructor 
   public AdjListWeighted()
   {
   }
   /*  enter your code for Graphs 6 */ 
   public List<wVertex> getVertices()
   {
      return vertices;
   }
   public Map<String, Integer> getNameToIndex()
   {
      return nameToIndex;
   }
   public wVertex getVertex(int i)
   {
      return vertices.get(i);
   }
   public wVertex getVertex(String vertexName)
   {
      return vertices.get(nameToIndex.get(vertexName));
   }
   public void addVertex(String v)
   {
      if(!(nameToIndex.containsKey(v)))
      {
         vertices.add(new wVertex(v));
         nameToIndex.put(v, nameToIndex.size());
      }
   }
   public void addEdge(String source, String target, double weight)
   {
      getVertex(source).addEdge(getVertex(target), weight);
   }
   public void minimumWeightPath(String vertexName)
   {
      PriorityQueue<wVertex> pq = new PriorityQueue<wVertex>();
      for(int x = 0; x < vertices.size(); x++)
      {
         if(vertices.get(x).getName().equals(vertexName))
            vertices.get(x).setMinDistance(0);
         else
            vertices.get(x).setMinDistance(Double.POSITIVE_INFINITY);
      }
      pq.add(getVertex(vertexName));
      while(!pq.isEmpty())
      {
         wVertex current = pq.remove();
         ArrayList<Edge> edgeList = current.getAdjacencies();
         for(int x = 0; x < edgeList.size(); x++)
         {
            if(current.getMinDistance() + edgeList.get(x).weight < edgeList.get(x).target.getMinDistance())
            {
               edgeList.get(x).target.setMinDistance(current.getMinDistance() + edgeList.get(x).weight);
               pq.add(edgeList.get(x).target);
               edgeList.get(x).target.setPrevious(current);
            }
         }
      }
   }
   
   
   
   /*  enter your code for two new methods in Graphs 7 */
   public List<String> getShortestPathTo(wVertex v)
   {
      ArrayList<String> l = new ArrayList<String>();
      PriorityQueue<wVertex> pq = new PriorityQueue<wVertex>();
      wVertex temp = v;
      while(temp.getPrevious() != null)
      {
         pq.add(temp);
         temp = temp.getPrevious();
      }
      pq.add(temp);
      while(!(pq.isEmpty()))
      {
         l.add(pq.remove().getName());
      }
      return l;
   }
   public AdjListWeighted graphFromEdgeListData(File vertexNames, File edgeListData) throws FileNotFoundException 
   {
      Scanner s = new Scanner(vertexNames);
      AdjListWeighted a = new AdjListWeighted();
      s.nextLine();
      while(s.hasNext())
      {
         a.addVertex(s.nextLine());
      }
      s = new Scanner(edgeListData);
      while(s.hasNext())
      {
         String[] arr = s.nextLine().split(" ");
         a.addEdge(arr[0], arr[1], Double.parseDouble(arr[2]));
      }
      return a;
   }
   
   /*Extension*/
   public void minimumWeightPathWithSet(String vertexName)
   {
      Set<wVertex> s = new TreeSet<wVertex>();
      for(int x = 0; x < vertices.size(); x++)
      {
         if(vertices.get(x).getName().equals(vertexName))
            vertices.get(x).setMinDistance(0);
         else
            vertices.get(x).setMinDistance(Double.POSITIVE_INFINITY);
      }
      s.add(getVertex(vertexName));
      while(s.size() > 0)
      {
         wVertex current = null;
         for(wVertex c : s)
         {
            current = c;
            break;
         }
         s.remove(current);
         ArrayList<Edge> edgeList = current.getAdjacencies();
         for(int x = 0; x < edgeList.size(); x++)
         {
            if(current.getMinDistance() + edgeList.get(x).weight < edgeList.get(x).target.getMinDistance())
            {
               edgeList.get(x).target.setMinDistance(current.getMinDistance() + edgeList.get(x).weight);
               s.add(edgeList.get(x).target);
               edgeList.get(x).target.setPrevious(current);
            }
         }
      }
   }

   public List<String> getShortestPathToWithSet(wVertex v)
   {
      ArrayList<String> l = new ArrayList<String>();
      Set<wVertex> s = new TreeSet<wVertex>();
      wVertex temp = v;
      while(temp.getPrevious() != null)
      {
         s.add(temp);
         temp = temp.getPrevious();
      }
      s.add(temp);
      for(wVertex c : s)
      {
         l.add(c.getName());
      }
      return l;
   }

}   


