import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: nicksirock
 * Date: 11/27/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class UndirectedGraph<T> extends DirectedGraph<T> implements GraphInterface<T>, java.io.Serializable {
    UndirectedGraph()
    {
        super();
    }
    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        if (super.addEdge(end,begin,edgeWeight))
            return super.addEdge(begin, end, edgeWeight);
        return false;
    }
    @Override
    public boolean addEdge(T begin, T end) {
        if (super.addEdge(end,begin,0))
            return super.addEdge(begin, end, 0);
        return false;
    }
    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges() / 2;
    }

    @Override
    public Stack<T> getTopologicalOrder() throws UnsupportedOperationException{
        return super.getTopologicalOrder();    //To change body of overridden methods use File | Settings | File Templates.
    }

}
