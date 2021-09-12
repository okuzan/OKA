package PW13;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Graph implements Cloneable {

    private final int V;
    private ArrayList[] adj;

    Graph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<>();
    }

    void addEdge(Integer v, Integer w) {
        if (!adj[v].contains(w))
            adj[v].add(w);
        if (!adj[w].contains(v))
            adj[w].add(v);
    }

    void delEdge(Integer v, Integer w) {
        adj[v].remove(w);
    }


    int degree(int v) {
        return adj[v].size();
    }

    public int V() {
        return V;
    }

    public ArrayList adj(int v) {
        return adj[v];
    }

    @Override
    protected Graph clone() throws CloneNotSupportedException {
        Graph clone = (Graph) super.clone();
        clone.adj = deepCopy(this.adj);
        return clone;
    }

    private ArrayList[] deepCopy(ArrayList<Integer>[] adj) {
        ArrayList[] newAdj = new ArrayList[adj.length];
        for (int i = 0; i < adj.length; i++) {
            newAdj[i] = new ArrayList<>();
            for (Integer integer : adj[i]) {
                newAdj[i].add(integer);
            }
        }
        return newAdj;
    }

    @Override
    public String toString() {
        return print(adj);
    }

    private String print(ArrayList<Integer>[] adj) {
        StringBuilder res = new StringBuilder();

        for (ArrayList<Integer> integers : adj) {
            for (Integer integer : integers)
                res.append(integer).append(", ");
            res.append("\b\b\n");
        }
        return res.toString();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        Graph c = g.clone();

        System.out.println(g.toString());
        System.out.println(c.toString());
    }
}
