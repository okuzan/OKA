package PW11;

import PW3.Queue;

@SuppressWarnings("Duplicates")
public class BreadthFirstPaths {

    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private final int s;

    public BreadthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.getV()];
        distTo = new int[G.getV()];
        marked = new boolean[G.getV()];
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;
        distTo[s] = 0;
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    q.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public int distTo(int v) { return distTo[v]; }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Queue<Integer> path = new Queue<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.enqueue(x);
        path.enqueue(s);
        return path;
    }
}
