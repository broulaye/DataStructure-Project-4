import java.util.Iterator;

/**
 * This class represent a graphArray
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 12/01/2016
 */
public class Graph {
    private Vertex[] graphArray;
    private DLLinkedList<Integer> positionStack;
    private int numOfNode;
    private int connectedComponents;
    private int largestSize;
    private int largestIndex;
    private int diameter;
    private int[] unionArray;
    private int[] unionWeight;
    private int currentPosition;
    private int expandBy;
    private int[] values;

    /**
     * Default constructor that initialized the variables
     * 
     * @param size
     *            initial size of graph
     */
    public Graph(int size) {
        graphArray = new Vertex[size];
        expandBy = size;
        numOfNode = 0;
        connectedComponents = 0;
        largestSize = 0;
        diameter = 0;
        largestIndex = 0;
        currentPosition = 0;
        positionStack = new DLLinkedList<>();
    }

    /**
     * Get number of vertex presently in the graphArray
     * 
     * @return numberOfVertex in the graphArray
     */
    public int getNumOfNode() {
        return numOfNode;
    }

    /**
     * Add new vertex to the graphArray
     * 
     * @param vertex
     *            new value of new vertex
     * @return vertex position of node
     */
    public int addNode(int vertex) {
        if (vertex != -1) {
            return vertex;
        }
        int pos;
        Vertex v = new Vertex(vertex);

        if (currentPosition >= graphArray.length) {
            if (!positionStack.isEmpty()) {
                pos = positionStack.get(0);
                positionStack.remove(0);
                graphArray[pos] = v;
                v.setValue(pos);
                numOfNode++;
                return pos;
            }
            else {
                expandGraph();
                graphArray[currentPosition] = v;
                v.setValue(currentPosition);
                currentPosition++;
                numOfNode++;
                return v.getValue();
            }
        }
        else {
            graphArray[currentPosition] = v;
            v.setValue(currentPosition);
            currentPosition++;
            numOfNode++;
            return v.getValue();
        }

    }

    /**
     * this method double the size of the graphArray
     */
    private void expandGraph() {
        int newLenght = graphArray.length + expandBy;
        Vertex[] newGraph = new Vertex[newLenght];
        System.arraycopy(graphArray, 0, newGraph, 0, graphArray.length);
        graphArray = newGraph;
    }

    /**
     * remove a vertex from the graphArray
     * 
     * @param vertex
     *            to be removed
     */
    public void removeNode(int vertex) {
        DLLinkedList<Integer> neighboor = graphArray[vertex].getNeighboor();
        Iterator<Integer> iterator = neighboor.iterator();
        while (iterator.hasNext()) {
            graphArray[iterator.next()].getNeighboor().removeValue(vertex);
        }
        graphArray[vertex] = null;
        positionStack.add(vertex);
        numOfNode--;
    }

    /**
     * Add new edge to the graphArray
     * 
     * @param dest1
     *            first node
     * @param dest2
     *            second node
     */
    public void addEdge(int dest1, int dest2) {
        graphArray[dest1].getNeighboor().add(dest2);
        graphArray[dest2].getNeighboor().add(dest1);
    }

    /**
     * private method that compute the tree of the vertex with the most element
     */
    private void computeTree() {
        DLLinkedList roots = new DLLinkedList();
        if (largestSize == 0) {
            return;
        }
        values = new int[largestSize];
        values[0] = largestIndex;
        int k = 0;
        for (int i = 1; i < largestSize; i++) {
            if (unionArray[k] != largestIndex && !roots.contains(unionArray[k])) {
                k++;
                i--;
                continue;

            }
            values[i] = k;
            roots.add(k);
            k++;
        }

    }

    /**
     * get the vertex at the given index
     * 
     * @param index
     *            of vertex to return
     * @return the vertex at the given index
     */
    public Vertex getVertex(int index) {

        return graphArray[index];
    }

