/**
 * Created by thomaspan on 12/7/16.
 */
public class QuickUnionUFTest {
    public static void main(String[] args) {
        QuickUnionUF qu = new QuickUnionUF(10);

        qu.print("id");
        qu.print("sz");

        qu.union(4,3);
        qu.union(3,8);
        qu.union(6,5);
        qu.union(9,4);
        qu.union(2,1);
        qu.union(5,0);
        qu.union(7,2);
        qu.union(6,1);
        qu.union(7,3);

        System.out.println(qu.connected(0,0) == true);
        System.out.println(qu.connected(2,9) == true);
        System.out.println(qu.connected(3,4) == true);
        System.out.println(qu.connected(3,9) == true);
        System.out.println(qu.connected(2,3) == true);

        qu.print("id");
        qu.print("sz");

    }
}
