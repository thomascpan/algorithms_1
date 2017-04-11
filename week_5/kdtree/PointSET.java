import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private final SET<Point2D> set = new SET<Point2D>();

    /**
     * Construct an empty set of points.
     */
    public PointSET() {
    }

    /**
     * Is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Number of points in the set.
     */
    public int size() {
        return set.size();
    }

    /**
     * Add the point p to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (!set.contains(p)) {
            set.add(p);
        }
    }

    /**
     * Does the set contain the point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        return set.contains(p);
    }

    /**
     * Draw all of the points to standard draw.
     */
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    /**
     * All points in the set that are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV r) {
        if (r == null) {
            throw new NullPointerException();
        }

        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D p : set) {
            if (r.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    /**
     * A nearest neighbor in the set to p; null if set is empty.
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        double distance = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D other : set) {
            double d = p.distanceTo(other);
            if (d < distance) {
                distance = d;
                nearest = other;
            }

        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}