    /**
     * Union algorithm to merge related vertex
     * 
     * @param a
     *            represent first vertex
     * @param b
     *            represent second vertex
     */
    private void union(int a, int b) {
        int root1 = find(a);
        int root2 = find(b);
        if (root1 != root2) {
            if (unionWeight[root2] > unionWeight[root1]) {
                unionArray[root1] = root2;
                unionWeight[root2] += unionWeight[root1];
            }
            else {
                unionArray[root2] = root1;
                unionWeight[root1] += unionWeight[root2];
            }

        }

    }

    /**
     * Compute the union/find algorithm on the graphArray
     */
    public void computeUnion() {
        unionArray = new int[graphArray.length];
        unionWeight = new int[graphArray.length];
        for (int i = 0; i < graphArray.length; i++) {
            if (graphArray[i] == null) {
                unionArray[i] = -2;
                unionWeight[i] = -1;
            }
            else {
                unionArray[i] = -1;
                unionWeight[i] = 1;
            }

        }

        int k = 0;
        for (int i = 0; i < numOfNode; i++) {
            if (graphArray[k] == null) {
                i--;
                k++;
                continue;
            }

            DLLinkedList<Integer> neigh = graphArray[k].getNeighboor();
            Iterator<Integer> iterator = neigh.iterator();
            while (iterator.hasNext()) {
                int index = iterator.next();
                union(k, index);
            }
            k++;

        }
    }

    /**
     * Compute Floyd's algorithm
     */
    private void computeFloyd() {
        diameter = 0;
        if (largestSize == 0) {
            return;
        }
        int[][] distanceMatrix = new int[largestSize][largestSize];
        // int[] values = tree.getArray();
        for (int i = 0; i < largestSize; i++) {
            for (int j = 0; j < largestSize; j++) {
                int v = weight(values[i], values[j]);
                if (v != 0) {
                    distanceMatrix[i][j] = v;
                }
                else if (i != j) {
                    distanceMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int k = 0; k < largestSize; k++) { // Compute all k paths
            for (int i = 0; i < largestSize; i++) {
                if (distanceMatrix[i][k] == Integer.MAX_VALUE) {
                    continue;
                }
                for (int j = 0; j < largestSize; j++) {
                    if ((distanceMatrix[k][j] != Integer.MAX_VALUE)
                            && (distanceMatrix[i][j] > (distanceMatrix[i][k]
                                    + distanceMatrix[k][j]))) {
                        distanceMatrix[i][j] =
                                distanceMatrix[i][k] + distanceMatrix[k][j];
                        if (distanceMatrix[i][j] > diameter) {
                            diameter = distanceMatrix[i][j];
                        }
                    }
                }
            }
        }

        if (diameter == 0 && largestSize > 1) {
            diameter = 1;
        }
    }

    /**
     * Compute the weight of an edge
     * 
     * @param i
     *            first vertex
     * @param j
     *            second vertex
     * @return weight of the edge
     */
    private int weight(int i, int j) {
        if (graphArray[i].getNeighboor().contains(j)) {
            return 1;
        }
        return 0;
    }

    /**
     * Find and return the root of the given vertex
     * 
     * @param curr
     *            represent given vertex
     * @return root of curr
     */
    private int find(int curr) {
        if (unionArray[curr] == -1) {
            return curr; // At root
        }

        unionArray[curr] = find(unionArray[curr]);

        return unionArray[curr];
    }

    /**
     * get the number of connected components
     * 
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
        connectedComponents = 0;
        for (int i = 0; i < graphArray.length; i++) {
            if (unionArray[i] == -1) {
                connectedComponents++;
                tempSize = unionWeight[i];
                if (tempSize > largestSize) {
                    largestSize = tempSize;
                    largestIndex = i;
                }
            }
        }

    }

    /**
     * get the number of element of the vertex with the most elments
     * 
     * @return number of elments
     */
    public int getLargestSize() {
        return largestSize;
    }

    /**
     * Print a string representation of the graphArray
     * 
     * @return string representation of the graphArray
     */
    public String printGraph() {
        computeUnion();
        computeConnectedComponents();
        computeTree();
        computeFloyd();
        String output = "There are " + connectedComponents
                + " connected components\n"
                + "The largest connected component has " + largestSize
                + " elements\n" + "The diameter of the largest component is "
                + diameter;
        return output;
    }

}
