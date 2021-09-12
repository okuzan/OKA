package PW3;

import edu.princeton.cs.introcs.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("Duplicates")
public class RandomQueue<Item> implements Iterable<Item> {

    private Item[] elements;
    private int size;

    public RandomQueue() {

        elements = (Item[]) new Object[2];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {

        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            copy[i] = elements[i];
        }
        elements = copy;
    }

    public void enqueue(Item item) {

        if (item == null) throw new NullPointerException();
        if (size == elements.length) resize(2 * elements.length);

        elements[size++] = item;
    }

    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException();

        int rand = StdRandom.uniform(size);
        Item value = elements[rand];

        if (rand != size - 1) elements[rand] = elements[size - 1];

        elements[size - 1] = null;
        size--;

        if (size > 0 && size <= elements.length / 4) resize(elements.length / 2);

        return value;
    }

    public Item sample() {

        if (size() == 0) throw new NoSuchElementException();

        int rand = StdRandom.uniform(size);

        Item value = elements[rand];

        if (size > 0 && size == elements.length / 4) resize(elements.length / 2);

        return value;
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int randLoc = 0;
        private int copySize = size;
        private Item[] copy = (Item[]) new Object[copySize];

        private RandomIterator() {
            for (int i = 0; i < copySize; i++) {
                copy[i] = elements[i];
            }
        }

        public boolean hasNext() {
            return copySize > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (copySize == 0) {
                throw new NoSuchElementException();
            }
            randLoc = StdRandom.uniform(copySize);
            Item currentItem = copy[randLoc];
            if (randLoc != copySize - 1) {
                copy[randLoc] = copy[copySize - 1];
            }
            copy[copySize - 1] = null;
            copySize--;
            return currentItem;
        }
    }

    public static void main(String[] args) {
        RandomQueue<String> rq = new RandomQueue<>();
        System.out.println("IS EMPTY?: "+rq.isEmpty());

        rq.enqueue("12 - UI");
        rq.enqueue("13 - UI");
        rq.enqueue("14 - UI");
        rq.enqueue("15 - UI");

        System.out.println("SIZE:      "+rq.size());
        System.out.println("IS EMPTY?: "+rq.isEmpty());
        System.out.println("SAMPLE:    " + rq.sample());
        System.out.println("DEQUEUED:  " + rq.dequeue());
        System.out.println("SIZE:      " + rq.size());

        Iterator<String> t = rq.iterator();

        while (t.hasNext()) {
            System.out.println(t.next());
        }
    }
}