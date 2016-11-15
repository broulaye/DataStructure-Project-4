import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import org.apache.commons.collections.Buffer;

/**
 * 
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 11/01/2016
 */
public class BufferPool implements BufferPoolADT {

    private BufferBlock[] pool;
    private RandomAccessFile file;
    private int blockSize;
    private int maxPoolSize;
    private int size;
    private int cacheHit;
    private int cacheMiss;
    private int numDiscWrite;
    private BufferBlock tempBlock;

    /**
     * constructor that creates a new buffer pool from file
     * 
     * @param file
     *            represent file name
     * @param poolSize
     *            represent pool size
     */
    public BufferPool(String file, int poolSize) {
        pool = new BufferBlock[poolSize];
        cacheHit = 0;
        cacheMiss = 0;
        numDiscWrite = 0;
        tempBlock = new BufferBlock();
        for (int i = 0; i < poolSize; i++) {
            pool[i] = new BufferBlock();
        }
        size = 0;
        blockSize = 4096;
        maxPoolSize = poolSize;
        try {
            this.file = new RandomAccessFile(file, "rw");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Copy "sz" bytes from "space" to position "pos" in the buffered storage
     * 
     * @param space
     *            source of bytes
     * @param sz
     *            size of bytes
     * @param pos
     *            position to be inserted in
     */
    @Override
    public void insert(byte[] space, int sz, int pos) {
        int blockPos = posToBlock(pos);
        for (int i = 0; i < size; i++) {
            if (pool[i].getPos() == blockPos) {
                cacheHit++;
                System.arraycopy(space, 0, pool[i].getBlock(),
                        ((4 * pos) - blockPos), sz);
                pool[i].setDirty(true);
                tempBlock = pool[i];
                for (int k = i; k > 0; k--) {
                    pool[k] = pool[k - 1];
                }
                pool[0] = tempBlock;
                return;
            }
        }
        // Not in the buffer pool
        cacheMiss++;
        if (size == maxPoolSize) {
            tempBlock = pool[size - 1];
            if (pool[size - 1].isDirty()) {
                writeToFile(pool[size - 1].getBlock(),
                        pool[size - 1].getPos());
            }
            for (int j = size - 1; j > 0; j--) {
                pool[j] = pool[j - 1];
            }

        }
        else {
            tempBlock = pool[size];
            for (int i = size; i > 0; i--) {
                pool[i] = pool[i - 1];
            }
            size++;
        }

        pool[0] = tempBlock;
        pool[0].setPos(blockPos);
        getBlock(blockPos, pool[0].getBlock());
        System.arraycopy(space, 0, pool[0].getBlock(), ((4 * pos) - blockPos),
                sz);
        pool[0].setDirty(true);
    }

    /**
     * private method that write bytes array to the file at a specified position
     * 
     * @param block2
     *            block to be inserted
     * @param bPos
     *            position block need to be put into
     */
    private void writeToFile(byte[] block2, int bPos) {
        try {
            file.seek(bPos);
            file.write(block2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        numDiscWrite++;
    }

    /**
     * Copy "sz" bytes from position "pos" of the buffered storage to "space"
     * 
     * @param space
     *            destination of bytes
     * @param sz
     *            size of bytes
     * @param pos
     *            position of bytes
     */
    @Override
    public void getBytes(byte[] space, int sz, int pos) {
        int blockPos = posToBlock(pos);
        for (int i = 0; i < size; i++) {
            if (pool[i].getPos() == blockPos) {
                cacheHit++;
                System.arraycopy(pool[i].getBlock(), ((4 * pos) - blockPos),
                        space, 0, sz);
                tempBlock = pool[i];
                for (int k = i; k > 0; k--) {
                    pool[k] = pool[k - 1];
                }
                pool[0] = tempBlock;
                return;
            }
        }
        // Not in buffer Pool
        cacheMiss++;
        if (size == maxPoolSize) {
            tempBlock = pool[size - 1];
            if (pool[size - 1].isDirty()) {
                writeToFile(pool[size - 1].getBlock(),
                        pool[size - 1].getPos());
            }
            for (int i = size - 1; i > 0; i--) {
                pool[i] = pool[i - 1];
            }
        }
        else {
            tempBlock = pool[size];
            for (int i = size; i > 0; i--) {
                pool[i] = pool[i - 1];
            }
            size++;
        }
        pool[0] = tempBlock;
        pool[0].setPos(blockPos);
        getBlock(blockPos, pool[0].getBlock());
        pool[0].setDirty(false);
        System.arraycopy(pool[0].getBlock(), ((4 * pos) - blockPos), space, 0,
                sz);

    }

    /**
     * private method to get the block at position blockPos in the file
     * 
     * @param blockPos
     *            position of block
     * @param block
     *            new block to get
     */
    private void getBlock(int blockPos, byte[] block) {
        try {
            file.seek(blockPos);

            file.read(block);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get the number of records in the file
     * 
     * @return number of records
     */
    public int getNumRecord() {
        int length = 0;
        try {
            length = (int) (file.length() / 4);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * convert a position to a block position
     * 
     * @param pos
     *            position to convert
     * @return the block number
     */
    private int posToBlock(int pos) {
        return ((pos * 4) / blockSize) * blockSize;
    }

    /**
     * This method flush the buffer pool and then close the file stream
     */
    public void close() {
        for (int i = 0; i < size; i++) {
            if (pool[i].isDirty()) {
                writeToFile(pool[i].getBlock(), pool[i].getPos());
            }
        }

        pool = null;

        try {
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get number of cache hit
     * 
     * @return number of cache hit
     */
    public int getCacheHit() {
        return cacheHit;
    }

    /**
     * get number of cache miss
     * 
     * @return number of cache miss
     */
    public int getCacheMiss() {
        return cacheMiss;
    }

    /**
     * get number of disc write
     * 
     * @return number of disc write
     */
    public int getNumDiscWrite() {
        return numDiscWrite;
    }
}
