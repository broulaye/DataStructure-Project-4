import java.util.LinkedList;

/**
 * This class represent a vertex in the graph
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 12/01/2016
 */
public class Vertex {
    private int value;
    private LinkedList<Integer> neighboor;
    private int parent;
    private int size;

    /**
     * constructor that creates a vertex with given value
     * @param newValue of the new vertex
     */
    public Vertex(int newValue) {
        value = newValue;
        neighboor = new LinkedList<>();
        parent = -1;
        size = 0;
    }


    /**
     * get the parent of the vertex
     * @return parent of the node
     */
    public int getParent() {
        return parent;
    }

    /**
     * set parent of vertex
     * @param parent new parent of vertex
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * get the value of the vertex
     * @return value of the vertex
     */
    public int getValue() {
        return value;
    }

    /**
     * set value of the vertex
     * @param value vertex new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * get neighboors of the vextex
     * @return vertex neibhboors
     */
    public LinkedList<Integer> getNeighboor() {
        return neighboor;
    }


    /**
     * set neighboor of the vertex
     * @param neighboor vertex new neighboor
     */
    public void setNeighboor(LinkedList<Integer> neighboor) {
        this.neighboor = neighboor;
    }

    /**
     * get the depth of the vertex
     * @return vertex depth
     */
    public int getSize() {
        return size;
    }

    /**
     * increment depth of the vertex
     */
    public void incrementSize() {
        size++;
    }

    /**
     * decrement depth of the vertex
     */
    public void decrementSize() {
        size--;
    }
}
