package daily_program;


/**
 * merge two sorted arrays of integers into a single sorted array. Assume ascending order of elements
 */
public class MergeSortedArrays {

    // merge two arrays
    public static int[] arrayMerge(int[] xs, int[] ys) {
        int[] sorted = new int[xs.length + ys.length];
        int si = 0; // index to sorted array
        int xi = 0; // index to shorter length array
        int yi = 0; // index to longer length array

        while (xi < xs.length && yi < ys.length) {
            switch (Integer.compare(xs[xi], ys[yi])) {
                case -1:
                    sorted[si++] = xs[xi++];
                    break;
                case 0:
                    sorted[si++] = xs[xi++];
                    sorted[si++] = ys[yi++];
                    break;
                case 1:
                    sorted[si++] = ys[yi++];
                    break;
            }
        }
        // add any remaining values from either of the arrays to the sorted array
        if (xi < xs.length) {
            while (xi < xs.length) {
                sorted[si++] = xs[xi++];
            }
        } else {
            while (yi < ys.length) {
                sorted[si++] = ys[yi++];
            }
        }
        return sorted;
    }

    public static void main(String[] args) {
        int[] xs = {11, 15, 33, 37, 55, 77, 99};
        int[] ys = {12, 17, 35, 39, 57, 79, 101};

        int[] sorted = arrayMerge(xs, ys);
        for (int i : sorted) {
            System.out.println(i);
        }
    }
}
