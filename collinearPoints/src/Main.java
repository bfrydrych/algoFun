import javax.sound.sampled.Line;
import java.io.PipedOutputStream;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        testSlopeTo();
        testCompare();
        testBrute();
        testFast();
        //endToEndTest();
    }

    private static void testFast() {
        Point[] p1 = {new Point(10000, 0),new Point(0, 10000),new Point(3000, 7000),new Point(7000, 3000),new Point(20000, 21000),new Point(3000, 4000),new Point(14000, 15000),new Point(6000, 7000)};
        testFastSingle(p1, List.of(new LineSegment(new Point(10000, 0), new Point(0, 10000)), new LineSegment(new Point(3000, 4000), new Point(20000, 21000))));
    }

    private static void testFastSingle(Point[] points, List<LineSegment> expectedSegments) {
        FastCollinearPoints b = new FastCollinearPoints(points);

        if (b.numberOfSegments() != expectedSegments.size()) {
            throw new AssertionError("Expected nr segments: " + expectedSegments.size() + " Actual " + b.numberOfSegments());
        }

        LineSegment[] actualSegments = b.segments();
        for (int i = 0; i < expectedSegments.size(); ++i) {
            LineSegment actualSegment = actualSegments[i];
            LineSegment expectedSegment = expectedSegments.get(i);
            if (!actualSegment.equals(expectedSegment)) {
                throw new AssertionError("Expected segment: " + expectedSegment + " Actual segment: " + actualSegment);
            }

        }
        LineSegment expectedLS = new LineSegment(new Point(10000, 0), new Point(0, 10000));
        System.out.println("testFast passed");
    }

    private static void testSlopeTo() {
        testSlopeToSingle(new Point(11, 184), new Point(11, 67), Double.POSITIVE_INFINITY);
        testSlopeToSingle(new Point(306, 65), new Point(306, 65), Double.NEGATIVE_INFINITY);
        testSlopeToSingle(new Point(358, 454), new Point(150, 454), 0.0);
    }

    private static void testSlopeToSingle(Point p, Point q, Double expectedPToQSlope) {
        if (Double.compare(p.slopeTo(q), expectedPToQSlope) != 0) {
            throw new AssertionError("" + p + q + "- actual: " + p.slopeTo(q) + " - expected: " + expectedPToQSlope);
        }
        System.out.println("testSlopeTo passed");
    }

    private static void testCompare() {
        testCompareSingle(new Point(460, 145), new Point(76, 454), new Point(460, 438), -1);
        testCompareSingle(new Point(25790, 18140), new Point(25790, 22154), new Point(11584, 5344), 1);
        testCompareSingle(new Point(4, 0), new Point(6, 9), new Point(4, 6), -1);
    }

    private static void testCompareSingle(Point p, Point q, Point r, int expected) {
        Comparator<Point> comparator = p.slopeOrder();
        int actual = comparator.compare(q, r);
        if (actual != expected) {
            throw new AssertionError("" + p + q + "- actual: " + actual + " - expected: " + expected);
        }
        System.out.println("testCompareTo passed");
    }

    private static void testBrute() {
        Point[] points = {new Point(10000, 0),new Point(0, 10000),new Point(3000, 7000),new Point(7000, 3000),new Point(20000, 21000),new Point(3000, 4000),new Point(14000, 15000),new Point(6000, 7000)};
        BruteCollinearPoints b = new BruteCollinearPoints(points);

        if (b.numberOfSegments() != 2) {
            throw new AssertionError("Expected nr segments: " + 2 + " Actual " + b.numberOfSegments());
        }

        LineSegment lineSegment = b.segments()[0];
        LineSegment expectedLS = new LineSegment(new Point(10000, 0), new Point(0, 10000));
        if (!lineSegment.equals(expectedLS)) {
            throw new AssertionError("Expected segment: " + expectedLS + " Actual segment: " + lineSegment);
        }
        System.out.println("testBrute passed");
    }

    private static void endToEndTest() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p4 = new Point(4, 4);

        Point p9 = new Point(8, 5);

        Point p5 = new Point(9, 5);
        Point p6 = new Point(10, 5);
        Point p7 = new Point(11, 5);
        Point p8 = new Point(2, 3);

        Point[] p = new Point[9];


        System.out.println(p1.slopeTo(p6));

        p[0] = p1;
        p[1] = p2;
        p[2] = p3;
        p[3] = p4;
        p[4] = p5;
        p[5] = p6;
        p[6] = p7;
        p[7] = p8;
        p[8] = p9;

        System.out.println();
        System.out.println("Brute results");
        long start = System.nanoTime();
        BruteCollinearPoints b = new BruteCollinearPoints(p);
        long end = System.nanoTime();
        System.out.println("Execution took: " + (end - start));
        System.out.println(b.numberOfSegments());
        System.out.println(b.segments()[0].toString());
        System.out.println(b.segments()[1].toString());

        System.out.println();
        System.out.println("Fast results");
        long startF = System.nanoTime();
        FastCollinearPoints f = new FastCollinearPoints(p);
        long endF = System.nanoTime();
        System.out.println("Execution took: " + (endF - startF));
        System.out.println(f.numberOfSegments());
        System.out.println(f.segments()[0].toString());
        System.out.println(f.segments()[1].toString());
    }
}
