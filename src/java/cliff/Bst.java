package cliff;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Very simple Binary Search Tree insert algorithm, plus an additional in order print method
 * User: Cliff
 * Date: 3/18/14
 * Time: 1:57 PM
 */
public class Bst {

    Node root = null;

    public Node insert( Integer i ) {
        root = rinsert( i, root );
        return root;
    }

    private Node rinsert( Integer i, Node n ) {
        if ( n == null ) {
            return new Node<>( i, null, null );
        }
        else if ( n.value.compareTo( i ) > 0 ) {
            n.left = rinsert( i , n.left );
        } else {
            n.right = rinsert( i, n.right );
        }
        return n;
    }

    //print in order
    public static void printTree( Node n ) {

        if (n != null) {
            printTree( n.left );
            System.out.println( n.value );
            printTree( n.right );
        }
    }

    //print tree nodes by level, this is iterative approach
    public static void printLevelOrder( Node root ) {
        Queue<Node> queue = new LinkedList<>();
        queue.add( root );
        while ( !queue.isEmpty() ) {
            //queue should contain all the nodes for the current level we are at, so print them
            int levelSize = queue.size();
            for ( int i = 0; i < levelSize; i++ ) {
                Node n = queue.remove();
                if ( n != null ) {
                    System.out.print( n.value + " ");
                    queue.add( n.left );
                    queue.add( n.right );
                }
            }
            System.out.println();
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
        System.out.println("LEVEL ORDER");
        printLevelOrder( tree.root );
    }
}
