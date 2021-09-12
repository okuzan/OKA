package PW7;

import edu.princeton.cs.introcs.StdDraw;
import sun.awt.SunToolkit;

public class BouncingBalls {

    public static void main(String[] args) {

        // number of bouncing balls
        int n = 10;

        // set boundary to box with coordinates between -1 and +1
        StdDraw.setXscale(-1.0, +1.0);
        StdDraw.setYscale(-1.0, +1.0);

        // create an array of n random balls
        Ball[] balls = new Ball[n];
        for (int i = 0; i < n; i++)
            balls[i] = new Ball();

        // do the animation loop
        StdDraw.enableDoubleBuffering();
        while (true) {

            // move the n balls
            for (int i = 0; i < n; i++) {
                balls[i].move();
            }

            // draw the n balls
            StdDraw.clear(StdDraw.WHITE);
            StdDraw.setPenColor(StdDraw.RED);
            for (int i = 0; i < n; i++) {
                balls[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}