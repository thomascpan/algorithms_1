import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private Node root;
    private int size = 0;
    private final boolean VERTICAL = true;
    private final boolean HORIZONTAL = false;

    private static class Node {
        private Point2D p;
        private RectHV r;
        private Node lb;
        private Node rt;
        private boolean orientation;

        public Node(Point2D p, boolean o) {
            this.p = p;
            this.orientation = o;
        }
    }

    /**
     * Construct an empty set of points.
     */
    public KdTree() {
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
            root.r = new RectHV(0, 0, 1, 1);
            size++;
            return;
        }
        root = put(root, p, root.orientation);
    }

    private Node put(Node x, Point2D p, boolean orientation) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (x == null) {
            size++;
            return new Node(p, !orientation);
        }
        if (x.p.equals(p)) {
            return x;
        }
        int cmp = compare(p, x.p, x);
        if (cmp < 0) {
            x.lb = put(x.lb, p, x.orientation);
            if (x.lb.r == null) {
                if (isVertical(x)) {
                    x.lb.r = new RectHV(x.r.xmin(), x.r.ymin(),
                            x.r.xmax(), x.p.y());
                } else {
                    x.lb.r = new RectHV(x.r.xmin(), x.r.ymin(),
                            x.p.x(), x.r.ymax());
                }
            }
        } else {
            x.rt = put(x.rt, p, x.orientation);
            if (x.rt.r == null) {
                if (isVertical(x)) {
                    x.rt.r = new RectHV(x.r.xmin(), x.p.y(),
                            x.r.xmax(), x.r.ymax());
                } else {
                    x.rt.r = new RectHV(x.p.x(), x.r.ymin(),
                            x.r.xmax(), x.r.ymax());
                }
            }
        }
        return x;
    }

    private int compare(Point2D p, Point2D q, Node x) {
        if (p == null || q == null) {
            throw new NullPointerException();
        }

        if (isVertical(x)) {
            return Double.compare(p.y(), q.y());
        } else {
            return Double.compare(p.x(), q.x());
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
        if (p == null) {
            throw new NullPointerException();
        }

        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        int cmp = compare(p, x.p, x);
        if (cmp < 0) {
            return contains(x.lb, p);
        } else {
            return contains(x.rt, p);
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
        draw(x.lb);
        draw(x.rt);
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
        if (queue == null || r == null || x == null) {
            throw new NullPointerException();
        }

        if (!r.intersects(x.r)) {
            return;
        }
        if (r.contains(x.p)) {
            queue.enqueue(x.p);
        }
        if (x.lb != null) {
            findPoints(queue, r, x.lb);
        }
        if (x.rt != null) {
            findPoints(queue, r, x.rt);
        }
    }

    /**
     * A nearest neighbor in the set to p; null if set is empty.
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            return null;
        }
        return findNearest(root, p, root.p, Double.MAX_VALUE);
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
                first = x.lb;
                second = x.rt;
            } else {
                first = x.rt;
                second = x.lb;
            }
        } else {
            if (p.x() < x.p.x()) {
                first = x.lb;
                second = x.rt;
            } else {
                first = x.rt;
                second = x.lb;
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
        if (x == null) {
            return false;
        }
        return x.orientation == VERTICAL;
    }
}