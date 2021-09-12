package PW4;

import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Brute {

    public Brute(Point[] points) {
        int size = points.length;
//        System.out.println(size);
//        for(MyPoint point : points) System.out.println(point);
        Arrays.sort(points);

        for (int i = 0; i < size - 3; i++) {
            for (int k = i + 1; k < size - 2; k++) {
                for (int j = k + 1; j < size - 1; j++) {
//                    System.out.println(i + " to " + k + ": " + points[i].slopeTo(points[k]) + ", " + i + " to " + j + ": " + points[i].slopeTo(points[j]));
                    if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[j])) {
//                        System.out.println("Skipped");
                        for (int l = j + 1; l < size; l++) {
//                        System.out.println("-------------");
//                            System.out.println("i: " + (i + 1) + " k: " + (k + 1) + " j: " + (j + 1) + " l: " + (l + 1));
//                        System.out.println(i + " to " + j + ": " + points[i].slopeTo(points[j]) + ", " + j + " to " + l + ": " + points[j].slopeTo(points[l]));
                            if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[l])) {
//                            System.out.println(" MATCH! ");
                                System.out.println(points[i] + " -> " + points[k] + " -> " + points[j] + " -> " + points[l]);
                                points[i].drawTo(points[l]);
                            }
                        }
                    }
                }
            }
            StdDraw.show();
        }
    }

        public static void main(String[] args) {
        //
        File file = new File("data/rs1423.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            int size = scanner.nextInt();
            Point[] points = new Point[size];
            for (int i = 0; i < size; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Point point = new Point(x, y);
                points[i] = point;
        //                System.out.println("MyPoint " + (i + 1) + ": " + point);
            }
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) p.draw();
            StdDraw.show();

            Brute brute = new Brute(points);
//          Fast fast = new Fast(points);
//
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        }

}
