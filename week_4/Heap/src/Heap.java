import java.util.Arrays;

/**
 * Created by thomaspan on 1/18/17.
 */
public class Heap {
    public static void sort(Comparable[] pq) {
        int N = pq.length-1;

        for (int k = N/2; k >= 1; k--) {
            sink(pq, k, N);
        }

        while (N > 0) {
            exch(pq, 0, N);
            sink(pq, 0, --N);
        }
    }

    private static void sink(Comparable[] a, int k, int N){
        while (2*(k+1)-1 <= N) {
            int j = 2*(k+1)-1;
            if (j < N && less(a, j, j+1)) {
                j++;
            }
            if (!less(a, k, j)) {
                break;
            }
            exch(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int k, int j) {
        return a[k].compareTo(a[j]) < 0;
    }

    private static void exch(Comparable[] a, int k, int j) {
        Comparable t = a[k];
        a[k] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        Comparable[] list = {"S","O","R","T","E","X","A","M","P","L","E"};
        Heap.sort(list);
        System.out.println(Arrays.toString(list));
    }

}
