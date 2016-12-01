/**
 * This class represent a block in the file
 * 
 * @author Broulaye Doumbia
 * @version 10-21-2016
 */
public class BufferBlock {
    private byte[] aBlock;
    private int pos;
    private boolean isDirty;

    /**
     * Default constructor
     */
    public BufferBlock(int size) {
        aBlock = new byte[size];
        pos = 0;
        isDirty = false;
    }

    /**
     * Constructor setting values of parameters
     * 
     * @param block
     *            represent the new block
     * @param pos
     *            represent new block position
     * @param isDirty
     *            represent new block state
     */
    public BufferBlock(byte[] block, int pos, boolean isDirty) {
        this.aBlock = block;
        this.pos = pos;
        this.isDirty = isDirty;
    }

    /**
     * get the block
     * 
     * @return the block
     */
    public byte[] getBlock() {
        return aBlock;
    }

    /**
     * set block to new value
     * 
     * @param block1
     *            new value
     */
    public void setBlock(byte[] block1) {
        aBlock = block1;
    }

    /**
     * get position of block
     * 
     * @return block position
     */
    public int getPos() {
        return pos;
    }

    /**
     * set position of block
     * 
     * @param pos
     *            new block position
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * get block state
     * 
     * @return block state
     */
    public boolean isDirty() {
        return isDirty;
    }

    /**
     * set block state
     * 
     * @param isDirty2
     *            block new state
     */
    public void setDirty(boolean isDirty2) {
        this.isDirty = isDirty2;
    }

    public String toString(){
        return new String(this.aBlock);
    }
}
