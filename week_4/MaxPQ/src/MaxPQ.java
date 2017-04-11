/**
 * Created by thomaspan on 1/17/17.
 */
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;

    public MaxPQ() {
        pq = (Key[]) new Comparable[2];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key x) {
        if (++N == pq.length) {
            resize(2 * pq.length);
        }

        pq[N] = x;
        swim(N);
    }

    public Key delMax() {
        if (N > 0 && N == pq.length/4) {
            resize(pq.length/2);
        }
        if (isEmpty()) {
            return null;
        }

        Key max = pq[1];

        exch(1, N--);
        sink(1);
        pq[N+1] = null;
        return max;
    }

    private void swim(int k) {
        while(k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k){
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(j, j+1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private void resize(int capacity) {
        Key[] copy = (Key[]) new Comparable[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = pq[i];
        }
        pq = copy;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {
        MaxPQ<Integer> mpq = new MaxPQ<>();
        mpq.insert(1);
        mpq.insert(2);
        mpq.insert(3);
        mpq.insert(4);
        mpq.insert(5);

        System.out.println(mpq.delMax());
    }
}
