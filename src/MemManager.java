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
        byte temp[] = str.getBytes();
        byte tempByte[] = new byte[str.length()+2];
        int length = str.length();
        // copy size to pool as 2 byte number
        tempByte[0] = (byte) ((length >> 8) & 0xFF);
        tempByte[1] = (byte) (length & 0xFF);
        int k =0;
        for(int i =2; i < length; i++) {
            tempByte[i] = temp[k];
            k++;
        }
        int position = bufferPool.insert(tempByte, length+2);
        return new Handle(position);
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
        bufferPool.getBytes(temp,0,theHandle.pos());
        return temp.toString();
    }
}
