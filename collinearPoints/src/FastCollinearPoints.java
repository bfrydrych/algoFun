import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int numOfSegments = 0;

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        lineSegments = new LineSegment[points.length * 4];
        int nextSegmentIndex = 0;

        for (int i = 0; i < points.length; ++i) {
            Point[] toCompare = new Point[points.length - i - 1];
            System.arraycopy(points, i + 1, toCompare, 0, points.length - (i + 1));
            Arrays.sort(toCompare, points[i].slopeOrder());

            // identify same slopes (line segments)
            double[] slopes = new double[toCompare.length];
            Point[] sameSlopes = new Point[toCompare.length];
            int sameSlopesIndex = 0;
            int sameSlopeNr = 0;
            for (int j = 0; j < toCompare.length; ++j) {
                // check for duplicates
                if (i == 0) {
                    if (points[i].compareTo(toCompare[j]) == 0) {
                        throw new IllegalArgumentException();
                    }
                }

                // and here we go
                double slope = points[i].slopeTo(toCompare[j]);
                slopes[j] = slope;
                // keep comparing slopes until not equal slope found
                if (j != 0 && slope == slopes[j -1]) {
                    sameSlopeNr++;
                    sameSlopes[sameSlopesIndex] = toCompare[j - 1];
                    sameSlopes[++sameSlopesIndex] = toCompare[j];
                } else {
                    // slope differs, point is not collinear
                    if (sameSlopeNr + 1 >= 3) {
                        // create a segment if same slopes found
                        LineSegment lineSegment = new LineSegment(points[i], sameSlopes[sameSlopesIndex]);
                        lineSegments[nextSegmentIndex++] = lineSegment;
                        numOfSegments++;
                    }
                    // reset same slopes list
                    sameSlopes = new Point[toCompare.length - j];
                    sameSlopesIndex = 0;
                    sameSlopeNr = 0;
                }
            }
        }

    }     // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return numOfSegments;
    }        // the number of line segments


    public LineSegment[] segments() {
        return lineSegments;
    }                // the line segments

    public static void main(String[] args) {
// read the n points from a file</font>
                In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

// draw the points</font>
                StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

// print and draw the line segments</font>
                FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
