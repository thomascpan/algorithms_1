import java.util.Comparator;

/**
 * Created by thomaspan on 1/6/17.
 */
public class Insertion {
    public static void sort(Object[] a, Comparator comparator) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(comparator, a[j], a[j-1]); j--) {
                exch(a, j, (j-1));
            }
        }
    }

    private static boolean less(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Student a = new Student("Bill", 3);
        Student b = new Student("Antonio", 2);
        Student c = new Student("Charlene", 1);

        Student[] students = {a,b,c};

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].getName());
        }

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].getSection());
        }

        Insertion.sort(students, Student.BY_NAME);

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].getName());
        }

        Insertion.sort(students, Student.BY_SECTION);

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].getSection());
        }

    }
}

