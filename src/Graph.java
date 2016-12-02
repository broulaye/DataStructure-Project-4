import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent a graph
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 12/01/2016
 */
public class Graph {
    private ArrayList<Vertex> graph;
    private int numOfNode;
    private int connectedComponents;
    private int largestSize;
    private int unionArray[];

    /**
     * Default constructor that initialized the variables
     */
    public Graph() {
        graph = new ArrayList<>();
        numOfNode = 0;
        connectedComponents = 0;
        largestSize = 0;
    }

    /**
     * Get number of vertex presently in the graph
     * @return numberOfVertex in the graph
     */
    public int getNumOfNode() {
        return numOfNode;
    }


    /**
     * Add new vertex to the graph
     * @param vertex new value of new vertex
     * @param handle handle to be inserted
     */
    public void addNode(int vertex, Handle handle) {
        if(handle.getVertex() != -1) {
            return;
        }

        Vertex v = new Vertex(vertex);
        //v.getNeighboor().add(value);
        graph.add(numOfNode, v);
        handle.setVertex(numOfNode);
        numOfNode++;
    }


    /**
     * remove a vertex from the graph
     * @param vertex to be removed
     */
    public void removeNode(int vertex) {
        LinkedList<Integer> neighboor = graph.get(vertex).getNeighboor();

        Iterator<Integer> iterator = neighboor.iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            //Vertex currentVertex = graph.get(index);
            graph.get(index).getNeighboor().removeFirstOccurrence(vertex);
        }
        graph.remove(vertex);
        numOfNode--;
    }

    /**
     * Add new edge to the graph
     * @param dest1 first node
     * @param dest2 second node
     */
    public void addEdge(int dest1, int dest2) {
        graph.get(dest1).getNeighboor().add(dest2);
        graph.get(dest2).getNeighboor().add(dest1);
    }


    /**
     * get the vertex at the given index
     * @param index of vertex to return
     * @return the vertex at the given index
     */
    public Vertex getVertex(int index) {

        return graph.get(index);
    }

    /**
     * Union algorithm to merge related vertex
     */
    public void union() {
        unionArray = new int[numOfNode];
        for (int i=0; i<numOfNode; i++) {
            unionArray[i] = -1;
        }

        for(int i = 0; i<numOfNode; i++) {
            int root = i;
            LinkedList<Integer> neigh = graph.get(root).getNeighboor();
            Iterator<Integer> iterator = neigh.iterator();
            while (iterator.hasNext()) {
                int root1 = Find(iterator.next());
                if(root1 != unionArray[root]) {
                    unionArray[root1] = root;
                    graph.get(root).incrementSize();
                }

            }

        }
    }

    /**
     * Find and return the root of the given vertex
     * @param curr represent given vertex
     * @return root of curr
     */
    private int Find(int curr) {
        if (unionArray[curr] == -1) {
            return curr; // At root
        }
        while (unionArray[curr] != -1)  {
            curr = unionArray[curr];
        }
        return curr;
    }

    /**
     * get the number of connected components
     * @return number of connected components
     */
    public int getConnectedComponents() {
        return connectedComponents;
    }

    /**
     * Compute the number of connected components
     */
    public void computeConnectedComponents() {
        largestSize = 0;
        int tempSize;
        for(int i = 0; i<numOfNode; i++) {
            if(unionArray[i] == -1) {
                connectedComponents++;
                tempSize = graph.get(i).getSize();
                if(tempSize > largestSize) {
                    largestSize = tempSize;
                }
            }
        }

    }

    public int getLargestSize() {
        return largestSize;
    }



    /**
     * Print a string representation of the graph
     * @return
     */
    public String printGraph() {
        return "";
    }

}
