import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: nicksirock
 * Date: 11/27/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class NeighborIterator<T> implements Iterator<VertexInterface<T>>{

    private Iterator<Edge> edges;
    public NeighborIterator(List<Edge> edgeList)
    {
        this.edges = edgeList.iterator();
    } // end default constructor

    public boolean hasNext()
    {
        return this.edges.hasNext();
    } // end hasNext

    public VertexInterface<T> next()
    {
        VertexInterface<T> nextNeighbor = null;

        if (this.edges.hasNext())
        {
            Edge edgeToNextNeighbor = this.edges.next();
            nextNeighbor = edgeToNextNeighbor.getEndVertex();
        } else
            throw new NoSuchElementException();

        return nextNeighbor;
    } // end next

    public void remove()
    {
        throw new UnsupportedOperationException();
    } // end remove
}
