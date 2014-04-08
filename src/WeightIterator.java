import java.util.Iterator;
import java.util.NoSuchElementException;
               import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: nicksirock
 * Date: 11/27/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeightIterator implements Iterator<Double>
{
    private Iterator<Edge> edges;

    public WeightIterator(List<Edge> edgeList)
    {
        this.edges = edgeList.iterator();
    } // end default constructor

    public boolean hasNext()
    {
        return this.edges.hasNext();
    } // end hasNext

    public Double next()
    {
        Double edgeWeight = new Double(0);

        if (this.edges.hasNext())
        {
            Edge edgeToNextNeighbor = this.edges.next();
            edgeWeight = edgeToNextNeighbor.getWeight();
        } else
            throw new NoSuchElementException();

        return edgeWeight;
    } // end next

    public void remove()
    {
        throw new UnsupportedOperationException();
    } // end remove
} // end WeightIterator