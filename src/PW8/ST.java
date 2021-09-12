package PW8;

import PW3.Queue;

import java.util.NoSuchElementException;

@SuppressWarnings("Duplicates")
public class ST<Key extends Comparable<Key>, Value> {

    private static final int INIT_CAPACITY = 5;
    private Key[] keys;
    private Value[] values;
    private int n;

    public ST() {
        this(INIT_CAPACITY);
    }

    ST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    private void resize(int capacity) {
        assert capacity > n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        keys = tempk;
        values = tempv;
    }

    void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("NULL arg");
        if (val == null) {
            delete(key);
            return;
        }

        int a = rank(key);

        //if already present
        if (a < n && keys[a].compareTo(key) == 0) {
            values[a] = val;
            return;
        }
        //create new
        if (n == keys.length) resize(2 * keys.length);
        for (int i = n; i < a; i--) {
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }
        values[a] = val;
        keys[a] = key;
        n++;
    }

    Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("NULL argument!");
        if (isEmpty()) return null;
        int a = rank(key);
        if (a < n && keys[a].compareTo(key) == 0) return values[a];
        return null;
    }

    private void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("NULL arg");
        if (isEmpty()) return;

        int a = rank(key);
        System.out.println("RANK "+ a);
        if (a == n || keys[a].compareTo(key) != 0) return;

        for (int i = a; i < n - 1; i++) {
            keys[i] = keys[i + 1];
            values[i] = values[i + 1];
        }
        keys[n] = null;
        values[n] = null;

        //resize
        if (n > 0 && keys.length > 4 * n) resize(keys.length / 2);
        n--;
    }

    boolean contains(Key key) {
//        for (Key key1 : keys) {
//            if (key1.equals(key))
//                return true;
//        }
//        return false;
        return get(key) != null;
    }

    private boolean isEmpty() {
        return n == 0;
    }

    int size() {
        return n;
    }

    //найменший ключ
    private Key min() {
        return keys[0];
    }

    //найбільший ключ
    private Key max() {
        if (isEmpty()) throw new NoSuchElementException("!");
        return keys[n - 1];
    }

    //найбільший ключ менший або рівний key
    Key floor(Key key) {
        int a = rank(key);
        //a may be larger than last array index, then we will also take prev to it getFirst
        if (a < n && keys[a].compareTo(key) == 0) return keys[a];
        if (a == 0) return null;
        return keys[a - 1];
    }


    //найменший ключ більший або рівний key
    Key ceiling(Key key) {
        int a = rank(key);
        if (a == n) return null;
        return keys[a];

    }

    //base efficient function
    //basically order num in sorted array

    //TODO test learn
    int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("NULL arg");

        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("Invalid argument " + k);
        }
        return keys[k];
    }

    int size(Key lo, Key hi) {
        //means lo is higher than high
        if (lo.compareTo(hi) > 0) return 0;

        int a = rank(lo);
        int b = rank(hi);

        if (contains(hi)) return b - a + 1;
        return b - a;
    }

    void deleteMin() {
        delete(min());
    }

    void deleteMax() {
        delete(max());
    }

    public Iterable<Key> keys() {
        System.out.println(max());
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++)
            queue.enqueue(keys[i]);
        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    public static void main(String[] args) {
        ST<Integer, Character> st = new ST<>();
        for (int i = 0; i < 7; i++) {
//            System.out.println((char) ('a' + i));
            st.put(i, (char) ('a' + i));
//            System.out.println(st.keys[i]);
        }
        for (Integer k : st.keys()) { System.out.print(st.get(k) + " "); }
        System.out.println("\n" + st.max() + " " + st.get(st.max()));
        st.delete(0);
        for (Integer k : st.keys()) { System.out.print(st.get(k) + " "); }

    }
}
