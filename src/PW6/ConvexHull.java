package PW6;

import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class ConvexHull {

    public ConvexHull(Point2D[] points) {

        //draw pivot
        Point2D p = points[0];
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();

        //draw lines
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GRAY);

        for (int i = 0; i < points.length; i++) {
            p.drawTo(points[i]);
            System.out.println(points[i].id);
            StdDraw.show();
//            StdDraw.pause(700);
        }
        StdDraw.pause(100);

        ArrayList<Point2D> hull = new ArrayList<>();

        //initial
        hull.add(points[0]);
        System.out.println("Added: " + hull.get(hull.size() - 1).id);
        hull.add(points[1]);
        System.out.println("Added: " + hull.get(hull.size() - 1).id);

        StdDraw.setPenColor(StdDraw.BOOK_RED);

        Point2D[] newPoints = new Point2D[points.length + 1];

        System.arraycopy(points, 0, newPoints, 0, points.length);
        newPoints[newPoints.length - 1] = newPoints[0];

        for (int i = 0; i < newPoints.length - 2; i++) {
            int n = hull.size() - 1;

            if (newPoints[0].ccw(hull.get(n - 1), hull.get(n), newPoints[i + 2]) >= 0) {
                System.out.println(hull.get(n - 1).id + " & " + hull.get(n).id + " & " + newPoints[i + 2].id + " : true");
                hull.add(newPoints[i + 2]);
                System.out.println("Added: " + hull.get(hull.size() - 1).id);
//
                n = hull.size() - 1;
                hull.get(n).drawTo(hull.get(n - 1));
                StdDraw.show();
                StdDraw.pause(200);
//
            } else {
                System.out.println(newPoints[i].id + " & " + newPoints[i + 1].id + " & " + newPoints[i + 2].id + " : false");
                n = hull.size() - 1;
                System.out.println(" n: " + n);
                hull.add(newPoints[i + 2]);
                System.out.println("Added: " + hull.get(hull.size() - 1).id);
                System.out.println(hull.get(n).id + " removed");
                hull.remove(n);
                hull.get(n).drawTo(hull.get(n - 1));
                StdDraw.show();
                StdDraw.pause(200);

                try {
                    n = hull.size() - 1;
                    while (newPoints[0].ccw(hull.get(n), hull.get(n - 1), hull.get(n - 2)) >= 0) {
                        System.out.println(hull.get(n).id + " & " + hull.get(n - 1).id + " & " + hull.get(n - 2).id + " : false");
                        System.out.println(hull.get(n - 1).id + " removed");
                        hull.remove(n - 1);
                        n = hull.size() - 1;
                    }
                } catch (Exception e) {
                    System.out.println("!");
                }
            }
        }

        //TODO for last few

        hull.get(0).drawTo(hull.get(1));
        StdDraw.show();

        for (int i = 0; i < hull.size() - 1; i++) {
            StdDraw.setPenColor(StdDraw.YELLOW);
            hull.get(i).drawTo(hull.get(i + 1));
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public static void main(String[] args) {
//
        File file = new File("./Data/input8.txt");
        Scanner scanner;

        try {
            scanner = new Scanner(file);
            int size = scanner.nextInt();
            Point2D[] points = new Point2D[size];
            for (int i = 0; i < size; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Point2D point = new Point2D(x, y, 0);
                points[i] = point;
                System.out.println("MyPoint " + (i + 1) + ": " + point);
            }

            //SORTING step
            Arrays.sort(points);
            Arrays.sort(points, points[0].polarOrder());

            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.setPenRadius(0.01);

            int cc = 0;
            for (Point2D p : points) {
                p.draw();
                p.id = cc;
                cc++;
            }

            StdDraw.show();

            //starting algorithm
            ConvexHull convexHull = new ConvexHull(points);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
