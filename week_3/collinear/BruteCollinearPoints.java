import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    /**
     * Finds all line segments containing 4 points.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        int pointsLength = points.length;
        Point[] myPoints = Arrays.copyOf(points, pointsLength);
        Arrays.sort(myPoints);

        for (int i = 0; i < pointsLength; i++) {
            if (myPoints[i] == null) {
                throw new NullPointerException();
            }
            if (i != 0) {
                if (myPoints[i].compareTo(myPoints[i-1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.segments = new ArrayList<>();

        for (int i = 0; i < pointsLength; i++) {
            for (int j = i+1; j < pointsLength; j++) {
                for (int k = j+1; k < pointsLength; k++) {
                    for (int l = k+1; l < pointsLength; l++) {
                        double slope1 = myPoints[i].slopeTo(myPoints[j]);
                        double slope2 = myPoints[j].slopeTo(myPoints[k]);
                        if (Double.compare(slope2, slope1) == 0) {
                            double slope3 = myPoints[k].slopeTo(myPoints[l]);
                            if (Double.compare(slope2, slope3) == 0) {
                                LineSegment ls = new LineSegment(myPoints[i], myPoints[l]);
                                segments.add(ls);
                            }
                        }

                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}