    package PW11;

    import PW3.Stack;
    import edu.princeton.cs.introcs.In;
    import edu.princeton.cs.introcs.StdOut;

    @SuppressWarnings("Duplicates")
    public class DepthFirstPaths {

        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        public DepthFirstPaths(Graph G, int s) {
            this.s = s;
            edgeTo = new int[G.getV()];
            marked = new boolean[G.getV()];
            dfs(G, s);
        }

        private void dfs(Graph G, int v) {
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    dfs(G, w);
                }
            }
        }

        public boolean hasPathTo(int v) {
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

        private static final String testFile = "tinyCG.txt";

        public static void main(String[] args) {
            In in = new In(testFile);
            Graph G = new Graph(2);
            StdOut.println(G);
            int s = Integer.parseInt("0");
            DepthFirstPaths dfs = new DepthFirstPaths(G, s);

            for (int v = 0; v < G.getV(); v++) {
                if (dfs.hasPathTo(v)) {
                    for (int x : dfs.pathTo(v)) {
                        StdOut.printf("%d to %d:  ", s, v);
                        if (x == s) StdOut.print(x);
                        else        StdOut.print("-" + x);
                    }
                    StdOut.println();
                }

                else {
                    StdOut.printf("%d to %d:  not connected\n", s, v);
                }

            }
        }
    }
