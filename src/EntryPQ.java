/**
 * Created with IntelliJ IDEA.
 * User: nicksirock
 * Date: 11/27/13
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntryPQ<T> implements Comparable<EntryPQ>
    {
        public VertexInterface<T> vertex;
        public VertexInterface<T> previousVertex;
        public double cost; // cost to nextVertex

        public EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
        {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        } // end constructor

        public VertexInterface<T> getVertex()
        {
            return this.vertex;
        } // end getVertex

        public VertexInterface<T> getPredecessor()
        {
            return this.previousVertex;
        } // end getPredecessor

    public double getCost()
    {
        return this.cost;
    } // end getCost

    public int compareTo(EntryPQ otherEntry)
    {
        return (int) Math.signum(this.cost - otherEntry.cost);
    } // end compareTo

    public String toString()
    {
        return this.vertex.toString() + " " + this.cost;
    } // end toString
} // end EntryPQ
