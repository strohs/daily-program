package daily_program.adjacent_colors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a grid, find the maximum number of connected colors. Cells in a grid are connected if they share the same color
 * with the cell to the left,right,above or below it. This is similar to a flood-fill algorithm.
 * This challenge will use three colors, RED(R), GREEN(G), BLUE(B), and a 3x4 grid (for simplicity).
 *
 * Here is the default grid:
 *                          G  G  B  R
 *                          G  B  R  B
 *                          R  B  B  B
 * the max number of connected colors is five for the color BLUE
 *
 * I'm going to use (to be different) a doubly linked list to hold the grid. The
 *
 * User: Cliff
 */

enum Color { RED,GREEN,BLUE }

//the grid of nodes
class Grid {
    int rows;
    int cols;
    List<Node> nodes;

    public Grid( int rows, int cols, List<Node> nodes ) {
        this.rows = rows;
        this.cols = cols;
        this.nodes = nodes;
    }

    //build the default 3x4 grid as a list of connected nodes..BRUTE FORCE build, this is ugly
    public static Grid buildDefGrid( int rows, int cols ) {
        Node n0 = new Node(0,Color.GREEN );
        Node n1 = new Node( 1, Color.GREEN );
        Node n2 = new Node( 2, Color.BLUE );
        Node n3 = new Node( 3, Color.RED );
        Node n4 = new Node( 4, Color.GREEN );
        Node n5 = new Node( 5, Color.BLUE );
        Node n6 = new Node( 6, Color.RED );
        Node n7 = new Node( 7, Color.BLUE );
        Node n8 = new Node( 8, Color.RED );
        Node n9 = new Node( 9, Color.BLUE );
        Node n10 = new Node( 10, Color.BLUE );
        Node n11 = new Node( 11, Color.BLUE );
        //connect the nodes
        n0.right = n1; n0.down = n4;
        n1.left = n0; n1.right = n2; n1.down = n5;
        n2.left = n1; n2.right = n3; n2.down = n6;
        n3.left = n2; n3.down = n7;
        n4.up = n1; n4.right = n5; n4.down = n8;
        n5.up = n1; n5.right = n6; n5.down = n9; n5.left = n4;
        n6.left = n5; n6.up = n2; n6.right = n7; n6.down = n10;
        n7.left = n6; n7.up = n3; n7.down = n11;
        n8.up = n4; n8.right = n9;
        n9.left = n8; n9.up = n5; n9.right = n10;
        n10.left = n9; n10.up = n6; n10.right = n11;
        n11.left = n10; n11.up = n7;

        List<Node> nodes = new ArrayList<>(rows*cols);
        nodes.add(n0);nodes.add(n1);nodes.add(n2);nodes.add(n3);
        nodes.add(n4);nodes.add(n5);nodes.add(n6);nodes.add(n7);
        nodes.add(n8); nodes.add(n9);nodes.add(n10);nodes.add(n11);
        return new Grid( rows, cols, nodes );
    }

//    private static List<Node> buildEmptyLinkedRow( int startIdx, int endIdx ) {
//        int size = endIdx - startIdx;
//        List<Node> row = new ArrayList<>(size);
//        for ( int i = startIdx; i < endIdx; i++ ) {
//            Node n = new Node( i, null );
//            if ( i > startIdx ) {
//                n.left = row.get( i - 1 );
//                row.get( i - 1 ).right = n;
//            }
//            row.add( n );
//        }
//        return row;
//    }
//
//    private static void linkUpDown( List<Node> r1, List<Node> r2 ) {
//        for ( int i = 0; i < r1.size(); i++ ) {
//            r1.get( i ).down = r2.get( i );
//            r2.get( i ).up = r1.get( i );
//        }
//    }
//
//    public static Grid buildEmptyGrid( int rows, int cols ) {
//        List<Node> grid = new ArrayList<>( rows * cols );
//        for ( int i = 0; i < (rows * cols); i++ ) {
//            grid.add( new Node(i,null) );
//        }
//        //link nodes
//
//    }
}


