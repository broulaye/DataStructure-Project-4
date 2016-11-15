import org.junit.Assert;
import student.TestCase;

/**
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 10/22/2016.
 */
public class BufferBlockTest extends TestCase {
    /**
     * Test basic block class functionalities
     */
    public void testBlockFunctions() {
        byte[] bytes = new byte[13];
        BufferBlock aBlock = new BufferBlock(bytes, 23, true);
        Assert.assertTrue(aBlock.isDirty());
        BufferBlock bBlock = new BufferBlock();
        bBlock.setBlock(bytes);
        bBlock.setDirty(false);
        Assert.assertFalse(bBlock.isDirty());
        Assert.assertEquals(bytes, bBlock.getBlock());
        bBlock.setPos(4);
        Assert.assertNotEquals(aBlock.getPos(), bBlock.getPos());
    }
}
