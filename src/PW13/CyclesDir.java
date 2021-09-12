package PW13;

import PW3.Queue;
import PW3.Stack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class CyclesDir {

    private Digraph graph;
    private DFS dfs;
    private Degrees degrees;

    private CyclesDir(Digraph graph) {
        this.graph = graph;
        degrees = new Degrees(graph);
        dfs = new DFS(graph, 0);
    }

    private void runEulerian() {
        System.out.println("---Euler---");
        boolean isEulerian = degrees.compareInOut() && isConnected();
        if (isEulerian) {
            Queue<Integer> q = (Queue<Integer>) hierholzer();
            for (Integer integer : q) System.out.print(integer + " <- ");
            System.out.print("\b\b\b\b\n");
        } else {
            System.out.println("N/A");
        }
    }

    private boolean isConnected() {
        return dfs.connected();
    }

    private Iterable<Integer> hierholzer() {
        Stack<Integer> curPath = new Stack<>();
        Queue<Integer> sequence = new Queue<>();

        curPath.push(0);
        int curV = 0;

        while (!curPath.isEmpty()) {

            if (graph.adj(curV).size() != 0) {
                int nextV = graph.adj(curV).get(0);
                curPath.push(curV);
                graph.delEdge(curV, nextV);
                curV = nextV;

            } else {
                sequence.enqueue(curV);
                curV = curPath.pop();
            }
        }
        return sequence;
    }

    private void runHamilton() {

        System.out.println("---Hamilton---");
        if (!checkHamilton()) {
            System.out.println("!N/A");
//            return;
        }

        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<String> deadMap = new ArrayList<>();

        int cur = 0;
        path.add(cur);

        while (path.size() != graph.V()) {
            ArrayList kids = graph.adj(cur);
            Collections.sort(kids);

            boolean found = false;
            for (Object kid : kids) {
                int cand = (int) kid;

                if (!deadMap.contains(listStr(path, cand)) && !path.contains(cand) && graph.adj(cur).contains(cand)) {
                    path.add(cand);
                    cur = cand;
                    found = true;
                    break;
                }
            }

            if (!found) {
                deadMap.add(listStr(path));
                path.remove((Integer) cur);
                if(path.size() == 0){
                    System.out.println("N/A");
                    return;
                }
                cur = path.get(path.size() - 1);
            }
        }
        System.out.println(Arrays.toString(path.toArray()));
    }

    private String listStr(ArrayList<Integer> path) {
        StringBuilder res = new StringBuilder();
        for (Integer i : path) res.append(i);
        return res.toString();
    }

    private String listStr(ArrayList<Integer> path, int cand) {
        StringBuilder res = new StringBuilder();
        for (Integer i : path) res.append(i);
        res.append(cand);
        return res.toString();
    }

    void topologicalSortUtil(int v, boolean[] visited, ArrayList stack) {
        visited[v] = true;
        Integer i;

        for (Integer integer : graph.adj(v)) {
            i = integer;
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        stack.add(v);
    }

    void topologicalSort() {
        ArrayList al = new ArrayList<>();

        boolean[] visited = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++)
            visited[i] = false;

        for (int i = 0; i < graph.V(); i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, al);

        for (int i = 0; i < al.size(); i++) {
            System.out.print(i + " - ");
        }
    }

    private boolean checkHamilton() {
        topologicalSort();
        ArrayList<Integer> sorted = dfs.getSorted();
        System.out.println(Arrays.toString(sorted.toArray()));
        for (int i = 0; i < sorted.size() - 1; i++)
            if (graph.adj(sorted.get(i + 1)).contains(sorted.get(i)))
                return false;
        return true;
    }

    private static Digraph readDirGraph(String filename) {

        Digraph graph = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader("data/Graphs/" + filename + ".txt"));
            int size = Integer.parseInt(br.readLine());
            graph = new Digraph(size);
            String line;

            while ((line = br.readLine()) != null) {
                int[] peaks = Stream.of(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                for (int i = 1; i < peaks.length; i++) graph.addEdge(peaks[0], peaks[i]);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String filename = scan.next();
        scan.close();
        CyclesDir cycles = new CyclesDir(readDirGraph(filename));
        cycles.runEulerian();
        cycles.runHamilton();
    }
}