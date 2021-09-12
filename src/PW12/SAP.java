package PW12;

@SuppressWarnings("Duplicates")
public class SAP {

    private Digraph graph;
    private int INF = Integer.MAX_VALUE;


    SAP(Digraph G) {
        if (G == null)
            throw new NullPointerException();
        graph = G;
    }

    // довжина найкоротшого шляху до спільного батька v та w
    public int length(int v, int w) {
        checkbounds(v, w);
        BFDP bfs1 = new BFDP(graph, v);
        BFDP bfs2 = new BFDP(graph, w);
        return calc(bfs1, bfs2, false);
    }

    // спільний батько v та w,  з найкоротшого шляху
    public int ancestor(int v, int w) {
        checkbounds(v, w);
        BFDP bfs1 = new BFDP(graph, v);
        BFDP bfs2 = new BFDP(graph, w);
        return calc(bfs1, bfs2, true);
    }


    // довжина найкоротшого шляху між будь-якою вершиною з v та з w; -1 якщо такого шляху немає
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkboundsIter(v, w);
        BFDP bfs1 = new BFDP(graph, v);
        BFDP bfs2 = new BFDP(graph, w);
        return calc(bfs1, bfs2, false);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BFDP bfs1 = new BFDP(graph, v);
        BFDP bfs2 = new BFDP(graph, w);
        return calc(bfs1, bfs2, true);
    }

    private int calc(BFDP bfs1, BFDP bfs2, boolean choose) {
        int length = INF, ancestor = 0;
        for (int i = 0, temp; i < graph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                temp = bfs1.distTo(i) + bfs2.distTo(i);
                if (temp < length) {
                    length = temp;
                    ancestor = i;
                }
            }
        }
        if (length == INF) return -1;
        return choose ? ancestor : length;
    }

    private void checkbounds(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("!");
    }

    private void checkboundsIter(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new java.lang.NullPointerException("!");
        for (int vertex : v) if (vertex < 0 || vertex > graph.V()) throw new java.lang.IndexOutOfBoundsException("!");
        for (int vertex : w) if (vertex < 0 || vertex > graph.V()) throw new java.lang.IndexOutOfBoundsException("!");
    }


    public static void main(String[] args) {

    }
}
