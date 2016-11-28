import java.io.PrintWriter;

/**
 * Hash table class.
 *
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 09/05/2015
 */

public class Hash {
    // stores the type of hash table
    private String type;

    // Array of strings
    private Handle[] valueArray;

    // number of elements in table
    private int numbElements;

    // memory manager
    private MemManager manager;

    /**
     * Create a new Hash object.
     *
     * @param initialSize
     *            represent the hash table's initial size
     * @param memManager
     *            represent a pointer to the memory manager
     * @param type
     *            represent the type of value (song, artist)
     * @throws Exception
     *             thrown when negative size value is passed
     */
    public Hash(int initialSize, MemManager memManager, String type)
            throws Exception {
        if (initialSize <= 0) {
            throw new Exception("WARN: invalid size type");
        }
        this.type = type;
        valueArray = new Handle[initialSize];
        numbElements = 0;
        manager = memManager;
    }

    /**
     * Finds a string in the database
     *
     * @param str
     *            string to be retrieved
     * @return position of retrieved string, -1 if not found
     */
    private int getP(String str) {
        int index = hash(str, valueArray.length);
        int pos = index;
        int i = 0;
        do {

            if (valueArray[pos] != null && !valueArray[pos].isTombStone()) {
                String x  = handle2String(valueArray[pos]);
                if(str.equalsIgnoreCase(handle2String(valueArray[pos]))) {
                    return pos;
                }


            }
            pos = (index + ++i * i) % valueArray.length;
        }
        while (pos != index);
        return -1;
    }

    /**
     * return the handle at the given position
     * @param pos represent the position
     * @return a handle
     */
    public Handle getHandle(int pos) {
        if (pos < 0) {
            return null;
        }
        return valueArray[pos];
    }

    /**
     * returns the position of the handle for str
     * @param str the string to get
     * @return the position of that string
     */
    public int get(String str) {
        return getP(str);
    }

    /**
     * get string for given handle
     *
     * @param theHandle
     *            handle associated with returned string
     * @return retrieved string
     */
    public String handle2String(Handle theHandle) {

        return manager.get(theHandle);
    }

    /**
     * @param str
     *            (string to insert)
     * @param writer
     *            used to return status of operation
     * @return handle resulting from insertion
     */
    public Handle insertString(String str, PrintWriter writer) {
        int position = get(str);
        if (position != -1) {
            writer.println("|" + str + "| duplicates a record already in the "
                    + type + " database.");
            return valueArray[position];
        }
        if (numbElements + 1 > (valueArray.length >> 1)) {
            doubleSize();
            writer.println(type + " hash table size doubled.");
        }
        int index = hash(str, valueArray.length);
        int pos = index;
        int i = 0;
        while (valueArray[pos] != null) {
            if (valueArray[pos].isTombStone()) {
                break;
            }
            pos = (index + ++i * i) % valueArray.length;
        }
        numbElements++;
        // store handle after storing string in memory pool
        Handle aHandle = manager.insert(str, writer);
        valueArray[pos] = aHandle;
        writer.println("|" + str + "| is added to the " + type + " database.");
        return aHandle;
    }

    /**
     * insert handle in table
     *
     * @param handle
     *            handle to be inserted
     */
    private void insert(Handle handle) {
        int index = hash(handle2String(handle), valueArray.length);
        int pos = index;
        int i = 0;
        while (valueArray[pos] != null) {
            pos = (index + ++i * i) % valueArray.length;
        }
        valueArray[pos] = handle;
    }

    /**
     * print the hash table
     * 
     * @return a string representation of the hash table
     */
    public String printTable() {
        StringBuilder builder = new StringBuilder();
        int pos = 0;
        for (Handle handle : valueArray) {
            if (handle != null && !handle.isTombStone()) {
                builder.append("|").append(handle2String(handle)).append("| ")
                        .append(pos).append("\n");
            }
            pos++;
        }
        builder.append("total ").append(type.toLowerCase()).append("s: ")
                .append(numbElements).append("\n");
        return builder.toString();
    }

    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    private int hash(String s, int m) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (char aC : c) {
                sum += aC * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (char aC : c) {
            sum += aC * mult;
            mult *= 256;
        }
        return (int) (Math.abs(sum) % m);
    }

    /**
     * get the number of elements
     *
     * @return the number of elements
     */
    public int getElement() {
        return numbElements;
    }

    /**
     * remove string from table
     *
     * @param str
     *            string to remove
     * @return true if operation was successful
     */
    public boolean removeString(String str) {
        int where = get(str);
        if (where == -1) {
            return false;
        }
        valueArray[where].setTombstone();
        manager.remove(valueArray[where]);
        numbElements--;
        return true;
    }

    /**
     * doubles the size of table and rehashes content
     */
    private void doubleSize() {
        Handle[] copy = valueArray.clone();
        valueArray = new Handle[valueArray.length * 2];
        for (Handle handle : copy) {
            if (handle != null && !handle.isTombStone()) {
                insert(handle);
            }
        }
    }

}