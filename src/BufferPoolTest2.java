import student.TestCase;

import java.util.LinkedList;

/**
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 12/05/2016.
 */
public class BufferPoolTest2 extends TestCase {

    private int numOfBuff;
    private int blockSize;
    private String memFile;
    MemManager manager;
    LinkedList<Handle> handles;

    /********************
     * Set up variables *
     ********************/
    public void setUp() {
        numOfBuff = 10;
        blockSize = 32;
        memFile = "testMemFile";
        manager = new MemManager(numOfBuff, blockSize, memFile);
        handles = new LinkedList<>();

    }

    /**
     * Complicated insertion and deletion test
     */
    public void testcomplicatedInsert() {
        String artist1 = "artist1";
        String artist2 = "artist2";
        String song1 = "song1";
        String song2 = "song2";

        for (int i = 0; i < 1000; i++) {
            artist1 = artist1 + "i";
            handles.add(i, manager.insert(artist1));
        }

        artist1 = "artist1";

        for (int i = 900; i < 950; i++) {
            artist1 = artist1 + "i";
            manager.remove(handles.get(i));
            handles.remove(i);
        }


        for(int i = 0; i < 20; i++) {
            artist2 = artist2 + (i+1);
            handles.add(manager.insert(artist1));
        }

        artist1 = "artist1";
        for (int i = 0; i < 1000; i++) {
            artist1 = artist1 + "i";
            handles.add(i, manager.insert(artist1));
        }

    }
}