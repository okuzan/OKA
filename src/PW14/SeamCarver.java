package PW14;

import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdRandom;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class SeamCarver {

    private DPWG dag;
    private Picture pic;
    private int w;
    private int h;
    private int s;
    private int sink;
    private double[][] energyMatrix;
    private MyPoint[] points;
    private String[] paths;
    private ArrayList<MyPoint> topSort;
    private double inf = Double.POSITIVE_INFINITY;

    private SeamCarver(Picture picture) {
        pic = picture;
        update();
    }

    private void update() {
        w = pic.width();
        h = pic.height();
        s = w * h + 2;
        energyMatrix = createMatrix();
        points = new MyPoint[s];
        dag = createGraph();
        topSort = topologicalSort();
    }

    private double[][] createMatrix() {
        double[][] matr = new double[w][h];
        for (int i = 0; i < w; i++) for (int k = 0; k < h; k++) matr[i][k] = energy(i, k);

        return matr;
    }

    private DPWG createGraph() {
        DPWG dag = new DPWG(s);
        int iter = 0;

        for (int k = 0; k < h; k++)
            for (int i = 0; i < w; i++)
                points[i + k * w] = new MyPoint(iter++, i, k, energyMatrix[i][k], inf);


        int src = w * h;
        sink = src + 1;

        points[src] = new MyPoint(src, -5, -1, 0, 0);
        for (int i = 0; i < w; i++) dag.addEdge(points[src], points[i]);

        points[sink] = new MyPoint(sink, -7, -1, 0, inf);
        for (int i = w * (h - 1); i < src; i++) dag.addEdge(points[i], points[sink]);

        for (int i = 0; i < w * (h - 1); i++) {
            if (i % w != 0) dag.addEdge(points[i], points[i + w - 1]);
            dag.addEdge(points[i], points[i + w]);
            if (i % w != w - 1) dag.addEdge(points[i], points[i + w + 1]);
        }
        return dag;
    }


    private double calcUtil(Color first, Color second) {
        return (second.getRed() - first.getRed()) * (second.getRed() - first.getRed()) +
                (second.getBlue() - first.getBlue()) * (second.getBlue() - first.getBlue()) +
                (second.getGreen() - first.getGreen()) * (second.getGreen() - first.getGreen());
    }

    private double energy(int x, int y) {

        if (x < 1 || y >= h - 1 || x >= w - 1 || y < 1)
            return 10000;

        Color up = pic.get(x, y - 1);
        Color down = pic.get(x, y + 1);
        Color left = pic.get(x - 1, y);
        Color right = pic.get(x + 1, y);
        double dx2 = calcUtil(left, right);
        double dy2 = calcUtil(down, up);
        return Math.sqrt(dx2 + dy2);
    }

    private int[] findHorizontalSeam() {
        traspose();
        return findVerticalSeam();
    }

    private int[] findVerticalSeam() {
        paths = new String[s];
        for (int i = 0; i < s; i++) paths[i] = "";

        for (MyPoint i : topSort) {
            for (MyPoint adj : dag.adj(i.getI())) {
                updDist(i, adj);
                assert (paths[i.getI()] != null);
            }
        }

        int[] seam = new int[h + 1];
        int[] extracted = Stream.of(paths[sink].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        if (h + 1 >= 0) System.arraycopy(extracted, 0, seam, 0, h + 1);

        return seam;
    }

    private void traspose() {
        Picture newPic = new Picture(h, w);
        for (int k = 0; k < h; k++)
            for (int i = 0; i < w; i++)
                newPic.set(k, i, pic.get(i, k));

        pic = newPic;
        update();
    }

    private void updDist(MyPoint i, MyPoint adj) {
        double edge = (i.getWeight() + adj.getWeight()) / 2;
        double path = i.getDist() == inf ? edge : i.getDist() + edge;
        assert (path != inf);
        if (path < adj.getDist()) {
            adj.setDist(path);
            paths[adj.getI()] = paths[i.getI()] + " " + i.getI();
        }
    }

    private void topologicalSortUtil(int v, boolean[] visited, ArrayList<MyPoint> list) {
        visited[v] = true;
        MyPoint i;

        for (MyPoint point : dag.adj(v)) {
            i = point;
            if (!visited[i.getI()])
                topologicalSortUtil(i.getI(), visited, list);
        }

        list.add(points[v]);
    }

    private ArrayList<MyPoint> topologicalSort() {

        ArrayList<MyPoint> al = new ArrayList<>();
        ArrayList<MyPoint> res = new ArrayList<>();

        boolean[] visited = new boolean[dag.V()];

        for (int i = 0; i < s; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, al);

        for (int i = s - 1; i >= 0; i--) {
            res.add(al.get(i));
        }
        return res;
    }

    private Picture removeHorizontalSeam(int[] seam) {
        removeVerticalSeam(seam);
        traspose();
        return pic;
    }

    private Picture removeVerticalSeam(int[] seam) {
        pointVerticalSeam(seam);
        Picture shrinked = new Picture(w - 1, h);
        for (int k = 0; k < h; k++) {
            for (int i = 0; i < w; i++) {
                if (i < seam[k + 1] % w)
                    shrinked.set(i, k, pic.get(i, k));
                if (i > seam[k + 1] % w)
                    shrinked.set(i - 1, k, pic.get(i, k));
            }
        }
        pic = shrinked;
        w--;
        return pic;
    }

    private void pointVerticalSeam(int[] seam) {
        Picture shrinked = new Picture(w, h);
        for (int k = 0; k < h; k++)
            for (int i = 0; i < w; i++)
                if (i == seam[k + 1] % w)
                    shrinked.set(i, k, Color.RED);
                else
                    shrinked.set(i, k, pic.get(i, k));

        shrinked.show();
    }

    static Picture generate() {
        Picture pic = new Picture(5, 5);
        for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) pic.setRGB(i, j, StdRandom.uniform(2437298));
        return pic;
    }

    public static void main(String[] args) {
        File file = new File("./Data/Pics/surf.png");
        Picture pic = new Picture(file);
        pic.show();

        for (int i = 0; i < 20; i++) {
            SeamCarver carv = new SeamCarver(pic);
            pic = carv.removeVerticalSeam(carv.findVerticalSeam());
            pic = carv.removeHorizontalSeam(carv.findHorizontalSeam());
            System.out.println(pic.width() + "X" + pic.height());
        }
        pic.show();
    }
}