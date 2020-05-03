import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int nrSeg = 0;
    public BruteCollinearPoints(Point[] pointsInput) { // finds all line segments containing 4 points
        if (pointsInput == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = new Point[pointsInput.length];
        for (int i = 0; i < pointsInput.length; ++i) {
            if (pointsInput[i] == null) {
                throw new IllegalArgumentException();
            }
            points[i] = pointsInput[i];
        }
        //DEBUG
        //printSuppliedPoints(points);
        LineSegment[] segmentsTmp = new LineSegment[points.length];
        for (int i = 0; i < points.length; ++i) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int k = j+1; k < points.length; ++k) {
                    if (points[i].compareTo(points[k]) == 0 || points[j].compareTo(points[k]) == 0) {
                        throw new IllegalArgumentException();
                    }
                    for (int n = k+1; n < points.length; ++n) {
                        if (points[n].compareTo(points[i]) == 0 || points[n].compareTo(points[j]) == 0 || points[n].compareTo(points[k]) == 0) {
                            throw new IllegalArgumentException();
                        }
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[n];
                        double firstSlope = p1.slopeTo(p2);
                        if (firstSlope == p1.slopeTo(p3) && firstSlope == p1.slopeTo(p4)) {
                            Point[] segment = new Point[4];
                            segment[0] = p1;
                            segment[1] = p2;
                            segment[2] = p3;
                            segment[3] = p4;
                            Arrays.sort(segment);
                            //System.out.println("Found segment: " + segment[0] + segment[1] + segment[2] + segment[3]);
                            LineSegment seg = new LineSegment(segment[0], segment[3]);
                            segmentsTmp[nrSeg++] = seg;
                        }
                    }
                }
            }
        }
        segments = new LineSegment[nrSeg];
        System.arraycopy(segmentsTmp, 0, segments, 0, nrSeg);
    }

    private void printSuppliedPoints(Point[] points) {
        String pointsSupplied = "{";
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            pointsSupplied = pointsSupplied + "new Point" + points[i] + ",";
        }
        pointsSupplied = pointsSupplied + "};";
        System.out.println(pointsSupplied);
    }

    public int numberOfSegments() { // the number of line segments
        return nrSeg;
    }

    public LineSegment[] segments() {// the line segments
        LineSegment[] toReturn = new LineSegment[segments.length];
        System.arraycopy(segments, 0, toReturn, 0, segments.length);
        return toReturn;
    }
}
