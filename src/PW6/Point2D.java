package PW6;

import java.util.Comparator;
import edu.princeton.cs.introcs.StdDraw;

@SuppressWarnings("Duplicates")
public class Point2D implements Comparable<Point2D> {

    public  int id;
    private final int x;
    private final int y;

    public Point2D(int x, int y, int id) {

        this.id = id;
        this.x = x;
        this.y = y;
    }


    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point2D that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static int ccw(Point2D a, Point2D b, Point2D c) {

        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0) return -1;
        if (area2 > 0) return 1;
        return 0;
    }

    public Comparator<Point2D> polarOrder() {
        return new PolarOrder();
    }

    @Override
    public int compareTo(Point2D that) {
        if (this.y < that.y) return -1;
        if (this.y == that.y && this.x < that.x) return -1;
        if (this.x == that.x && this.y == that.y) return 0;
        return 1;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;

        if (obj.getClass() != this.getClass()) return false;
        Point2D that = (Point2D) obj;

        return (this.x == that.x && this.y == that.y);
    }

    private class PolarOrder implements Comparator<Point2D> {

        @Override
        public int compare(Point2D q1, Point2D q2) {

            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;

            if (dy1 >= 0 && dy2 < 0) return -1;    // q1 higher
            if (dy2 >= 0 && dy1 < 0) return +1;    // q1 lower
            if (dy1 == 0 && dy2 == 0) {            // if equal compare by x
                if (dx1 >= 0 && dx2 < 0) return -1;
                if (dx2 >= 0 && dx1 < 0) return +1;
                return 0;
            } else return -ccw(Point2D.this, q1, q2);     // both above or below

        }
    }
}