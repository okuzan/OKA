package PW11;

import PW3.Bag;

public class Graph {

    private final int V;
    private Bag<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public void addEdge(int v, int w) {
        adj[w].add(v);
        adj[v].add(w);
    }

    public int degree(int v) {
        int degree = 0;
        for (int w : adj(v))
            degree++;
        return degree;
    }

    public int getV() {
        return V;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

}
