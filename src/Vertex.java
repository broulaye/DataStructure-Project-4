import java.util.LinkedList;

/**
 * Created by Broulaye on 11/28/2016.
 */
public class Vertex {
    private int value;
    private LinkedList<Integer> neighboor;

    public Vertex(int newValue) {
        value = newValue;
        neighboor = new LinkedList<>();
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LinkedList<Integer> getNeighboor() {
        return neighboor;
    }

    public void setNeighboor(LinkedList<Integer> neighboor) {
        this.neighboor = neighboor;
    }
}
