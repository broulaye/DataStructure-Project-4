import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 11/01/2016
 */
public class BufferPool implements BufferPoolADT {
    private FreeBlockList freeBlocks;
    private BufferBlock[] pool;
    private RandomAccessFile file;
    private int expandBy;
    private int blockSize;
    private int maxPoolSize;
    private int size;
    private int cacheHit;
    private int cacheMiss;
    private int numDiscWrite;
    private int length;
    private BufferBlock tempBlock;

    /**
     * constructor that creates a new buffer pool from file
     * 
     * @param file
     *            represent file name
     * @param poolSize
     *            represent pool size
     */
    public BufferPool(String file, int poolSize, int blckSz) {
        length = poolSize;
        expandBy = poolSize;
        freeBlocks = new FreeBlockList(poolSize);
        pool = new BufferBlock[poolSize];
        cacheHit = 0;
        cacheMiss = 0;
        numDiscWrite = 0;
        blockSize = blckSz;
        tempBlock = new BufferBlock(blockSize);
        for (int i = 0; i < poolSize; i++) {
            pool[i] = new BufferBlock(blockSize);
        }
        size = 0;

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
     */
    @Override
    public int insert(byte[] space, int sz) {
        // Find next available block
        int pos = freeBlocks.getNextAvailable(sz+2);


        while(pos == -1) {
            //keep expanding free block list until available block is found
            freeBlocks.expand(expandBy, length);
            length += expandBy;
            pos = freeBlocks.getNextAvailable(sz+2);
        }


        // get position of block corresponding to given position
        int blockPos = posToBlock(pos);
        // get start position of free space
        int start = pos - (blockPos * blockSize);

        byte temp[] = new byte[sz + 2];
        temp[0] = (byte) ((sz >> 8) & 0xFF);
        temp[1] = (byte) (sz & 0xFF);
        System.arraycopy(space, 0, temp, 2, sz);

        int remaining = sz + 2;
        do {
            //try to find block in the buffer pool
            int result = findBlock(blockPos);

            if(result == -1) {
                // Not in the buffer pool
                cacheMiss++;
                loadBlock(blockPos);
                result = 0;
            }
            else {
                cacheHit++;
            }

            //check if temp will overflow current block
            if(remaining > blockSize - start){
                System.arraycopy(temp, (sz + 2 - remaining), pool[result].getBlock(), start, blockSize);
                blockPos++;
                start = 0;
            }
            else{
                System.arraycopy(temp, (sz + 2 - remaining), pool[result].getBlock(), start, remaining);

            }
            //update remaining and current block in buffer pool
            remaining -= blockSize;
            pool[result].setDirty(true);
            //shift all blocks down once
            tempBlock = pool[result];
            for (int k = result; k > 0; k--) {
                pool[k] = pool[k - 1];
            }

            pool[0] = tempBlock;

        } while(remaining > 0);

        return pos;

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
    public byte[] getBytes(byte[] space, int sz, int pos) {
        int blockPos = posToBlock(pos);
        int start = pos - (blockPos * blockSize);

        int result = findBlockForRead(blockPos);

        int length = (pool[result].getBlock()[start] << 8);
        // check if length bytes span two blocks
        if (start + 1 > blockSize - 1){
            blockPos++;
            // load two consecutive blocks to read length bytes
            result = findBlockForRead(blockPos);
            start = 0;
        }
        length += (pool[result].getBlock()[start]);
        space = new byte[length];
        int remaining = length;
        // reset start cursor if first byte value is beyond block limit
        if(start + 2 > blockSize - 1){
            start = 0;
        }
        blockPos++;
        do{
           int res = findBlockForRead(blockPos);
           //check if remaining will span blocks
            if(remaining > blockSize - start) {
                System.arraycopy(pool[res].getBlock(), start, space, length - remaining,blockSize - start);
                blockPos++;
                start = 0;
            }else{
                System.arraycopy(pool[res].getBlock(), start, space, length - remaining, remaining);
            }
            remaining -= blockSize;
        }while(remaining > 0);

        return space;
    }

    private int findBlockForRead(int blockPos){
        int result = findBlock(blockPos);
        if(result == -1){
            cacheMiss++;
            loadBlock(blockPos);
            pool[0].setDirty(false);
            result = 0;
        }
        else {
            cacheHit++;
        }
        return result;
    }
    /**
     * This function find a given block in the buffer pool
     * @param blockPos represent position of the block in the file
     * @return position of the block in the pool or -1
     */
    private int findBlock(int blockPos) {
        int pos = -1;
        for (int i = 0; i < size; i++) {
            if (pool[i].getPos() == blockPos) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    /***
     * This function loads a given block in the buffer pool
     * @param blockPos position of the block in the file
     */
    private void loadBlock(int blockPos) {
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
    }

    /**
     * this function get the length of the record in the file
     * @param array byte array that contains the length
     * @param start where to start reading the length
     * @return length of the record
     */
    private int getLength(byte array[], int start) {
        int length = (array[start] << 8) + array[start + 1];
        return length;
    }

    /**
     * Removes string at given location
     *
     * @param location
     *            location of string
     */
    public void removeStringAt(int location) {
        byte length[] = new byte[2];
        getBytes(length, 2, location);
        int size = (length[0] << 8) + length[1];
        freeBlocks.freeUpSpace(location, size + 2);
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
        return ((pos)/ blockSize);
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

    /**
     * print free block list
     *
     * @return a string representation of the blocks
     */
    public String printFreeBlocks() {
        if (freeBlocks.isEmpty()) {
            return "(" + (pool.length) + "," + "0)";
        }
        return freeBlocks.printBlocks();
    }
}
