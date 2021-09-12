package PW5;

import java.util.Arrays;
import java.util.Random;

public class Test {

    private static Robot[] robots;

    public static void main(String[] args) {
        Robot r1 = new Robot("r1", "HH|123", 3.3, 33213);
        Robot r2 = new Robot("r2", "HH|133", 4.3, 43113);
        Robot r3 = new Robot("r3", "HH|431", 5.3, 12389);
        Robot r4 = new Robot("r4", "HH|431", 6.3, 19849);
        Robot r5 = new Robot("r5", "HH|104", 7.3, 23923);

        robots = new Robot[5];

        for (int i = 0; i < robots.length; i++) {
            int randIndex = randIndex(robots.length);
            System.out.println(randIndex);
            System.out.println("    i    "+i);
            robots[randIndex] = new Robot("r" + i, "HH|" + i + 89 + i, i + 0.3, i + 32134);
        }
        System.out.println(Arrays.toString(robots));
        Arrays.sort(robots, new Robot.byAge());
        System.out.println(Arrays.toString(robots));
        Arrays.sort(robots, new Robot.byID());
        System.out.println(Arrays.toString(robots));
        Arrays.sort(robots, new Robot.byCode());
        System.out.println(Arrays.toString(robots));
        Arrays.sort(robots, new Robot.byName());
        System.out.println(Arrays.toString(robots));

    }

    public static int randIndex(int x) {

        Random random = new Random(System.currentTimeMillis());
        int s = (int) (random.nextDouble() * x);
        boolean condition = (robots[s] == null);
        do {
            if (robots[s] == null) {
                System.out.println("TRuE");
                return s;
            }
            s = (int) (random.nextDouble() * x);
            System.out.println(" s " + s);
        } while (!condition);
        return 0;
    }
}
