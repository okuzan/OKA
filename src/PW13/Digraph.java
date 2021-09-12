package PW13;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Digraph implements Cloneable {

    private final int V;
    private ArrayList<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public void delEdge(int v, Integer w) {
        adj[v].remove(w);
    }

    public int degree(int v) {
        return adj[v].size();
    }

    public ArrayList<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    @Override
    protected Digraph clone() throws CloneNotSupportedException {
        Digraph clone = (Digraph) super.clone();
        clone.adj = deepCopy(this.adj);
        return clone;
    }

    private ArrayList<Integer>[] deepCopy(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] newAdj = new ArrayList[adj.length];
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
        String res = "";
        for (ArrayList<Integer> integers : adj) {
            for (Integer integer : integers) {
                res += integer + ", ";
            }
            res += ("\b\b\n");
        }
        return res;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Digraph g = new Digraph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        Digraph c = g.clone();

        System.out.println(g.toString());
        System.out.println(c.toString());
    }
}
