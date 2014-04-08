import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class that implements the ADT directed graph.
 *
 * @author Frank M. Carrano
 * @version 11/26/2013
 * @modifiedBy Anna Bieszczad
 */
public class DirectedGraph<T> implements GraphInterface<T>
{
    private Map<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph()
    {
        this.vertices = new TreeMap<T, VertexInterface<T>>();
        this.edgeCount = 0;
    } // end default constructor

    public boolean addVertex(T vertexLabel)
    {
        VertexInterface<T> isDuplicate = this.vertices.put(vertexLabel, new Vertex<T>(vertexLabel));
        return isDuplicate == null; // was add to dictionary successful?
    } // end addVertex

    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        boolean result = false;

        VertexInterface<T> beginVertex = this.vertices.get(begin);
        VertexInterface<T> endVertex = this.vertices.get(end);

        if ((beginVertex != null) && (endVertex != null))
            result = beginVertex.connect(endVertex, edgeWeight);

        if (result)
            this.edgeCount++;

        return result;
    } // end addEdge

    public boolean addEdge(T begin, T end)
    {
        return addEdge(begin, end, 0);
    } // end addEdge

    public boolean hasEdge(T begin, T end)
    {
        boolean found = false;

        VertexInterface<T> beginVertex = this.vertices.get(begin);
        VertexInterface<T> endVertex = this.vertices.get(end);

        if ((beginVertex != null) && (endVertex != null))
        {
            Iterator<VertexInterface<T>> neighbors =
                    beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if

        return found;
    } // end hasEdge

    public boolean isEmpty()
    {
        return this.vertices.isEmpty();
    } // end isEmpty

    public void clear()
    {
        this.vertices.clear();
        this.edgeCount = 0;
    } // end clear

    public int getNumberOfVertices()
    {
        return this.vertices.size();
    } // end getNumberOfVertices

    public int getNumberOfEdges()
    {
        return this.edgeCount;
    } // end getNumberOfEdges

    protected void resetVertices()
    {
        Collection<VertexInterface<T>> values = this.vertices.values();
        Iterator<VertexInterface<T>> vertexIterator = values.iterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    public Queue<T> getBreadthFirstTraversal(T origin)
    {
        resetVertices();
        Queue<T> traversalOrder = new LinkedBlockingQueue<T>();
        Queue<VertexInterface<T>> vertexQueue =
                new LinkedBlockingQueue<VertexInterface<T>>();
        VertexInterface<T> originVertex = this.vertices.get(origin);
        originVertex.visit();
        traversalOrder.offer(origin);    // enqueue vertex label
        vertexQueue.offer(originVertex); // enqueue vertex

        while (!vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.poll();

            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    traversalOrder.offer(nextNeighbor.getLabel());
                    vertexQueue.offer(nextNeighbor);
                } // end if
            } // end while
        } // end while

        return traversalOrder;
    } // end getBreadthFirstTraversal

    public Queue<T> getDepthFirstTraversal(T origin)     // STUDENT EXERCISE
    {
        Queue<T> traversalOrder = new LinkedBlockingQueue<T>();
        Stack<VertexInterface<T>> vertexStack = new Stack<VertexInterface<T>>();
        VertexInterface<T> originVertex = this.vertices.get(origin);
        originVertex.visit();
        traversalOrder.offer(origin);
        vertexStack.push(originVertex);
        boolean check;
        while (!vertexStack.empty())
        {
            VertexInterface<T> topVertex = vertexStack.peek();
            Iterator<VertexInterface<T>> neighbors = topVertex.getNeighborIterator();
            check = false;
            while (neighbors.hasNext() && !check)
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited())
                {
                    check = true;
                    nextNeighbor.visit();
                    traversalOrder.offer(nextNeighbor.getLabel());
                    vertexStack.push(nextNeighbor);
                }

            }
            if (!check)
                vertexStack.pop();
        }

        return traversalOrder;
    } // end getDepthFirstTraversal

    public Stack<T> getTopologicalOrder()
    {
        resetVertices();

        Stack<T> vertexStack = new Stack<T>();
        int numberOfVertices = getNumberOfVertices();
        for (int counter = 1; counter <= numberOfVertices; counter++)
        {
            VertexInterface<T> nextVertex = findTerminal();
            nextVertex.visit();
            vertexStack.push(nextVertex.getLabel());
        } // end for

        return vertexStack;
    } // end getTopologicalOrder

    /**
     * Precondition: path is an empty stack (NOT null)
     */
    public int getShortestPath(T begin, T end, Stack<T> path)
    {
        resetVertices();
        boolean done = false;
        Queue<VertexInterface<T>> vertexQueue =
                new LinkedBlockingQueue<VertexInterface<T>>();
        VertexInterface<T> originVertex = this.vertices.get(begin);
        VertexInterface<T> endVertex = this.vertices.get(end);

        originVertex.visit();
        // Assertion: resetVertices() has executed setCost(0)
        // and setPredecessor(null) for originVertex

        vertexQueue.offer(originVertex);

        while (!done && !vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.poll();

            Iterator<VertexInterface<T>> neighbors =
                    frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();

                if (!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.offer(nextNeighbor);
                } // end if

                if (nextNeighbor.equals(endVertex))
                    done = true;
            } // end while
        } // end while

        // traversal ends; construct shortest path
        int pathLength = (int) endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while

        return pathLength;
    } // end getShortestPath

    /**
     * Precondition: path is an empty stack (NOT null)
     */
    public double getCheapestPath(T begin, T end, Stack<T> path) // STUDENT EXERCISE
    {
        resetVertices();
        boolean done = false;

        // use EntryPQ instead of Lab14.Vertex because multiple entries contain
        // the same vertex but different costs - cost of path to vertex is EntryPQ's priority value
        resetVertices();
        PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<EntryPQ>();

        VertexInterface<T> originVertex = this.vertices.get(begin);
        VertexInterface<T> endVertex = this.vertices.get(end);

        priorityQueue.add(new EntryPQ(originVertex, 0, null));
        while (!done && !priorityQueue.isEmpty())
        {
            EntryPQ frontEntry = priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.vertex;
            if (!frontVertex.isVisited())
            {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost());
                frontVertex.setPredecessor(frontEntry.getPredecessor());
                if (frontVertex.equals(frontEntry))
                {
                    done = true;
                }
                else
                {
                    Iterator<VertexInterface<T>> neighbors =
                            frontVertex.getNeighborIterator();
                    Iterator<Double> weightNeighbors =
                            frontVertex.getWeightIterator();
                    while (neighbors.hasNext())
                    {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        double weight = weightNeighbors.next();
                        if (!nextNeighbor.isVisited())
                        {
                            double nextCost = weight + frontVertex.getCost();
                            priorityQueue.add(new EntryPQ(nextNeighbor,nextCost,frontVertex));
                        } // end if

                    }
                }
            }
        }
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathCost;
    } // end getCheapestPath

    protected VertexInterface<T> findTerminal()
    {
        boolean found = false;
        VertexInterface<T> result = null;
        Collection<VertexInterface<T>> values = this.vertices.values();
        Iterator<VertexInterface<T>> vertexIterator = values.iterator();

        while (!found && vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();

            // if nextVertex is unvisited AND has only visited neighbors)
            if (!nextVertex.isVisited())
            {
                if (nextVertex.getUnvisitedNeighbor() == null)
                {
                    found = true;
                    result = nextVertex;
                } // end if
            } // end if
        } // end while

        return result;
    } // end findTerminal

    // Used for testing
    public void display()
    {
        System.out.println("Graph has " + getNumberOfVertices() + " vertices and " +
                getNumberOfEdges() + " edges.");

        System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
        System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
        Collection<VertexInterface<T>> values = this.vertices.values();
        Iterator<VertexInterface<T>> vertexIterator = values.iterator();
        while (vertexIterator.hasNext())
        {
            ((Vertex<T>) (vertexIterator.next())).display();
        } // end while
    } // end display
    public int[][] getAdjacencyMatrix()
    {
        int[][] result = new int[this.vertices.size()][this.vertices.size()];
        Collection<VertexInterface<T>> values = this.vertices.values();
        Iterator<VertexInterface<T>> vertexIterator = values.iterator();
        for (int i = 0; i < result.length; i++)
        {
         VertexInterface<T> vertex = vertexIterator.next();
            Iterator<VertexInterface<T>> otherIterator = values.iterator();
         for (int j = 0; j < result.length; j++)
         {
           VertexInterface<T> other = otherIterator.next();
           if (hasEdge(vertex.getLabel(),other.getLabel()))
             {
                 result[i][j] = 1;
             }
           else
             {
                 result[i][j] = 0;
             }
         }
        }
        return result;
    }
    public  ArrayList<VertexInterface<T>> getMapping()
    {

        ArrayList<VertexInterface<T>> result = new ArrayList<VertexInterface<T>>();
        Collection<VertexInterface<T>> values = this.vertices.values();
        Iterator<VertexInterface<T>> vertexIterator = values.iterator();
        for (int i = 0; i < this.vertices.size(); i++)
        {
         result.add(vertexIterator.next());
        }

        return result;
    }
    }

