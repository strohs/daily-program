package cliff;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 3/18/14
 * Time: 1:55 PM
 */
public class Node<T extends Comparable<T>> {

    T value;
    Node left;
    Node right;

    public Node( T value, Node left, Node right ) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
