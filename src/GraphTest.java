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
        graph = new Graph();
        handle = new Handle(1);
    }

    /**
     * Test getNumOfNode method
     */
    public void testGetNumOfNode() {
        assertEquals(0, graph.getNumOfNode());
        graph.addNode(1, handle);
        assertEquals(1, graph.getNumOfNode());
    }



    /**
     * Test addNode method
     */
    public void testAddNode() {
        assertEquals(0, graph.getNumOfNode());
        graph.addNode(1, handle);
        assertEquals(1, graph.getNumOfNode());
        graph.addNode(1, handle);
        assertEquals(1, graph.getNumOfNode());
    }

    /**
     * Test removeNode method
     */
    public void testRemoveNode() {
        Handle handle3 = new Handle(2);
        assertEquals(0, graph.getNumOfNode());
        graph.addNode(1, handle);
        graph.addNode(2, handle3);
        graph.addEdge(0, 1);
        assertEquals(2, graph.getNumOfNode());
        graph.removeNode(0);
        assertEquals(1, graph.getNumOfNode());
        graph.removeNode(0);
        assertEquals(0, graph.getNumOfNode());

    }

    /**
     * Test addEdge method
     */
    public void testAddEdge() {

        Handle handle2 = new Handle(2);
        graph.addNode(1, handle);
        graph.addNode(2, handle2);

        assertEquals(0, graph.getVertex(0).getNeighboor().size());
        assertEquals(0, graph.getVertex(1).getNeighboor().size());
        graph.addEdge(0, 1);
        assertEquals(1, graph.getVertex(0).getNeighboor().size());
        assertEquals(1, graph.getVertex(1).getNeighboor().size());


    }



    /**
     * Test printGraph method
     */
    public void testPrintGraph() {
        assertEquals("", graph.printGraph());
    }

    /**
     * Test union method
     */
    public void testUnion() {
        Handle handle2 = new Handle(2);
        Handle handle3 = new Handle(3);
        graph.addNode(1, handle);
        graph.addNode(2, handle2);
        graph.addNode(3, handle3);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.union();
        graph.computeConnectedComponents();
        assertEquals(1, graph.getConnectedComponents());
        assertEquals(2, graph.getLargestSize());
    }
}
