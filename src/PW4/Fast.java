package PW4;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Arrays;

public class Fast {

    public Fast(Point[] points) {

        Point[] copyPoints = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(copyPoints, point.slopeOrder());
            double tmpSlope = point.slopeTo(copyPoints[0]);
            int count = 0;
            int i;
            for (i = 1; i < copyPoints.length; i++) {
                if (point.slopeTo(copyPoints[i]) == tmpSlope) {
                    count++;
                } else {
                    if (count > 1) {
                        point.drawTo(copyPoints[i - 1]);
                        System.out.println(point + " -> " + copyPoints[i - 3] + " -> " + copyPoints[i - 2] + " -> " + copyPoints[i - 1]);
                    }

                    tmpSlope = point.slopeTo(copyPoints[i]);
                    count = 0;
                }
            }
            if (count > 1) point.drawTo(copyPoints[i - 1]);
        }
        StdDraw.show();
    }
}
