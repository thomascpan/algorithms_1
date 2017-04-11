import java.util.Arrays;

/**
 * Created by thomaspan on 12/7/16.
 */
public class QuickUnionUF {
    private int[] id;
    private int[] sz;

    public QuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public void union(int x, int y) {
        int i = findRoot(x);
        int j = findRoot(y);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j]+=sz[i];
        } else {
            id[j] = i;
            sz[i]+=sz[j];
        }
    }

    public boolean connected(int x, int y) {
        return findRoot(x) == findRoot(y);
    }

    public void print(String array) {
        if (array == "id") {
            System.out.println(Arrays.toString(id));
        } else {
            System.out.println(Arrays.toString(sz));
        }
    }

    private int findRoot(int x) {
        while (x != id[x]) {
            id[x] = id[id[x]];
            x = id[x];
        }
        return x;
    }
}
