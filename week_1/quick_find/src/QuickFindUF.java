import java.util.Arrays;

/**
 * Created by thomaspan on 12/7/16.
 */
public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int x, int y) {
        int temp = id[x];
        id[x] = id[y];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == temp) {
                id[i] = id[y];
            }
        }
    }

    public boolean connected(int x, int y) {
        return id[x] == id[y];
    }

    public void print() {
        System.out.println(Arrays.toString(id));
    }
}
