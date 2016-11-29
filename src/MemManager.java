import java.io.PrintWriter;

/**
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 9/5/2016.
 */
public class MemManager {

    // memory pool
    private BufferPool bufferPool;

    /**
     * constructor
     *
     * @param poolSize
     *            size of memory pool in bytes
     */
    public MemManager(int poolSize, int blockSize, String memFile) {
        bufferPool = new BufferPool(memFile, poolSize, blockSize);
    }

    /**
     * Insert a record and return its position handle
     *
     * @param str
     *            string to be stored
     * @param writer
     *            used to return status of operation
     * @return handle for str
     */
    public Handle insert(String str, PrintWriter writer) {
        int position = bufferPool.insert(str.getBytes(), str.length());
        return new Handle(position);
    }

    /**
     * Dump content of memory pool
     *
     * @return a string representation of the memory pool
     */
    public String dump() {
        return bufferPool.printFreeBlocks();
    }


    /**
     * Free a block at position specified by theHandle Merge adjacent blocks
     *
     * @param theHandle
     *            handle referring to position
     */
    public void remove(Handle theHandle) {
        bufferPool.removeStringAt(theHandle.pos());
    }

    /**
     * Dump content of memory pool
     * 
     * @return a string representation of the memory pool

    public String dump() {
        return bufferPool.printFreeBlocks();
    }*/

    /**
     * get string corresponding to given handle
     *
     * @param theHandle
     *            handle used for retrieval
     * @return string from memory pool
     */
    public String get(Handle theHandle) {
        byte temp[] = null;
        temp = bufferPool.getBytes(temp,0,theHandle.pos());
        String s = new String(temp);
        return s;
    }


}