//represents a "cell" in the grid, containing the Color as well as links to adjacent nodes
class Node {

    //index will uniquely identify a node
    int index;
    Color color;
    Node up;
    Node down;
    Node left;
    Node right;

    public Node( int index, Color color ) {
        this.index = index;
        this.color = color;
        up = down = left = right = null;
    }

    public Node( int index, Color color, Node up, Node down, Node left, Node right ) {
        this.index = index;
        this.color = color;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    //get the non-null neighbors of this node
    List<Node> getNeighbors() {
        List<Node> nodes = Arrays.asList( up, down, left, right );
        return nodes.stream().filter( Objects::nonNull ).collect( Collectors.toList() );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Node node = ( Node ) o;
        return index == node.index;
    }

    @Override
    public int hashCode() {

        return Objects.hash( index );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( "Node{" );
        sb.append( "index=" ).append( index );
        sb.append( ", color=" ).append( color );
        sb.append( '}' );
        return sb.toString();
    }
}

public class Main {



    //iterative solution using a Stack for Depth First Search
    // returns a list of the maximum connected nodes
    public static List<Node> maxConnected( Grid grid ) {
        //holds visited nodes
        Set<Node> visited = new HashSet<>();
        //holds the current max nodes
        List<Node> maxConnected = new ArrayList<>();
        //holds currently connected nodes
        List<Node> connected = new ArrayList<>();
        Stack<Node> nodeStack = new Stack<>();

        //start at index 0 in the grid
        nodeStack.add( grid.nodes.get( 0 ) );
        for ( int i = 0; i < grid.nodes.size(); i++ ) {
            Node curNode = grid.nodes.get(i);
            nodeStack.addAll( curNode.getNeighbors() );
            visited.add( curNode );
            connected.add( curNode );
            while ( !nodeStack.empty() ) {
                Node n = nodeStack.pop();
                if ( !visited.contains( n ) ) {
                    visited.add( n );
                    if ( n.color == curNode.color ) {
                        connected.add( n );
                        nodeStack.addAll( n.getNeighbors() );
                    }
                }
            }
            if ( connected.size() > maxConnected.size() ) {
                maxConnected.clear();
                maxConnected.addAll( connected );
            }
            connected.clear();
            visited.clear();
        }
        return maxConnected;
    }

    //recursive solution
    public static List<Node> maxConnectedRec(Grid grid) {
        List<Node> maxConnected = new ArrayList<>();
        grid.nodes.stream().forEach( node -> {
            //get connected colors size
            List<Node> connected = new ArrayList<>();
            Stack<Node> neighbors = new Stack<>();
            Set<Node> visited = new HashSet<>();
            connected.add( node );
            visited.add( node );
            neighbors.addAll( node.getNeighbors() );
            maxConnectedRec( connected, neighbors,visited );
            System.out.println( connected.get( 0 ) );
            System.out.println("   SIZE:" + connected.size() );
            //if connected color size > maxConnected.size, connected is the new maxConnected
            if ( connected.size() > maxConnected.size() ) {
                maxConnected.clear();
                maxConnected.addAll( connected );
            }
        } );
        return maxConnected;
    }

    //helper recursive function
    private static List<Node> maxConnectedRec( List<Node> connected, Stack<Node> neighbors, Set<Node> visited) {
        if ( neighbors.empty() ) return connected;
        Node n = neighbors.pop();
        if ( !visited.contains( n ) && n.color == connected.get(0).color ) {
            visited.add( n );
            connected.add( n );
            neighbors.addAll( n.getNeighbors() );
        }
        return maxConnectedRec( connected, neighbors, visited );
    }

    public static void main( String[] args ) {

        //the grid of nodes
        Grid grid = Grid.buildDefGrid( 3,4 );
        //current longest list of nodes of a color
        List<Node> longest = maxConnectedRec( grid );
        System.out.println( "Max connected length: " + longest.size() );
        System.out.println("Max connected color: " + longest.get( 0 ).color );
        longest.forEach( System.out::println );

    }


}
