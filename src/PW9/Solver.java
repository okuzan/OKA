package PW9;

import PW3.Deque;
import PW7.MinPQ;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Solver {

    private int moves = 0;
    Board board;

    // знайти рішення для дошки initial
    Solver(Board initial) {
        board = initial;
    }

    // чи має початкова дошка розв’язок
    boolean isSolvable() {
        return countInversions(this.board) % 2 == 0;
    }

    int countInversions(Board board) {

        int inversions = 0;
        int[] arr = board.getOneLiner();
        for (int i = 0; i < arr.length; i++)
            for (int j = i + 1; j < arr.length; j++)
                if (arr[i] > arr[j] && arr[i] != 0 && arr[j] != 0)
                    inversions++;

        return inversions;
    }

    // мінімальна кількість кроків для вирішення дошки, -1 якщо немає рішення
    int moves() {
//        MinPQ<Board> pq = (MinPQ<Board>) solution();
//        if (pq == null) return -1;
//        return pq.size();
        return moves;
    }


    // послідовність дошок в найкоротшому рішенні; null якщо немає рішення
    Iterable<Board> solution() throws CloneNotSupportedException, InterruptedException {
        if (!isSolvable()) return null;

        MinPQ<Board> pq = new MinPQ<>();
        ArrayList<Board> al = new ArrayList<>();
        pq.insert(board);
        al.add(board);
        Board pre = null, cur = board;

        do {
//          System.out.println("-------");
            Deque<Board> neibs = (Deque<Board>) cur.neighbors();
//            System.out.println("How many neibs: " + neibs.size());
            for (Board neib : neibs)

                if (!neib.equals(pre)) {
                    neib.moves = cur.moves + 1;
//                    System.out.println("Moves: " + neib.moves);
//                    System.out.println("Func: " + neib.manhattan());
//                    Thread.sleep(300);
                    pq.insert(neib);
//                    System.out.println(neib.toString());
                } else {
//                    System.out.println("CRINGE DETECTED: ");
//                    System.out.println(neib.toString());
                }
            pre = cur;
//            System.out.println("Q-size " + pq.size());

            cur = pq.delMin();
            if (!al.contains(cur)) al.add(cur);
//            System.out.println("top: ");
//            System.out.println(cur.toString());

        } while (!cur.isGoal());

        moves = cur.moves;
        System.out.println("!");
        return al;
    }

    public static void main(String[] args) throws CloneNotSupportedException, InterruptedException {
        // створюємо початкову дошку з файлу
        In in = new In();
        int N = 3;
        Integer[][] blocks = new Integer[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // розв’язати
        Solver solver = new Solver(initial);

        // надрукувати рішення
        if (!solver.isSolvable())
            StdOut.println("Дошка не має розв’язку");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
        }
            StdOut.println("Мінімальна кількість кроків = " + solver.moves());
    }
}
