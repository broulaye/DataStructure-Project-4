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

    /**
     * Set up our variable
     */
    public void setUp() {
        graph = new Graph();
    }

    /**
     * Test getNumOfNode method
     */
    public void testGetNumOfNode() {
        assertEquals(0, graph.getNumOfNode());
        graph.addNode(1);
        assertEquals(1, graph.getNumOfNode());
    }

    /**
     * Test getNumOfEdge method
     */
    public void testGetNumOfEdge() {
        assertEquals(0, graph.getNumOfEdge());
        graph.addNode(1);
        graph.addEdge(1, 1);
        assertEquals(1, graph.getNumOfEdge());
    }

    /**
     * Test addNode method
     */
    public void testAddNode() {
        assertEquals(0, graph.getNumOfNode());
        graph.addNode(1);
        assertEquals(1, graph.getNumOfNode());
        graph.addNode(1);
        assertEquals(1, graph.getNumOfNode());
    }

    /**
     * Test removeNode method
     */
    public void testRemoveNode() {

        assertEquals(0, graph.getNumOfNode());
        graph.removeNode(1);
        assertEquals(0, graph.getNumOfNode());
        graph.addNode(1);
        assertEquals(1, graph.getNumOfNode());
        graph.removeNode(1);
        assertEquals(0, graph.getNumOfNode());

    }

    /**
     * Test addEdge method
     */
    public void testAddEdge() {
        assertEquals(0, graph.getNumOfEdge());
        graph.addNode(1);
        graph.addEdge(1, 1);
        assertEquals(1, graph.getNumOfEdge());
        graph.addEdge(1, 1);
        assertEquals(1, graph.getNumOfEdge());
        graph.addEdge(2, 1);
        assertEquals(1, graph.getNumOfEdge());
        graph.addNode(2);
        graph.addEdge(2, 1);
        assertEquals(2, graph.getNumOfEdge());

    }

    /**
     * Test removeEdge method
     */
    public void testRemoveEdge() {
        assertEquals(0, graph.getNumOfEdge());
        graph.addNode(1);
        graph.addEdge(1, 1);
        assertEquals(1, graph.getNumOfEdge());
        graph.removeEdge(2);
        assertEquals(1, graph.getNumOfEdge());
        graph.removeEdge(1);
        assertEquals(0, graph.getNumOfEdge());
        graph.addEdge(1, 2);
        assertEquals(1, graph.getNumOfEdge());

    }

    /**
     * Test getNeighboors method
     */
    public void testGetNeighboors() {
        assertNull(graph.getNeighboors(1));
        graph.addNode(1);
        graph.addEdge(1, 1);
        assertNotNull(graph.getNeighboors(1));
        assertNull(graph.getNeighboors(2));


    }

    /**
     * Test printGraph method
     */
    public void testPrintGraph() {
        assertEquals("", graph.printGraph());
    }
}
