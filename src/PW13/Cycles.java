package PW13;

import PW3.Queue;
import PW3.Stack;
import PW6.Point2D;
import edu.princeton.cs.introcs.StdDraw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

import static edu.princeton.cs.introcs.StdDraw.setPenRadius;

@SuppressWarnings("Duplicates")
public class Cycles {

    private Graph graph;
    private Degrees degrees;

    private Cycles(Graph graph) {
        this.graph = graph;
        degrees = new Degrees(graph);
    }

    private void runEuler() throws CloneNotSupportedException {
        System.out.println("---Euler---");
        boolean isEulerian = degrees.getCountOdd() == 0 && isConnected();
        if (isEulerian) {
            Queue<Integer> q = (Queue<Integer>) hierholzer(this.graph);
            for (Integer integer : q) System.out.print(integer + " -> ");
            System.out.print("\b\b\b\b\n");
        } else {
            System.out.println("N/A");
        }
    }

    private boolean isConnected() {
        DFS dfs = new DFS(graph, 0);
        return dfs.connected();
    }

    private Iterable<Integer> hierholzer(Graph gr) throws CloneNotSupportedException {
        Stack<Integer> curPath = new Stack<>();
        Queue<Integer> sequence = new Queue<>();
        Graph graphCopy = gr.clone();
        curPath.push(0);
        int curV = 0;

        while (!curPath.isEmpty()) {

            if (graphCopy.adj(curV).size() != 0) {
                int nextV = (int) graphCopy.adj(curV).get(0);
                curPath.push(curV);

                graphCopy.delEdge(curV, nextV);
                graphCopy.delEdge(nextV, curV);

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

    private static Graph readGraph(String filename) {
        Graph graph = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/Graphs/" + filename + ".txt"));
            int size = Integer.parseInt(br.readLine());
            graph = new Graph(size);
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

    private void showEuler() throws FileNotFoundException, CloneNotSupportedException {

        File file = new File("./Data/Graphs/eulerPoints.txt");
        Scanner scanner = new Scanner(file);
        int size = Integer.parseInt(scanner.nextLine());
        Point2D[] points = new Point2D[size];
        for (int i = 0; i < size; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Point2D point = new Point2D(x, y, 0);
            points[i] = point;
            System.out.println("MyPoint " + (i + 1) + ": " + point);
        }
        scanner.close();

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLACK);
        setPenRadius(0.02);

        for (Point2D p : points) p.draw();

        StdDraw.setPenColor(StdDraw.BLACK);
        setPenRadius(0.01);

        Graph graphO = readGraph("eulerShow");
        for (int i = 0; i < graphO.V(); i++) {
            ArrayList<Integer> adj = graphO.adj(i);
            for (Integer k : adj) points[i].drawTo(points[k]);
        }

        StdDraw.show();
        StdDraw.pause(1000);
        setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.YELLOW);

        Queue<Integer> seq = (Queue<Integer>) hierholzer(graphO);
        int save = seq.dequeue();
        while (!seq.isEmpty()) {
            points[save].drawTo(points[save = seq.dequeue()]);
            StdDraw.show();
            StdDraw.pause(900);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        Scanner scan = new Scanner(System.in);
        String filename = scan.next();
        Cycles cycles = new Cycles(readGraph(filename));
        if (scan.nextInt() == 0)
            cycles.showEuler();
        scan.close();

        cycles.runEuler();
        cycles.runHamilton();
    }
}