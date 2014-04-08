/**
 * Created with IntelliJ IDEA.
 * User: nicksirock
 * Date: 11/27/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge<T>
    {
        private VertexInterface<T> vertex; // end vertex
        private double weight;

        public Edge(VertexInterface<T> endVertex, double edgeWeight)
        {
            this.vertex = endVertex;
            this.weight = edgeWeight;
        } // end constructor

        public VertexInterface<T> getEndVertex()
        {
            return this.vertex;
        } // end getEndVertex

        public double getWeight()
        {
            return this.weight;
        } // end getWeight

        public String toString() // for testing only
        {
            return this.vertex.toString() + " " + this.weight;
        } // end toString
    } // end Edge

