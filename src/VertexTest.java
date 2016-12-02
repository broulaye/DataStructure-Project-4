import student.TestCase;

import java.util.LinkedList;

/**
 * This class test the vertex class
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 11/28/2016
 */

public class VertexTest extends TestCase{
    private Vertex vertex;

    /**
     * Set up our variable
     */
    public void setUp() {
        vertex = new Vertex(1);
    }

    /**
     * Test getValue method
     */
    public void testGetValue() {
        assertEquals(1, vertex.getValue());
    }

    /**
     * Test setValue method
     */
    public void testSetValue() {
        vertex.setValue(2);
        assertEquals(2, vertex.getValue());
    }

    /**
     * Test getNeighbor method
     */
    public void testGetNeighbor() {
        assertTrue(vertex.getNeighboor().isEmpty());
        vertex.getNeighboor().add(1);
        assertFalse(vertex.getNeighboor().isEmpty());
    }

    /**
     * Test setNeighbor method
     */
    public void testSetNeighbor() {
        LinkedList<Integer> newNeighboor = new LinkedList<>();
        vertex.setNeighboor(newNeighboor);
        assertEquals(newNeighboor, vertex.getNeighboor());
    }

    /**
     * Test getParent method
     */
    public void testGetParent() {
        assertEquals(-1, vertex.getParent());
    }

    /**
     * Test setParent method
     */
    public void testSetParent() {
        assertEquals(-1, vertex.getParent());
        vertex.setParent(1);
        assertEquals(1, vertex.getParent());
    }

    /**
     * Test getSize method
     */
    public void testGetSize() {
        assertEquals(0, vertex.getSize());
    }

    /**
     * Test incrementSize method
     */
    public void testIncrementSize() {
        assertEquals(0, vertex.getSize());
        vertex.incrementSize();
        assertEquals(1, vertex.getSize());
    }

    /**
     * Test decrementSize method
     */
    public void testDecrementSize() {
        assertEquals(0, vertex.getSize());
        vertex.incrementSize();
        assertEquals(1, vertex.getSize());
        vertex.decrementSize();
        assertEquals(0, vertex.getSize());
    }
}
