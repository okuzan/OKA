package PW12;

import PW3.Bag;

public class Digraph {

    private final int V;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public int degree(int v) {
        int degree = 0;
        for (int w : adj(v))
            degree++;
        return degree;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }
}
