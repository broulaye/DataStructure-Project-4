import student.TestCase;

/**
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 12/05/2016.
 */
public class BufferPoolTest extends TestCase{

    private int numOfBuff;
    private int blockSize;
    private String memFile;
    BufferPool pool;
    
    /********************
     * Set up variables *
     ********************/
    public void setUp() {
        numOfBuff = 10;
        blockSize = 32;
        memFile = "testMemFile";
        pool = new BufferPool(memFile, numOfBuff, blockSize);

    }
    
    /**
     * Complicated insertion and deletion test
     */
    public void complicatedInsert() {
        String artist1 = "artist1";
        String artist2 = "artist2";
        String song1 = "song1";
        String song2 = "song2";
        
        for(int i= 0; i <1000; i++) {
            artist1 = artist1 + "i";
            pool.insert(artist1.getBytes(), artist1.length());
        }
        
        
    }

    
    
}
