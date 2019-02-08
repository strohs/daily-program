package daily_program.adjacent_colors_v2;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Given a grid, find the maximum number of connected colors. Cells in a grid are connected if they share the same color
 * with the cell to the left,right,above or below it. This is similar to a flood-fill algorithm.
 * This challenge will use three colors, R(R), G(G), B(B), and a 3x4 grid (for simplicity).
 *
 * Here is the default grid:
 *                          G  G  B  R
 *                          G  B  R  B
 *                          R  B  B  B
 * the max number of connected colors is five for the color B
 *
 *
 * User: Cliff
 */


enum Color {R, G, B} // Red, Green, Blue

/**
 * Grid is a 2D grif backed by a generic 2D java array of type T
 *
 * @param <T>
 */
class Grid<T> {

    private T[][] grid;
    private Class<T> cls;

    @SuppressWarnings("unchecked")
    Grid(Class<T> cls, int rows, int cols) {
        this.cls = cls;
        this.grid = (T[][]) Array.newInstance(cls, rows, cols);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                sb.append( String.format(" %s ", grid[r][c]) );
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Optional<T> get( int r, int c ) {
        if (validIndex(r,c))
            return Optional.of( grid[r][c] );
        else
            return Optional.empty();
    }

    public Optional<T> get( Pos p ) {
        return get(p.r, p.c);
    }

    // return the Positions(indices) of the cells adjacent to r,c
    public List<Pos> adjacentPositions(int r, int c) {
        List<Pos> poss;
        // check cells: above,below,right,left
        List<Pos> indices = Arrays.asList( new Pos(r-1,c), new Pos(r,c+1), new Pos(r+1,c), new Pos(r, c-1));
        return indices.stream().filter( p -> !get( p.r, p.c ).isEmpty() ).collect(Collectors.toList());
    }

    public List<Pos> adjacentPositions(Pos p) {
        return adjacentPositions(p.r, p.c);
    }

    public void set( int r, int c, T val ) { grid[r][c] = val; }

    @SafeVarargs
    public final void setRow(int row, T... vals) {
        if (grid[row].length >= 0)
            System.arraycopy(vals, 0, grid[row], 0, grid[row].length);
    }

    public int rowSize() { return grid.length; }
    public int colSize() { return grid[0].length; }

    private boolean validIndex(int r, int c ) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }
}

// represents a 2-dimensional position (index) in the grid
class Pos {
    int r;
    int c;

    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", r,c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return r == pos.r &&
                c == pos.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}

public class ConnectedColors {

    static Color randomColor() {
        int sz = Color.values().length;
        int randomNum = ThreadLocalRandom.current().nextInt(0, sz);
        if ( randomNum == 0)
            return Color.R;
        else if ( randomNum == 1)
            return Color.G;
        else
            return Color.B;
    }

    static boolean equalColors(Grid g, Pos p1, Pos p2) {
        Color c1 = (Color) g.get(p1).orElseThrow();
        Color c2 = (Color) g.get(p2).orElseThrow();
        return c1.equals(c2);
    }

    static void checkAndSetMaxLongest(List<Pos> current, List<Pos> max) {
        if (current.size() > max.size()) {
            max.clear();
            max.addAll(current);
        }
    }

    static void checkAndAddCurrentColor(Grid<Color> g, Pos p, List<Pos> currentLongest) {
        if (currentLongest.isEmpty() || equalColors(g, p, currentLongest.get(0)))
            currentLongest.add(p);
    }

    // returns neighbors of cp that are the same color as cp
    static Collection<Pos> unvisitedSameColorNeighbors(Pos cp, Grid<Color> g, Set<Pos> visited ) {
        return g.adjacentPositions( cp )
                .stream()
                .filter( pos -> !visited.contains(pos) && equalColors(g, cp, pos)).collect(Collectors.toList());
    }

    /**
     * iterative solution to finding the maximum connected colors in a Grid
     *
     * @param grid - 2D grid of colors
     * @return - a list of Pos(ition) objects that represent that maximum connected colors in the Grid
     */
    static List<Pos> maxConnectedColorsIter(Grid<Color> grid) {
        // iterate through each row of the grid using Depth First Search to finds the longest connected color
        Set<Pos> visited = new HashSet<>();
        List<Pos> maxLongest = new ArrayList<>();
        List<Pos> currLongest = new ArrayList<>();
        Deque<Pos> neighbors = new ArrayDeque<>();

        // cp is current position being visited
        Pos cp;
        for (int r = 0; r < grid.rowSize(); r++) {
            for (int c = 0; c < grid.colSize(); c++) {
                cp = new Pos(r,c);
                if ( visited.contains(cp) ) continue;
                // add cp as the current color
                checkAndAddCurrentColor( grid, cp, currLongest);
                // get neighbors that are the same color as cp
                neighbors.addAll( unvisitedSameColorNeighbors(cp, grid, visited) );
                // mark cp as visited
                visited.add(cp);

                // now visit all of cp's neighbors.. this is the Depth First Search
                cp = neighbors.pollLast();
                while ( cp != null ) {
                    if ( !visited.contains(cp) ) {
                        checkAndAddCurrentColor( grid, cp, currLongest );
                        neighbors.addAll( unvisitedSameColorNeighbors(cp, grid, visited) );
                        visited.add(cp);
                    }
                    cp  = neighbors.pollLast();
                }
                checkAndSetMaxLongest( currLongest, maxLongest );
                currLongest.clear();
            }
        }
        return maxLongest;
    }

    private static Grid buildDefaultColorGrid() {
        Grid<Color> g = new Grid<>(Color.class, 3, 4);
        g.setRow( 0, Color.G, Color.G, Color.B, Color.R );
        g.setRow( 1, Color.G, Color.B, Color.R, Color.B );
        g.setRow( 2, Color.R, Color.B, Color.B, Color.B );
        return g;
    }

    private static Grid buildRandomGrid( int rows, int cols ) {
        Grid<Color> g = new Grid<>(Color.class, rows, cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g.set( r,c, randomColor() );
            }
        }
        return g;
    }

    // print out the grid to System.out but only show the positions contained in the poss list
    private static String printDebugGrid(Grid<Color> g, List<Pos> poss) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < g.rowSize(); r++) {
            for (int c = 0; c < g.colSize(); c++) {
                if ( poss.indexOf( new Pos(r,c) ) != -1 ) {
                    Color color = g.get(r,c).get();
                    sb.append(String.format(" %s ", color));
                } else {
                    sb.append(" _ ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Grid<Color> g = buildRandomGrid(10,30);
        System.out.println( g );
        List<Pos> maxs = maxConnectedColorsIter(g);
        System.out.println( String.format("max color length: %s", maxs.size() ));
        System.out.println( String.format("max color: %s", g.get( maxs.get(0) ).get() ));
        System.out.println(printDebugGrid(g, maxs));

    }
}
