import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ST<Key extends Comparable<Key>, Value> {
    private String[] keys;
    private int[] vals;

    public ST() {

    }

    public put(Key key, Value, value) {

    }

    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }

        int i = rank(key);

        if (i < N && keys[i].compareTo(key) == 0) {
            return vals[i];
        } else {
            return null;
        }
    }

    public void delete(Key key) {

    }

    public boolean contains(Key key) {

    }

    public boolean isEmpty() {

    }

    public int size() {

    }

    public Iterable<Key> keys() {

    }

    private int rank(Key key) {
        int lo = 0;
        int hi = N-1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return lo;
    }

    public static void main(String[] args) {
        ST<String, Integer> st = new ST<>() {
            for (int i = 0; !StdIn.isEmpty(), i++) {
                String key = StdIn.readString();
                st.put(key, i);
            }
            for (String s : st.keys()) {
                StdOut.println(s + " " + st.get(s));
            }
        }
    }
}