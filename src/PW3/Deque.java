package PW3;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("Duplicates")

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node head;
    private Node tail;

    public Deque() {
    }

    // створюється порожня дека

    public boolean isEmpty() {
        return (size == 0);
    }

    // чи порожня?
    public int size() {
        return size;
    }

    // кількість елементів в деці
    public void addFirst(Item item) {
        checkIfNull(item);

        Node newFirst = new Node();
        newFirst.item = item;

        //if there is old head to be connected with
        if (head != null) {
            newFirst.next = head;
            head.prev = newFirst;
        }

        head = newFirst;

        if (size == 0) head = newFirst;
        size++;
//        System.out.println(item + " was added as new head.\n");
//        Node oldFirst = head;

    }

    // додаємо на початок
    public void addLast(Item item) {
        checkIfNull(item);

        Node newLast = new Node();
        newLast.item = item;


        if (tail != null) {
            newLast.prev = tail;
            tail.next = newLast;
        }
        //in any case this should be done
        tail = newLast;

        //if only one getFirst is presented
        if (size == 0)
            head = tail;
        size++;

//        System.out.println(item + " was added as new tail.\n");

    }

    // додаємо в кінець
    public Item removeFirst() {
        checkIfEmpty();

        //saving to return
        Node oldFirst = head;

        head = head.next;

        if (head == null)
            tail = null;

        else head.prev = null;

        size--;
//        System.out.println(oldFirst.item + " (head) removed. New head is  - " + head.item);
        return oldFirst.item;
    }


    // видаляємо і повертаємо перший
    public Item removeLast() {
        checkIfEmpty();

        Node oldLast = tail;

//        System.out.println(tail.prev.item + " - tail prev ");
        tail = tail.prev;

        //tail node wad deleted
        if (tail == null)
            head = null;

        else tail.next = null;
        size--;

//        System.out.println(oldLast.item + " (tail) removed. New tail is  - " + tail.item);

        return oldLast.item;
    }

    public Item getFirst(){
        Iterator<Item> it = this.iterator();
        return it.next();
    }

    private void checkIfNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void checkIfEmpty() {
        if (size == 0) throw new NoSuchElementException();
    }

    // видаляємо і повертаємо останній
    @Override
    public Iterator<Item> iterator() {
        return new ItemsIterator();
    }

    private class ItemsIterator implements Iterator<Item> {

        private Node current;

        public ItemsIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null)
                throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // повертаємо ітератор по елементам
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public static void main(String[] args) {
        Deque<Integer> testDeque = new Deque<>();
        testDeque.addLast(1);
        testDeque.addFirst(3);
        testDeque.addLast(2);
        testDeque.addFirst(4);
        testDeque.addLast(8);
        testDeque.addFirst(11);

        testDeque.removeFirst();
        testDeque.removeFirst();

        testDeque.removeLast();

        System.out.println("1st item = " + testDeque.head.item);
        System.out.println("Last item = " + testDeque.tail.item);
        System.out.println("Size: " + testDeque.size());

        testDeque.addLast(null);

        testDeque.removeLast();
        testDeque.removeLast();
        testDeque.removeLast();
        testDeque.removeLast();

    }
}
