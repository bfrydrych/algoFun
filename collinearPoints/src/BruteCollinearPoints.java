
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int nrSeg = 0;
    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException();
        }
        segments = new LineSegment[points.length];
        for (int i = 0; i < points.length; ++i) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; ++k) {
                    for (int n = k+1; n < points.length; ++n) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[n];
                        if (p1 == null || p2 == null || p3 == null || p4 == null) {
                            throw new IllegalArgumentException();
                        }
                        if (p1.compareTo(p2) == 0 || p1.compareTo(p3) == 0 || p1.compareTo(p4) == 0 || p2.compareTo(p3) == 0 || p2.compareTo(p4) == 0 || p3.compareTo(p4) == 0) {
                            throw new IllegalArgumentException();
                        }
                        double firstSlope = p1.slopeTo(p2);
                        if (firstSlope == p1.slopeTo(p3) && firstSlope == p1.slopeTo(p4)) {
                            System.out.println("Found segment: " + p1 + p2 + p3 + p4);
                            LineSegment seg = new LineSegment(p1, p4);
                            segments[nrSeg++] = seg;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return nrSeg;
    }

    public LineSegment[] segments() {// the line segments
        return segments;
    }
}
