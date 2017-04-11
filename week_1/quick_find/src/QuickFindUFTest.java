/**
 * Created by thomaspan on 12/7/16.
 */
public class QuickFindUFTest {
    public static void main(String[] args) {
        QuickFindUF qf = new QuickFindUF(10);

        qf.print();

        qf.union(4,3);
        qf.union(3,8);
        qf.union(6,5);
        qf.union(9,4);
        qf.union(2,1);

        System.out.println(qf.connected(8,9) == true);
        System.out.println(qf.connected(5,0) == false);

        qf.union(5,0);
        qf.union(7,2);
        qf.union(6,1);

        qf.print();
    }
}
