package PW14;

import PW3.Bag;

public class DPWG {

    private final int V;
    private Bag<MyPoint>[] adj;

    DPWG(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public void addEdge(MyPoint v, MyPoint w) {
//        System.out.println("in " + v.getI() + " placed " + w.getI());
        adj[v.getI()].add(w);
    }

//    public void setWeight(int v, double value) {
//        weights[v] = value;
//    }

    public int degree(int v) {
        int degree = 0;
        for (MyPoint w : adj(v))
            degree++;
        return degree;
    }

    public Bag<MyPoint> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

}
