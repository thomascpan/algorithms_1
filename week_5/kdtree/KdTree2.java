import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree2 {
    private Node root;
    private final static boolean VERTICAL = true;
    private int size;

    private class Node() {
        private Point2d p;
        private RectHV r;
        private Node left;
        private Node right;
        private boolean o = true;

        private Node(Point2d p, boolean o) {
            this.p = p;
            this.o = o;
        }
    }

    /**
     * Construct an empty set of points.
     */
    public KdTree2() {
    }

    /**
     * Is the set empty?
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Number of points in the set.
     */
    public int size() {
        return size;
    }

    /**
     * Add the point p to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            root = new Node(p, VERTICAL);
            size++;
            root.r = new RectHV(0, 0, 1, 1);
        } else {
            root = put(root, p, VERTICAL);
        }
    }

    private Node put(Node x, Point2D p, boolean orientation) {
        if (x == null) {
            size++;
            return new Node(p, !orientation);
        }

        if (x.p.equals(p)) {
            return x;
        }

        int cmp = compare(p, x.p, x);
        if (cmp < 0) {
            x.left = put(x.left, p, x.o);
            if (x.left.r == null) {
                if (isVertical(x)) {
                    x.left.r = new RectHV(x.r.xmin(), x.r.ymin(),
                            x.r.xmax(), x.p.y());
                } else {
                    x.left.r = new RectHV(x.r.xmin(), x.r.ymin(),
                            x.p.x(), x.r.ymax());
                }
            }
        } else {
            x.right = put(x.right, p, x.orientation);
            if (x.right.r == null) {
                if (isVertical(x)) {
                    x.right.r = new RectHV(x.r.xmin(), x.p.y(),
                            x.r.xmax(), x.r.ymax());
                } else {
                    x.rightt.r = new RectHV(x.p.x(), x.r.ymin(),
                            x.r.xmax(), x.r.ymax());
                }
            }
        }
        return x;
    }

    private int compare(Point2D p, Point2D q, Node x) {
        if (isVertical(x)) {
            return Double.compare(p.x(), q.x());
        } else {
            return Double.compare(p.y(), q.y());
        }
    }

    /**
     * Does the set contain the point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        int cmp = compare(p, x.p, x);
        if (cmp < 0) {
            return contains(x.left, p);
        } else {
            return contains(x.right, p);
        }
    }

    /**
     * Draw all of the points to standard draw.
     */
    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        if (isVertical(x)) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.r.xmin(), x.p.y(), x.r.xmax(), x.p.y());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.p.x(), x.r.ymin(), x.p.x(), x.r.ymax());
        }
        draw(x.left);
        draw(x.right);
    }

    /**
     * All points in the set that are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV r) {
        if (r == null) {
            throw new NullPointerException();
        }

        Queue<Point2D> queue = new Queue<Point2D>();

        if (!isEmpty()) {
            findPoints(queue, r, root);
        }
        return queue;
    }

    private void findPoints(Queue<Point2D> queue, RectHV r, Node x) {
        if (!r.intersects(x.r)) {
            return;
        }
        if (r.contains(x.p)) {
            queue.enqueue(x.p);
        }
        if (x.left != null) {
            findPoints(queue, r, x.left);
        }
        if (x.right != null) {
            findPoints(queue, r, x.right);
        }
    }

    /**
     * A nearest neighbor in the set to p; null if set is empty.
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
    }

    private Point2D findNearest(Node x, Point2D p, Point2D nearest, double nearestDistance) {
        if (p == null || nearest == null) {
            throw new NullPointerException();
        }

        if (x == null) {
            return nearest;
        }
        Point2D closest = nearest;
        double closestDistance = nearestDistance;
        double distance = x.p.distanceSquaredTo(p);
        if (distance < nearestDistance) {
            closest = x.p;
            closestDistance = distance;
        }
        Node first, second;
        if (isVertical(x)) {
            if (p.y() < x.p.y()) {
                first = x.left;
                second = x.right;
            } else {
                first = x.right;
                second = x.left;
            }
        } else {
            if (p.x() < x.p.x()) {
                first = x.left;
                second = x.right;
            } else {
                first = x.right;
                second = x.left;
            }
        }
        if (first != null && first.r.distanceSquaredTo(p) < closestDistance) {
            closest = findNearest(first, p, closest, closestDistance);
            closestDistance = closest.distanceSquaredTo(p);
        }
        if (second != null
                && second.r.distanceSquaredTo(p) < closestDistance) {
            closest = findNearest(second, p, closest, closestDistance);
        }

        return closest;
    }

    private boolean isVertical(Node x) {
        return x.direction == VERTICAL;
    }
}