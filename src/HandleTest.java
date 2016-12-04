import student.TestCase;

/**
 * Test the Handle class.
 * 
 * @author CS3114 Instructor and TAs
 * @version 9/16/2016
 */

public class HandleTest extends TestCase {

    private Handle myHandle;

    /**
     * Set up the tests that follow.
     */
    public void setUp() {
        myHandle = new Handle(1);
    }

    /**
     * Test the Handle class.
     */
    public void testH() {

        Handle lessHandle = new Handle(2);
        Handle sameHandle = new Handle(1);
        Handle moreHandle = new Handle(0);
        assertEquals(myHandle.compareTo(lessHandle), -1);
        assertEquals(myHandle.compareTo(sameHandle), 0);
        assertEquals(myHandle.compareTo(moreHandle), 1);
        assertEquals(myHandle.pos(), 1);
        assertEquals(myHandle.toString(), "1");
    }

    /**
     * Test getVertex method
     */
    public void testGetVertex() {
        assertEquals(-1, myHandle.getVertex());
    }

    /**
     * Test setVertex method
     */
    public void testSetVertex() {
        assertEquals(-1, myHandle.getVertex());
        myHandle.setVertex(1);
        assertEquals(1, myHandle.getVertex());
    }
}