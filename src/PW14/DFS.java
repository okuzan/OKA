package PW14;

import PW13.Digraph;
import PW13.Graph;
import PW3.Bag;
import PW3.Stack;

import java.util.ArrayList;
import java.util.InputMismatchException;

@SuppressWarnings("Duplicates")
public class DFS {

    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    private Stack<Integer> sorted;

    DFS(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    DFS(Digraph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        sorted = new Stack<>();
        dfs(G, s);
        sorted.push(s);
    }

    public DFS(DPWG G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        sorted = new Stack<>();
        dfs(G, s);
        sorted.push(s);
    }

    private void dfs(DPWG G, int v) {
        marked[v] = true;

        Bag<MyPoint> e = (Bag<MyPoint>) G.adj(v);
        for (MyPoint i : e) {
            if (!marked[i.getI()]) {
                edgeTo[i.getI()] = v;
                sorted.push(i.getI());
                dfs(G, i.getI());
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;

        ArrayList<Integer> e = G.adj(v);
        for (Integer i : e) {
            if (!marked[i]) {
                edgeTo[i] = v;
                dfs(G, i);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;

        ArrayList<Integer> e = G.adj(v);
        for (Integer i : e) {
            if (!marked[i]) {
                edgeTo[i] = v;
                sorted.push(i);
                dfs(G, i);
            }
        }
    }

    boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    boolean connected() {
        for (boolean b : marked)
            if (!b) return false;
        return true;
    }

    //TODO
    ArrayList<Integer> getSorted() {
        if (sorted == null) throw new InputMismatchException("You are dealing with graph, not digraph!");
        ArrayList<Integer> al = new ArrayList<>();
        while (!sorted.isEmpty())
            al.add(sorted.pop());
        return al;
    }
}
