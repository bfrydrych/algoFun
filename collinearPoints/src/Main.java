import java.util.Timer;

public class Main {

    public static void main(String[] args) {
	// write your code here
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
