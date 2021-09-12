package PW9;

import PW3.Deque;
import PW3.Queue;

import java.util.Iterator;

@SuppressWarnings("Duplicates")
public class Board implements Comparable<Board>, Cloneable {

    Integer[][] blocks;
    int index0 = 0;
    int moves = 0;

    public Board(Integer[][] blocks) {
        this.blocks = blocks;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int s = blocks.length, res = 0;
        for (int i = 0; i < s; i++) {
            for (int k = 0; k < s - 1; k++) {
                int v = blocks[i][k];
                if (v != 0) if (blocks[i][k] != i * s + k) res++;
            }
        }
        return res;
    }

    // сума Манхатенських відстаней між блоками і цільовим станом
    public int manhattan() {
        int s = blocks.length, sum = 0;
        for (int i = 0; i < s; i++) {
            for (int k = 0; k < s; k++) {
                int v = blocks[i][k];
//                System.out.println("v " + v);
                if (v != 0) {
                    int vy = (v - 1) / s, vx = v - vy * s - 1;
//                    System.out.println("VX " + vx + " VY " + vy + " i " + i + " k " + k);
                    sum += Math.abs(vx - k) + Math.abs(vy - i);
//                    System.out.println("sum " + sum);
                }
            }
        }
        return sum;
    }


    // чи є ця дошка цільовим станом
    public boolean isGoal() {
        int s = blocks.length;
        for (int i = 0; i < s; i++) {
            for (int k = 0; k < s; k++) {
                int bl = blocks[i][k];
                if (bl != 0)
                    if (bl != i * s + k + 1) {
                        return false;
                    }
            }
        }
        return true;
    }

    // чи ця дошка рівна y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        int s = blocks.length;
        for (int i = 0; i < s; i++) {
            for (int k = 0; k < s; k++) {
                if (this.blocks[i][k] != that.blocks[i][k]) {
                    return false;
                }
            }
        }
        return true;
    }

    int[] getOneLiner() {
        int s = blocks.length;
        int[] arr = new int[s * s];
        int j = 0;
        for (int i = 0; i < s; i++) {
            Integer[] block = blocks[i];
            for (int k = 0; k < s; k++) {
                arr[j] = block[k];
                if (block[k] == 0) index0 = s - j % s - 1;
                j++;
            }
        }
        return arr;
    }

    // всі сусдні дошки
    public Iterable<Board> neighbors() throws CloneNotSupportedException, InterruptedException {

        //TODO index 0
        int x0 = 0, y0 = 0;
        int s = blocks.length;
        boolean found = false;
        for (int i = 0; i < s; i++) {
            if (found) break;
            for (int j = 0; j < s; j++) {
                if (blocks[i][j] == 0) {
                    found = true;
                    x0 = i;
                    y0 = j;
                    break;
                }
            }
        }

        Deque<Board> dq = new Deque<>();
        dq.addLast(new Board(blocks));

        //TODO CHECK
        int xt, yt;

//        System.out.println("x0 " + x0 + " y0 " + y0);
        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
//                System.out.println("..............");
//                System.out.println("i: " + i + ", k: " + k);
                if (Math.abs(i) + Math.abs(k) == 1) {
                    xt = x0 + i;
                    yt = y0 + k;
                    check(x0, y0, xt, yt, dq);
                }
//                System.out.println("..............");
//                Thread.sleep(200);
            }
        }
//        System.exit(0);
//        System.out.println("DONE");
//        printQ(dq);
        dq.removeFirst();
//        printQ(dq);
        return dq;
    }

    @Override
    protected Board clone() throws CloneNotSupportedException {
        Board clone = (Board) super.clone();
        clone.blocks = deepCopy(this.blocks);
        return clone;
    }

    <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    void printQ(Deque<Board> dq) {
        Iterator<Board> it = dq.iterator();
        System.out.println("------------------" + "\nDEQUEUE");
        while (it.hasNext()) {
            System.out.println("-------");
            Board next = it.next();
            System.out.println(next.toString());
            System.out.println("-------");
        }
        System.out.println("------------------");
    }

    private void check(int x0, int y0, int xt, int yt, Deque<Board> q) throws CloneNotSupportedException {
        Board initial = q.getFirst();
        int s = initial.blocks.length;
        if (xt < 0 || yt < 0 || xt >= s || yt >= s) {
//            System.out.println("Out of bounds");
            return;
        }

//        printQ(q);
//        System.out.println("Initial");
//        System.out.println(initial.toString());

//        System.out.println("xt " + xt + " yt " + yt);

        Board newB = initial.clone();
        newB.blocks[x0][y0] = newB.blocks[xt][yt];
        newB.blocks[xt][yt] = 0;
//        System.out.println(newB.toString());
//        System.out.println(initial.toString());

        //  System.exit(0);
        q.addLast(newB);
//        printQ(q);
    }

    // строкове подання
    public String toString() {
        for (int i = 0; i < blocks.length; i++) {
            System.out.print("[");
            for (int k = 0; k < blocks.length; k++)
                System.out.print(blocks[i][k] + " ");
            System.out.println("\b] ");
        }
        return "";
    }

    @Override
    public int compareTo(Board o) {
        int iThis = this.manhattan() + this.moves;
        int iThat = o.manhattan() + o.moves;
        return iThis - iThat;
    }
}