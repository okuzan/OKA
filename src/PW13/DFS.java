package PW13;

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
                dfs(G, i);
                sorted.push(i);
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
