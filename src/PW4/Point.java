package PW4;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (that.x == this.x) return Double.POSITIVE_INFINITY;
        if (that.y == this.y) return 0;
        return ((double)(this.y - that.y)) / ((double)(this.x - that.x));
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y == that.y && this.x < that.x) return -1;
        if (this.x == that.x && this.y == that.y) return 0;
        return 1;
    }

    public Comparator <Point> slopeOrder() {
        return (po1, po2) -> {
            double slope1 = Point.this.slopeTo(po1);
            double slope2 = Point.this.slopeTo(po2);
            if (slope1 == slope2) return 0;
            if (slope1 < slope2) return -1;
            return 1;
        };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}