package PW7;

import edu.princeton.cs.introcs.StdDraw;

import java.io.Console;
import java.util.Scanner;

public class MyCollisionSystem {
    private static MinPQ<Event> pq; // the priority queue
    private double t = 0.0; // simulation clock time
    private Particle[] particles; // the array of particles

    public MyCollisionSystem(Particle[] particles) {
        this.particles = particles;
    }

    private  void predict(Particle a) {
        if (a == null) return;
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            pq.insert(new Event(t + dt, a, particles[i]));
        }
        pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
        pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));
    }

    private void redraw() {
        StdDraw.clear();

        for (int i = 0; i < particles.length; i++) particles[i].draw();

        StdDraw.show();
        StdDraw.pause(20);
        pq.insert(new Event(t, null, null));
    }


    private class Event implements Comparable<Event> {
        private double time; // time of event
        private Particle a, b; // particles involved in event
        private int countA, countB; // collision counts for a and b

        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
        }

        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        public boolean isValid() {
            //TODO
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }


    private  void simulate() {
        pq = new MinPQ<>();

        for (int i = 0; i < particles.length; i++) predict(particles[i]);

        pq.insert(new Event(0, null, null));

        while (!pq.isEmpty()) {

            Event event = pq.delMin();
            if (!event.isValid()) continue;

            Particle a = event.a;
            Particle b = event.b;

            for (int i = 0; i < particles.length; i++)
                particles[i].move(event.time - t);

            t = event.time;

            if (a != null && b != null) a.bounceOff(b);
            else if (a != null && b == null) a.bounceOffVerticalWall();
            else if (a == null && b != null) b.bounceOffHorizontalWall();
            else if (a == null && b == null) redraw();

            predict(a);
            predict(b);
        }
    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.enableDoubleBuffering();

        System.out.println("How many particles do you want to generate?");
        Scanner scan = new Scanner(System.in);

        int quantity = scan.nextInt();
        Particle[] particles = new Particle[quantity];
        scan.close();

        for (int i = 0; i < quantity; i++) particles[i] = new Particle();

        MyCollisionSystem system = new MyCollisionSystem(particles);
        system.simulate();
        StdDraw.show();
    }
}
