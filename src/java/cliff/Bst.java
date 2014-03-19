package cliff;

/**
 * Very simple Binary Search Tree insert algorithm, plus an additional in order print method
 * User: Cliff
 * Date: 3/18/14
 * Time: 1:57 PM
 */
public class Bst {

    Node root = null;

    public Node insert( int i ) {
        root = rinsert( i, root );
        return root;
    }

    private Node rinsert( int i, Node n ) {
        if ( n == null ) {
            return new Node( i, null, null );
        }
        else if ( i <= n.value ) {
            n.left = rinsert( i , n.left );
        } else {
            n.right = rinsert( i, n.right );
        }
        return n;
    }

    public static void printTree( Node n ) {

        if (n != null) {
            printTree( n.left );
            System.out.println( n.value );
            printTree( n.right );
        }
    }


    public static void main( String[] args ) {
        //test case
        Bst tree = new Bst();
        printTree( tree.root );
        tree.insert( 10 );
        tree.insert( 20 );
        tree.insert( 5 );
        tree.insert( 15 );
        tree.insert( 30 );
        printTree( tree.root );
    }
}
