import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    /**
     * Finds all line segments containing 4 points.
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        int pointsLength = points.length;
        Point[] myPoints = Arrays.copyOf(points, pointsLength);
        Arrays.sort(myPoints);

        for (int i = 0; i < pointsLength; i++) {
            if (myPoints[i] == null) {
                throw  new NullPointerException();
            }
            if (i != 0) {
                if (myPoints[i].compareTo(myPoints[i-1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.segments = new ArrayList<>();

        Point prevStart = null;
        Point prevEnd = null;

        for (int i = 0; i < pointsLength; i++) {
            Point[] localPoints = createPointsArray(myPoints, i);
            Arrays.sort(localPoints, myPoints[i].slopeOrder());

            for (int j = 0; j < localPoints.length; j++) {
                int lastMatch = isCollinear(localPoints, myPoints[i], j);
                if (lastMatch != -1) {
                    if (prevEnd != null && prevEnd.compareTo(localPoints[lastMatch]) == 0) {
                            if (i > 0 && i < points.length-1) {
                                double prevSlope = prevStart.slopeTo(prevEnd);
                                double currentSlope = myPoints[i].slopeTo(prevEnd);

                                if (Double.compare(prevSlope, currentSlope) == 0) {
                                    break;
                                }
                            }
                    }
                    prevStart = myPoints[i];
                    prevEnd = localPoints[lastMatch];
                    LineSegment ls = new LineSegment(myPoints[i], localPoints[lastMatch]);
                    this.segments.add(ls);
                    j = lastMatch;
                }
            }
        }
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }

    private int isCollinear(Point[] points, Point point, int start) {
        double slope = point.slopeTo(points[start]);
        int matches = 1;
        int index = start+matches;

        while (index < points.length) {
            double localSlope = point.slopeTo(points[index]);

            if (Double.compare(slope, localSlope) != 0) {
                break;
            } else {
                matches++;
                index++;
            }
        }
        index--;

        if (matches > 2) {
            return index;
        } else {
            return -1;
        }
    }

    private Point[] createPointsArray(Point[] a, int index) {
        return Arrays.copyOfRange(a, index+1, a.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}