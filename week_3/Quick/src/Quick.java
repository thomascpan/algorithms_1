/**
 * Created by thomaspan on 1/15/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

public class Quick {
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;

        while (true) {
            while (less(a[++i], a[lo])) {
                if (i == hi) {
                    break;
                }
            }

            while (less(a[lo], a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            exch(a, i, j);
        }

        exch(a, lo, j);
        return j;
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    public static Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length-1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) {
                lo = j+1;
            } else if (j > k) {
                hi = j-1;
            } else {
                return a[k];
            }
        }
        return a[k];
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Comparable[] list = {1, 2, 3, 3, 3, 4, 5};
        System.out.println(Quick.select(list, 1));
    }
}
