
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
     * @param numBuffs
     *            represent number of buffers
     * @param blockSize
     *            represent the size for a block in the memory pool
     * @param memFile
     *            represent the name of the file that will store the memory
     *            pool.
     */
    public MemManager(int numBuffs, int blockSize, String memFile) {
        bufferPool = new BufferPool(memFile, numBuffs, blockSize);
    }

    /**
     * Insert a record and return its position handle
     *
     * @param str
     *            string to be stored
     * @return handle for str
     */
    public Handle insert(String str) {
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
     * 
     *         public String dump() { return bufferPool.printFreeBlocks(); }
     */

    /**
     * get string corresponding to given handle
     *
     * @param theHandle
     *            handle used for retrieval
     * @return string from memory pool
     */
    public String get(Handle theHandle) {
        byte[] temp = null;
        temp = bufferPool.getBytes(temp, 0, theHandle.pos());
        /*if(temp.length <= 1) {
            return "";
        }*/
        return new String(temp);
    }

    /**
     * This method close and flush buffer pool
     */
    public void closePool() {
        bufferPool.close();
    }

}
