package PW2;

import java.util.Random;

@SuppressWarnings("Duplicates")
public class Timing {


    private static long powerLaw(int paramInt1, int paramInt2, double paramDouble) {
//        System.out.println("paramInt1 " + paramInt1);
//        System.out.println("paramInt2 " + paramInt2);

        long l1 = 0L;
        for (long l2 = 0L; l2 < Math.pow(paramInt1, 2); l2 += 1L) {
            l1 += 3;
        }
        return l1;
    }

    public static void trial(int paramInt, long paramLong) {
        long a = System.nanoTime();
        if (paramInt <= 0) throw new IllegalArgumentException("N must be a positive integer");
        if (paramLong <= 0L) throw new IllegalArgumentException("seed must be a positive integer");

        Random localRandom = new Random(paramLong);
        int myCounter = 0;

        int i = 1 + localRandom.nextInt(20);
        double d1 = 1.2D + 2.1D * localRandom.nextDouble();
        int j = 15;
        int k = 0;
        int l = 1;
        int i1 = 1;
        int i2 = 0;
        int i3 = k;
        int i4 = l;
        double d2 = Math.abs(d1);
        int i5;
        int i6;
        do {
            i5 = k + i1;
            i6 = l + i2;
            double d3 = i5 / i6;
            if (d1 < d3) {
                i1 = i5;
                i2 = i6;
            } else {
                k = i5;
                l = i6;
            }

            double d4 = Math.abs(d3 - d1);
            if (d4 < d2) {
                i3 = i5;
                i4 = i6;
                d2 = d4;
            }

//            System.out.println("-----------");
//            System.out.println("i1 " + i1);
//            System.out.println("i2 " + i2);
//            System.out.println("i5 " + i5);
//            System.out.println("i6 " + i6);
//            System.out.println("k  " + k);
//            System.out.println("l  " + l);
//            System.out.println("-----------");
//            myCounter++;
        }
        while (l + i2 <= j);

        d1 = i5 / i6;
//        System.out.println("d1 " + d1);
//        System.out.println("CyclesDir: " + myCounter);

        double constTime = System.nanoTime() - a;
        System.out.println("Constant time waste in trial " +constTime/Math.pow(10, 9));
        double b = System.nanoTime();
        long l1 = powerLaw(paramInt, 3, 2);
        double varyTime = System.nanoTime() - b;
//            System.out.println("TIME: " + (System.nanoTime() - a)/Math.pow(10, 9));

        System.out.println("VARY TIME: " + varyTime);
        System.out.println("varyTime.sqrt: " + Math.sqrt(varyTime)+ ", parameter: " + paramInt+ " | k = " + (i/Math.sqrt(varyTime)));
        System.out.println("_______________");
    }

    public static void main(String[] paramArrayOfString) {
        int i = 0;
        int j = 0;

        if (paramArrayOfString.length != 2) {
            System.out.println("You must supply two postive integer command-line arguments: N and seed");
            return;
        }
        try {
            i = Integer.parseInt(paramArrayOfString[0]);
            j = Integer.parseInt(paramArrayOfString[1]);
        } catch (NumberFormatException localNumberFormatException) {
            System.out.println("You must supply two postive integer command-line arguments: N and seed");
            return;
        }
        trial(i, j);
    }
}