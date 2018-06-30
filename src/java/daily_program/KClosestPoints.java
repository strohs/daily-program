package daily_program;

import java.util.*;
import java.util.stream.Collectors;

/**
 * given an array of integer tuples that represent x,y coordinates on a 2D grid, What are the K closest point to the
 * origin point (located at 0,0).
 *
 *
 * User: Cliff
 */
public class KClosestPoints {
    //in this aray closest points are [-1,0 ::1], [0,-2 ::2], [-2,-3 ::3] tie with [3,2 ::3]
    static final int [][] points1 = { {-2,-4},{0,-2}, {-1,0}, {3,-5}, {-2,-3}, {3,2} };


    //helper class that stores x,y coordinates and the distance between them and the origin
    private class XYPair {
        private Integer x;    //x coordinate
        private Integer y;    //y coordinate
        private Integer dist; //distance between x,y coordinate and the origin

        public XYPair( int [] xy ) {
            this.x = xy[0];
            this.y = xy[1];
            this.dist = distance( 0,0, x, y );
        }

        //compute the euclidian distance between two points.
        int distance( int x1, int y1, int x2, int y2) {
            double xd = Math.pow( x2 - x1, 2.0 ); // (x2 - x1)^2
            double yd = Math.pow( y2 - y1, 2.0 ); // (y2 - y1)^2

            return (int)Math.sqrt(xd + yd);
        }
        
        @Override
        public String toString() {
            return String.format( "x: %2d y: %2d   dist: %d", x,y,dist );
        }

    }

    /**
     * find the k closest points to the origin. This algorithm is O(n) in time, it iterates through the point
     * array one time, creates a helper object that stores the x,y coordinate plus distance to the origin, and then
     * stores the helper object in a priority queue that is configured to sort objects based on distance to the
     * origin (by using a comparator).
     *
     * @param points - an array of arrays containing x,y coordinates
     * @param k integer specifying the number of closest points to find
     * @return collection of XYPair objects representing the k-closest points to the origin
     */
    public Collection<XYPair> findKClosest( int [][] points, int k ) {
        //this is a priority queue that is configured to sort XYPair objects by distance to origin,
        // with closest distance stored at head of the queue
        PriorityQueue<XYPair> pqueue = new PriorityQueue<>( Comparator.comparing( o -> o.dist ) );
        Arrays.stream( points )
                .forEach( point -> {
                    XYPair xyd = new XYPair( point ); //convert each point to a XYPair
                    pqueue.offer( xyd );              //store each point in Priority Queue (acting as a max heap
                } );
        return pqueue.stream().limit( k ).collect( Collectors.toList() );
    }
    


    public static void main( String[] args ) {
        KClosestPoints kcp = new KClosestPoints();
        Collection<XYPair> kclosest = kcp.findKClosest( points1, 3 );
        kclosest.forEach( System.out::println );
        //System.out.println("-----DEBUG------------");
        //Arrays.stream( points1 ).map( ints -> kcp.new XYPair( ints ) ).forEach( System.out::println );
    }
}
