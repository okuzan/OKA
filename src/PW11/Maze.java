package PW11;

import edu.princeton.cs.introcs.StdRandom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class Maze {

    static Graph mazeG;
    static int size, init, lamp;

    public Maze() {
        lamp = StdRandom.uniform(0, size - 1);
        System.out.println("Our position: " + init);
        System.out.println("Lamp apex: " + lamp);
    }

    public static void main(String[] args) throws Exception {

        readGraph();
        Maze maze = new Maze();
        maze.solveBFS();
        maze.solveDFS();
    }

    private static void solveDFS() {
//        boolean[] map = new boolean[size];
        System.out.println("------------DFS-----------");
        DepthFirstPaths dfp = new DepthFirstPaths(mazeG, init);
        if (!dfp.hasPathTo(lamp)) System.out.println("Cannot be reached!");
        else {
            System.out.print("PATH: ");
            Iterable<Integer> path = dfp.pathTo(lamp);
            int count = 0;
            for (int next : path) {
                System.out.print(next + " ");
                count++;
            }
            System.out.println("\nSteps: " + count + ", path: " + (count - 1));
        }
    }

    private static void solveBFS() {
        System.out.println("------------BFS-----------");
        BreadthFirstPaths bfp = new BreadthFirstPaths(mazeG, init);
        if (!bfp.hasPathTo(lamp)) System.out.println("Cannot be reached!");
        else {
            System.out.print("PATH: ");
            Iterable<Integer> path = bfp.pathTo(lamp);
            int count = 0;

            ArrayList<Integer> al = new ArrayList<>();
            for (int next : path) {
                al.add(next);
                count++;
            }

            for (int i = al.size() - 1; i != -1; i--) System.out.print(al.get(i) + " ");

            System.out.println("\nSteps: " + count + ", path: " + (count - 1));
        }
    }

    private static void readGraph() throws Exception {

        File file = new File("data/maze1.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        size = Integer.parseInt(br.readLine());
        init = Integer.parseInt(br.readLine());

        mazeG = new Graph(size);

        String line;
//        int m = 0;
        while ((line = br.readLine()) != null) {

            int[] peaks = Stream.of(line.split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int i = 1; i < peaks.length; i++) {
                mazeG.addEdge(peaks[0], peaks[i]);
            }
//            m++;
        }
//        size = m;

        br.close();
    }
}
