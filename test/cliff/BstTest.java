package cliff;

import cliff.Bst;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 1/5/2016
 * Time: 5:18 PM
 */
public class BstTest {

    @Test
    public void testNewBST() throws Exception {
        Bst tree = new Bst();
        assertNull( "new tree root should be null", tree.root );
    }

    @Test
    public void testInsert() throws Exception {
        Bst tree = new Bst();
        tree.insert( 55 );
        assertEquals( tree.root.value, 55 );
        assertNull( tree.root.left );
        assertNull( tree.root.right );

        tree.insert(22);
        tree.insert(66);
        assertEquals( tree.root.left.value, 22 );
        assertEquals( tree.root.right.value, 66 );
        assertEquals( tree.root.value, 55 );
    }

    @Test
    public void testPrintTree() throws Exception {

    }
}