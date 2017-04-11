import java.util.Arrays;

/**
 * Created by thomaspan on 12/12/16.
 */
public class ThreeSum {
    public static int count(int[] a) {
        Arrays.sort(a);

        int N = a.length;
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (ThreeSum.binarySearch(Arrays.copyOfRange(a, j+1, N), -(a[i] + a[j])) != -1) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int binarySearch(int[] a, int key) {
        int lo = 0;
        int hi = a.length-1;
        while (lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = lo +1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
