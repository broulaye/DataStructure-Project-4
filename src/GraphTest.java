import student.TestCase;

/**
 * This class test the graph class
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 11/28/2016
 */
public class GraphTest extends TestCase {
    private Graph graph;
    private Handle handle;

    /**
     * Set up our variable
     */
    public void setUp() {
        graph = new Graph(10);
        handle = new Handle(1);
    }

    /**
     * Test getNumOfNode method
     */
    public void testGetNumOfNode() {
        assertEquals(0, graph.getNumOfNode());
        handle.setVertex(graph.addNode(-1));
        assertEquals(1, graph.getNumOfNode());
    }

    /**
     * Test addNode method
     */
    public void testAddNode() {
        assertEquals(0, graph.getNumOfNode());
        handle.setVertex(graph.addNode(-1));
        assertEquals(1, graph.getNumOfNode());
        handle.setVertex(graph.addNode(handle.getVertex()));
        assertEquals(1, graph.getNumOfNode());
    }

    /**
     * Test removeNode method
     */
    public void testRemoveNode() {
        Handle handle3 = new Handle(2);
        assertEquals(0, graph.getNumOfNode());
        handle.setVertex(graph.addNode(-1));
        handle3.setVertex(graph.addNode(-1));
        graph.addEdge(0, 1);
        assertEquals(2, graph.getNumOfNode());
        graph.removeNode(0);
        assertEquals(1, graph.getNumOfNode());
        graph.removeNode(1);
        assertEquals(0, graph.getNumOfNode());

    }

    /**
     * Test removing an edge
     */
    public void testRemoveEdge() {
        Handle handle2 = new Handle(1234);
        Handle handle3 = new Handle(2);
        handle2.setVertex(graph.addNode(-1));
        handle3.setVertex(graph.addNode(-1));
        graph.addEdge(0, 1);
        assertEquals(1, graph.getVertex(1).getNeighboor().size());
        graph.removeNode(0);
        assertEquals(0, graph.getVertex(1).getNeighboor().size());
    }

    /**
     * Test addEdge method
     */
    public void testAddEdge() {

        Handle handle2 = new Handle(2);
        handle.setVertex(graph.addNode(-1));
        handle2.setVertex(graph.addNode(-1));

        assertEquals(0, graph.getVertex(0).getNeighboor().size());
        assertEquals(0, graph.getVertex(1).getNeighboor().size());
        graph.addEdge(0, 1);
        assertEquals(1, graph.getVertex(0).getNeighboor().size());
        assertEquals(1, graph.getVertex(1).getNeighboor().size());

    }

    /**
     * Test expanding the graph
     */
    public void testExpandingGraph() {
        Handle handle2;
        for (int i = 0; i < 10; i++) {
            handle2 = new Handle(i);
            handle2.setVertex(graph.addNode(-1));
        }

        assertEquals(10, graph.getNumOfNode());
        handle2 = new Handle(10);
        handle2.setVertex(graph.addNode(-1));
        assertEquals(11, graph.getNumOfNode());
        assertEquals(10, graph.getVertex(10).getValue());
    }

    /**
     * Test printGraph method
     */
    public void testPrintGraph() {
        String output = "There are " + 0 + " connected components\n"
                + "The largest connected component has " + 0 + " elements\n"
                + "The diameter of the largest component is " + 0;
        assertEquals(output, graph.printGraph());
    }

    /**
     * Test union method
     */
    public void testUnion() {
        Handle handle2 = new Handle(2);
        Handle handle3 = new Handle(3);
        handle.setVertex(graph.addNode(-1));
        handle2.setVertex(graph.addNode(-1));
        handle3.setVertex(graph.addNode(-1));
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.computeUnion();
        graph.computeConnectedComponents();
        assertEquals(1, graph.getConnectedComponents());
        assertEquals(3, graph.getLargestSize());
    }
}
