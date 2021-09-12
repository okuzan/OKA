package PW7;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class CollisionSystem {
    private static final double HZ = 0.5;    // number of redraw events per clock tick

    private MinPQ<Event> pq;          // the priority queue
    private double t = 0.0;          // simulation clock time
    private Particle[] particles;     // the array of particles

    private CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();   // defensive copy
    }

    // updates priority queue with all new events for particle a
    private void update(Particle a, double limit) {
        if (a == null) return;

        // particle-particle collisions
        for (Particle particle : particles) {
            double dt = a.timeToHit(particle);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particle));
        }

        // particle-wall collisions
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
    }

    private void redraw(double limit) {
        StdDraw.clear();
        for (Particle particle : particles) {
            particle.draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / HZ, null, null));
        }
    }


    /**
     * Simulates the system of particles for the specified amount of time.
     *
     * @param limit the amount of time
     */
    private void simulate(double limit) {

        // initialize PQ with collision events and redraw event
        pq = new MinPQ<>();
        for (Particle particle1 : particles) {
            update(particle1, limit);
        }
        pq.insert(new Event(0, null, null));        // redraw event


        // the main event-driven simulation loop
        while (!pq.isEmpty()) {

            // get impending event, discard if invalidated
            Event event = pq.delMin();
            if (!event.isValid()) continue;
            Particle a = event.a;
            Particle b = event.b;

            // physical collision, so update positions, and then simulation clock
            for (Particle particle : particles) particle.move(event.time - t);
            t = event.time;

            // process event
            if (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else redraw(limit);               // redraw event

            // update the priority queue with new collisions involving a or b
            update(a, limit);
            update(b, limit);
        }
    }

    private static class Event implements Comparable<Event> {
        private final double time;         // time that event is scheduled to occur
        private final Particle a, b;       // particles involved in event, possibly null
        private final int countA, countB;  // collision counts at event creation


        // create a new event to occur at time t involving a and b
        Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null) countA = a.count();
            else countA = -1;
            if (b != null) countB = b.count();
            else countB = -1;
        }

        // compare times when two events will occur
        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        // has any collision occurred between when event was created and now?
        boolean isValid() {
            if (a != null && a.count() != countA) return false;
            return b == null || b.count() == countB;
        }

    }

    public static void main(String[] args) {

        StdDraw.setCanvasSize(600, 600);

        // enable double buffering
        StdDraw.enableDoubleBuffering();

        // the array of particles
        Particle[] particles;

        // create n random particles
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        particles = new Particle[n];
        for (int i = 0; i < n; i++)
            particles[i] = new Particle();

        // create collision system and simulate
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000);
    }

}
