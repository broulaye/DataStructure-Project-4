
/**
 * This class represent a vertex in the graph
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 12/01/2016
 */
public class Vertex {
    private int value;
    private DLLinkedList<Integer> neighboor;

    /**
     * constructor that creates a vertex with given value
     * 
     * @param newValue
     *            of the new vertex
     */
    public Vertex(int newValue) {
        value = newValue;
        neighboor = new DLLinkedList<>();
    }

    /**
     * get the value of the vertex
     * 
     * @return value of the vertex
     */
    public int getValue() {
        return value;
    }

    /**
     * set value of the vertex
     * 
     * @param value
     *            vertex new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * get neighboors of the vextex
     * 
     * @return vertex neibhboors
     */
    public DLLinkedList<Integer> getNeighboor() {
        return neighboor;
    }

    /**
     * set neighboor of the vertex
     * 
     * @param neighboor
     *            vertex new neighboor
     */
    public void setNeighboor(DLLinkedList<Integer> neighboor) {
        this.neighboor = neighboor;
    }

}
