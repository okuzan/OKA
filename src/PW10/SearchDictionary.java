package PW10;

import PW3.Queue;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("Duplicates")
public class SearchDictionary {

    private static Node root;
    private static int vocSize;

    SearchDictionary() {
        root = new Node();
    }

    static void addWord(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';

            if (cur.kids[index] == null)
                cur.kids[index] = new Node();

            cur.kids[index].count++;
            cur = cur.kids[index];
        }
        cur.isEnd = true;
        vocSize++;
    }

    static boolean search(String word) {

        int index;
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (cur.kids[index] == null) return false;
//            if(i == word.length() - 1)
            Node temp = cur;
            cur = cur.kids[index];
            cur.prev = temp;
        }
        return cur.prev.isEnd;
    }

    public static Iterable<String> getStrings(String word, Node n, Queue q) {
//        System.out.println("word: " + word);
        for (int j = 0; j < 25; j++) {
            Node t = n.kids[j];
            if (t != null) {
                System.out.println("___________");
                System.out.println((char)('a' + j));
                String newWord = word + ((char) ('a' + j));
                System.out.println("Appended() word: " + newWord);
                if (t.isEnd) {
                    System.out.println("it's end");
                    System.out.println("Word added " + newWord);
                    printQ(q);
                    q.enqueue(newWord);
                    printQ(q);
                    if (t.count > 1) {
                        System.out.println("but there is other..");
                        getStrings(newWord, t, q);

                    } else {
                        System.out.println("No others on this line");
                    }
                } else {
                    getStrings(newWord, t, q);
                }
            }
        }
        return q;
    }


    static void delWord(String word) {
        if (!search(word))
            throw new IllegalArgumentException("There is no such word!");


        Node cur = root;
        int index;

        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
//            System.out.println(index);

            if (cur.kids[index].count == 1)
                cur.kids[index] = null;

            else cur.kids[index].count--;

            cur = cur.kids[index];
        }

        if(cur != null) if(cur.count > 1) cur.isEnd = false;
        vocSize--;
    }

    public static Iterable<String> query(String query) {
        Queue<String> q = new Queue<>();
        int index;
        Node cur = root;
        String curStr = "";
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
//            System.out.println(curStr);
            index = c - 'a';
            if (c == '*') {
                if(cur.isEnd)
                    q.enqueue(curStr);
//                System.out.println("WILDCARD MODE.........");
                return getStrings(curStr, cur, q);

            } else if (cur.kids[index] == null) {
//                System.out.println("No such character");
                return null;
            }

            curStr += c;
            cur = cur.kids[index];
        }
        return q;
    }

    public int countWords() {
        return vocSize;
    }

    static void printQ(Queue<String> q) {
        for (String s : q) {
            System.out.println(s);
        }
    }
    static void getFile(){
        try {
            BufferedReader brw = new BufferedReader(new InputStreamReader(
                    new FileInputStream("data/words.txt"), StandardCharsets.UTF_8));
            String line;
            while ((line = brw.readLine()) != null) {
                addWord(line);
            }
            brw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SearchDictionary sd = new SearchDictionary();
//        addWord("hoof");
//        addWord("hoofla");
//        addWord("hoofdir");
//        addWord("hooflako");
//        addWord("hooflakor");
//        getFile();
//        System.out.println("COUNT WORDS: " + sd.countWords());
//        System.out.println(search("hoo"));
//        //throws Exception
////        delWord("hoofl");
//        delWord("hoofla");
//        System.out.println(search("hoofla"));
//        Queue<String> q1 = (Queue) query("hoof*");
//        printQ(q1);
//        Queue<String> q2 = (Queue) query("hope*");
//        printQ(q2);
        addWord("poofik");
        System.out.println(search("poofik"));
        addWord("poofikk");
        System.out.println(search("poofikk"));
        delWord("poofik");
//        delWord("poofik");
    }
}

