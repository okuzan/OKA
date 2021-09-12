package PW2;

public class Tester {

    public static void main(String[] args) {
        for (int i = 100; i < 1000; i += 100) {
            long a = System.nanoTime();
//            TimingNew.trial(i,777280);
            Timing.trial(i, 777280);

        }
    }
}
