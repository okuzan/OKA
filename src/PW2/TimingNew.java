package PW2;

import java.util.Random;

@SuppressWarnings("Duplicates")
public class TimingNew {


    private static long constant(int paramInt) {
        long l = 0L;
        for (int i = 0; i < paramInt; ++i)
            l += 1L;
        return l;
    }

    private static long logarithmic(int paramInt1, int paramInt2) {
        long l = 0L;
        for (int i = 1; i + i <= paramInt1; i += i)
            l += constant(paramInt2);
        return l;
    }

    private static long sqrt(int paramInt1, int paramInt2) {
        long l = 0L;
        int i = 0; for (int j = 0; j < paramInt1; ++i) {
            l += constant(paramInt2);
            j += i;
        }
        return l;
    }

    public static long linearithmic(int paramInt1, int paramInt2) {
        if (paramInt1 == 0) return 0L;
        long l = 0L;
        for (int i = 0; i < paramInt1; ++i)
            l += constant(paramInt2);
        return (linearithmic(paramInt1 / 2, paramInt2) + l + linearithmic(paramInt1 / 2, paramInt2));
    }

    private static long linear(int paramInt1, int paramInt2) {
        long l = 0L;
        for (int i = 0; i < paramInt1; ++i)
            l += constant(paramInt2);
        return l;
    }

    private static long linearsqrt(int paramInt1, int paramInt2) {
        long l = 0L;
        for (int i = 0; i < paramInt1; ++i) {
            l += sqrt(paramInt1, paramInt2);
        }
        return l;
    }

    private static long quadratic(int paramInt1, int paramInt2) {
        long l = 0L;
        for (int i = 0; i < paramInt1; ++i)
            for (int j = 0; j < paramInt1; ++j)
                l += constant(paramInt2);
        return l;
    }

    private static long exponential(int paramInt1, int paramInt2) {
        if (paramInt1 == 0) return constant(paramInt2);
        return (exponential(paramInt1 - 1, paramInt2) + exponential(paramInt1 - 1, paramInt2));
    }

    private static long factorial(int paramInt1, int paramInt2) {
        if (paramInt1 == 0) return constant(paramInt2);
        long l = 0L;
        for (int i = 0; i < paramInt1; ++i)
            l += factorial(paramInt1 - 1, paramInt2);
        return l;
    }

    private static long divideAndConquer(int paramInt1, int paramInt2, int paramInt3, int paramInt4){
        if (paramInt1 == 0) return 0L;
        long l = constant(paramInt2);
        for (int i = 0; i < paramInt3; ++i) {
            l += divideAndConquer(paramInt1 / paramInt4, paramInt2, paramInt3, paramInt4);
        }
        return l;
    }

    private static long powerLaw(int paramInt1, int paramInt2, double paramDouble){
        long l1 = 0L;
        for (long l2 = 0L; l2 < Math.pow(paramInt1, paramDouble); l2 += 1L) {
            l1 += constant(paramInt2);
        }
        return l1;
    }

    public static void trial(int paramInt, long paramLong) {
        if (paramInt <= 0) throw new IllegalArgumentException("N must be a positive integer");
        if (paramLong <= 0L) throw new IllegalArgumentException("seed must be a positive integer");

        Random localRandom = new Random(paramLong);

        double d1 = 1.2D + 2.1D * localRandom.nextDouble();

        int k = 0; int l = 1;
        int i1 = 1; int i2 = 0;
        int i5;
        int i6;

        do{
            i5 = k + i1; //1  //2
            i6 = l + i2; //1  //1

            double d3 = i5 / i6; //1 //2

            if (d1 < (i5 / i6)) { //not first time // 2 - mb
                i1 = i5; i2 = i6;  } //if yes: i1 = 2 , i2 = 1;

            else {
                k = i5; l = i6; //k = k + i1 = k++ >>> k = 1, l = l + 0   1 1 //
            }

            double d4 = Math.abs(d3 - d1); //i5 / i6 - d1; //1 - d1;

            //need to check last iteration
            if (d4 < d1) {
                d1 = d4;//changing
            }
        }
        while (l + i2 <= 15); //1 + i2 <= 15, i2 <= 14; 0 <= 14

        d1 = i5 / i6;
        long l1 = powerLaw(paramInt, localRandom.nextInt(21), 2);
    }

    public static void main(String[] paramArrayOfString){
        int i = 0;
        int j = 0;

        if (paramArrayOfString.length != 2) {
            System.out.println("You must supply two postive integer command-line arguments: N and seed");
            return;
        }
        try {
            i = Integer.parseInt(paramArrayOfString[0]);
            j = Integer.parseInt(paramArrayOfString[1]);
        }catch (NumberFormatException localNumberFormatException) {
            System.out.println("You must supply two postive integer command-line arguments: N and seed");
            return;
        }

        trial(i, j);
    }
}

