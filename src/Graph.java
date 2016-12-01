import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Broulaye on 11/28/2016.
 */
public class Graph {
    private ArrayList<Vertex> graph;
    private int numOfNode;
    private int numOfEdge;

    public Graph() {
        graph = new ArrayList<>();
        numOfEdge = 0;
        numOfNode = 0;
    }

    public int getNumOfNode() {
        return numOfNode;
    }

    public int getNumOfEdge() {
        return numOfEdge;
    }

    public void addNode(int vertex/*, int value*/) {
        for(int i=0; i<graph.size(); i++) {
            if(vertex == graph.get(i).getValue()) {
                return;
            }
        }

        Vertex v = new Vertex(vertex);
        //v.getNeighboor().add(value);
        graph.add(v);
        numOfNode++;
    }

    public void removeNode(int vertex) {
        for(int i=0; i<graph.size(); i++) {
            if(vertex == graph.get(i).getValue()) {
                graph.remove(i);
                numOfNode--;
                return;
            }
        }
    }

    public void addEdge(int dest, int value) {
        LinkedList<Integer> tempNeighbor;
        for(int i=0; i<graph.size(); i++) {
            if(dest == graph.get(i).getValue()) {
                tempNeighbor = graph.get(i).getNeighboor();
                if(tempNeighbor.contains(value)) {
                    return;
                }
                else {
                    tempNeighbor.add(value);
                }
                numOfEdge++;
                return;
            }
        }
    }

    public void removeEdge(int remove) {
        for(int i=0; i<graph.size(); i++) {
            if(graph.get(i).getNeighboor().contains(remove)) {
                graph.get(i).getNeighboor().removeFirstOccurrence(remove);
                numOfEdge--;
                return;
            }
        }
    }

    public LinkedList<Integer> getNeighboors(int value) {
        for(int i=0; i<graph.size(); i++) {
            if(graph.get(i).getValue() == value) {
                return graph.get(i).getNeighboor();
            }
        }
        return null;
    }

    public String printGraph() {
        return "";
    }

}
