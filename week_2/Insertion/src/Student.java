import java.util.Comparator;

/**
 * Created by thomaspan on 1/11/17.
 */
public class Student {
    public static final Comparator<Student> BY_NAME = new ByName();
    public static final Comparator<Student> BY_SECTION = new BySection();
    private final String name;
    private final int section;

    public Student(String name, int section) {
        this.name = name;
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public int getSection() {
        return section;
    }

    private static class ByName implements Comparator<Student> {
        public int compare(Student v , Student w) {
            return v.name.compareTo(w.name);
        }
    }

    private static class BySection implements Comparator<Student> {
        public int compare(Student v, Student w) {
            return v.section - w.section;
        }
    }
}
