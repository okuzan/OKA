package PW13;

import PW3.Queue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
class Degrees {

    private Object graph;

    private int[] degreeMap;

    private int[] outdegreeMap;
    private int[] indegreeMap;

    private Queue<Integer> sources, sinks;

    private int s;
    private int countOdd;

    Degrees(Digraph G) {
        s = G.V();
        outdegreeMap = new int[s];
        indegreeMap = new int[s];

        sources = new Queue<>();
        sinks = new Queue<>();

        graph = G;

        for (int i = 0; i < s; i++) {
            ArrayList<Integer> q = G.adj(i);
            outdegreeMap[i] += q.size();
            for (Integer vertex : q) indegreeMap[vertex]++;
        }

        for (int i = 0; i < s; i++) {
            if (indegreeMap[i] == 0)
                sources.enqueue(i);
            if (outdegreeMap[i] == 0)
                sinks.enqueue(i);
        }
    }

    Degrees(Graph G) {
        int s = G.V();
        degreeMap = new int[s];

        graph = G;

        for (int i = 0; i < s; i++) degreeMap[i] += G.degree(i);

        for (int i = 0; i < s; i++)
            if (degreeMap[i] % 2 == 1)
                countOdd++;

    }

    boolean compareInOut() {
        for (int i = 0; i < s; i++)
            if (indegreeMap[i] != outdegreeMap[i]) {
                System.out.println(indegreeMap[i] + " " + outdegreeMap[i]);
                return false;
            }

        return true;
    }

    int getCountOdd() {
        return countOdd;
    }

    int indegree(int v) {
        return indegreeMap[v];
    }

    int outdegree(int v) {
        return outdegreeMap[v];
    }

    int degree(int v) {
        return degreeMap[v];
    }

    Iterable<Integer> sources() {
        return sources;
    }

    Iterable<Integer> sinks() {
        return sinks;
    }

    //TODO
    boolean isMap() {
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
        Digraph dag = readDirGraph("degrees");
        Degrees deg = new Degrees(dag);

        System.out.println("Sinks");
        for (Integer sink : deg.sinks()) System.out.println(sink);

        System.out.println("Sources");
        for (Integer source : deg.sources()) System.out.println(source);

        System.out.println(deg.isMap());
    }
}
