/**
 * 
 */

/**
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 11-01-2016
 */
public interface BufferPoolADT {

    /**
     * Copy "sz" bytes from "space" to position "pos" in the buffered storage
     * 
     * @param space
     *            array to be inserted
     * @param sz
     *            size of the array
    */
    public int insert(byte[] space, int sz);

    /**
     * Copy "sz" bytes from position "pos" of the buffered storage to "space"
     * 
     * @param space
     *            array to copied into
     * @param sz
     *            size to be copied
     * @param pos
     *            position where to ge value
     */
    public void getBytes(byte[] space, int sz, int pos);
}
