import javax.sound.sampled.Line;

public class LineSegment {

    private Point p;
    private Point q;

    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }        // constructs the line segment between points p and q

    public void draw() {
        q.drawTo(q);
    }
    // draws this line segment


    public String toString() {
        return "P: " + p.toString() + " Q: " + q.toString();
    }                    // string representation

    @Override
    public boolean equals(Object obj) {
        LineSegment ls =  (LineSegment) obj;
        return p.compareTo(ls.p) == 0 && q.compareTo(ls.q) == 0;
    }
}
