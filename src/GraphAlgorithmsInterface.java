import java.util.Queue;import java.util.Stack;/** * An interface of methods that process an existing graph. * * @author Frank M. Carrano, Charles Hoot * @version 11/26/2013 */public interface GraphAlgorithmsInterface<T>{    /**     * Performs a breadth-first traversal of a graph.     *     * @param origin an object that labels the origin vertex of the     *               traversal     * @return a queue of labels of the vertices in the traversal, with     *         the label of the origin vertex at the queue's front     */    public Queue<T> getBreadthFirstTraversal(T origin);    /**     * Performs a depth-first traversal of a graph.     *     * @param origin an object that labels the origin vertex of the     *               traversal     * @return a queue of labels of the vertices in the traversal, with     *         the label of the origin vertex at the queue's front     */    public Queue<T> getDepthFirstTraversal(T origin);    /**     * Performs a topological sort of the vertices in a graph     * without cycles.     *     * @return a stack of vertex labels in topological order, beginning     *         with the stack's top     */    public Stack<T> getTopologicalOrder();    /**     * Finds the path between two given vertices that has the     * shortest length.     *     * @param begin an object that labels the path's origin vertex     * @param end   an object that labels the path's destination vertex     * @param path  a stack of labels that is empty initially;     *              at the completion of the method, this stack contains     *              the labels of the vertices along the shortest path;     *              the label of the origin vertex is at the top, and     *              the label of the destination vertex is at the bottom     * @return the length of the shortest path     */    public int getShortestPath(T begin, T end, Stack<T> path);    /**     * Finds the least-cost path between two given vertices.     *     * @param begin an object that labels the path's origin vertex     * @param end   an object that labels the path's destination vertex     * @param path  a stack of labels that is empty initially;     *              at the completion of the method, this stack contains     *              the labels of the vertices along the cheapest path;     *              the label of the origin vertex is at the top, and     *              the label of the destination vertex is at the bottom     * @return the cost of the cheapest path     */    public double getCheapestPath(T begin, T end, Stack<T> path);}