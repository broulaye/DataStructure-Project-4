/**
 * Handle class definition
 *
 * @author CS3114 Instructors and TAs
 * @version 9/15/2016
 */

public class Handle {
    /**
     * The position for the associated message in the memory pool
     */
    private int thePos;

    /**
     * defines the state of handle in hash table
     */

    private boolean tombstone;

    // ----------------------------------------------------------
    /**
     * Create a new Handle object.
     *
     * @param p
     *            Value for position
     */
    public Handle(int p) {
        thePos = p;
    }

    // ----------------------------------------------------------
    /**
     * Overload compareTo
     *
     * @param it
     *            The handle being compared against
     * @return standard values of -1, 0, 1
     */
    public int compareTo(Handle it) {
        if (thePos < it.pos()) {
            return -1;
        }
        else if (thePos == it.pos()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    // ----------------------------------------------------------
    /**
     * Getter for position
     *
     * @return The position
     */
    public int pos() {
        return thePos;
    }

    // ----------------------------------------------------------
    /**
     * Overload toString
     *
     * @return A print string
     */
    public String toString() {
        return String.valueOf(thePos);
    }

    // -----------------------------------------------------------
    /**
     * check if handle is tombstone
     *
     * @return state of handle
     */
    public boolean isTombStone() {
        return tombstone;
    }

    // ------------------------------------------------------------
    /**
     * set the tombstone
     */
    public void setTombstone() {
        tombstone = true;
    }
}
