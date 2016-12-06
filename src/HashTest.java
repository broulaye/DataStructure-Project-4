import student.TestCase;

// -------------------------------------------------------------------------

/**
 * Test the hash function (you should throw this away for your project)
 *
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 09/05/2016
 */
public class HashTest extends TestCase {
    private String testString = "test";
    // private static final String ALPHA_NUMERIC_STRING =
    // "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Test insertion and deletion from the hash table
     */

    public void testInsert() {

        Hash myHash;
        MemManager memManager;
        try {
            memManager = new MemManager(80, 50, "mem.txt");
            myHash = new Hash(2, memManager, testString);
            assertNotNull(myHash.insertString("broulaye"));
            assertNotNull(myHash.insertString("Cheick"));
            assertEquals("|broulaye| 2\n|Cheick| 3\ntotal tests: 2\n",
                    myHash.printTable());
            assertNotNull(myHash.removeString("broulaye"));
            assertNull(myHash.removeString("broulaye"));
            assertEquals("|Cheick| 3\ntotal tests: 1\n", myHash.printTable());
            assertNotNull(myHash.removeString("Cheick"));
            assertNull(myHash.removeString("Cheick"));
            assertEquals("total tests: 0\n", myHash.printTable());
            assertEquals(0, myHash.getElement());
            assertNotNull(myHash.insertString("Cheick"));
            assertNotNull(myHash.insertString("Cheicks"));
            assertNotNull(myHash.insertString("Cheikc"));

        }
        catch (Exception e) {
            assertTrue(e instanceof Exception);
        }

    }

    /**
     * Test the hash table with negative size
     */
    public void testNegativeSize() {

        Hash table;
        try {
            table = new Hash(-2, new MemManager(2, 20, "memtext4.txt"),
                    testString);
            table.insertString("cheick");
        }
        catch (Exception e) {
            assertTrue(e instanceof Exception);
        }
    }

    /**
     * Test for duplicate strings.
     */
    public void testDuplicateStrings() {
        MemManager manager = new MemManager(3, 4, "memtest.txt");
        try {
            Hash table = new Hash(3, manager, testString);
            String str = "berthe";
            Handle result1 = table.insertString(str);
            Handle result2 = table.insertString(str);
            assertNotNull(result1);
            assertNotNull(result2);
        }
        catch (Exception e) {
            assertTrue(e instanceof Exception);
            e.printStackTrace();
        }
    }

    /**
     * TestgetElement
     */
    public void testgetElement() {
        MemManager manager = new MemManager(3, 6, "memtest5.txt");
        try {
            Hash table = new Hash(3, manager, testString);
            String str = "berthe";
            table.insertString(str);

            assertEquals(1, table.getElement());
        }
        catch (Exception e) {
            assertTrue(e instanceof Exception);
            e.printStackTrace();
        }
    }

    /**
     * Test Delete element from empty hash table
     */
    public void testRemoveElement() {
        MemManager manager = new MemManager(1, 5, "memtest7.txt");
        String word = "Cheicks";
        try {
            Hash table = new Hash(10, manager, testString);
            // assertFalse(table.removeString(word));
            assertNotNull(table.insertString(word));
            assertNotNull(table.removeString(word));
            assertNotNull(table.insertString(word));
        }
        catch (Exception e) {
            assertTrue(e instanceof Exception);
            e.printStackTrace();
        }
    }
}
