package PW9;

import PW3.Queue;

import java.util.Iterator;

@SuppressWarnings("Duplicates")
public class Tester {

    public static void main(String[] args) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        Iterator<Integer> it =  q.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            System.out.println(next);
        }
        System.out.println(q.getFirst());
    }
}
